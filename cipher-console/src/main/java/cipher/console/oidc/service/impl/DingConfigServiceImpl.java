package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DingUrlConstants;
import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.web.AutoSyncDingDomain;
import cipher.console.oidc.domain.web.AutoSyncInfo;
import cipher.console.oidc.domain.web.DingConfigDomain;
import cipher.console.oidc.domain.web.DingScanInfoDomain;
import cipher.console.oidc.mapper.DingConfigMapper;
import cipher.console.oidc.mapper.DingScanInfoMapper;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AutoSyncService;
import cipher.console.oidc.service.DingConfigService;
import cipher.console.oidc.service.UUIDService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019-04-29 17:23
 */
@Service
public class DingConfigServiceImpl implements DingConfigService {


    @Autowired
    private DingConfigMapper dingConfigMapper;

    @Autowired
    private DingScanInfoMapper dingScanInfoMapper;

    @Autowired
    private UUIDService uuidService;

    @Autowired
    private AutoSyncService autoSyncService;

    @Override
    public void insertBaseConfig(DingConfigDomain dingConfigDomain) {
        dingConfigDomain.setId(uuidService.getUUid());
        dingConfigMapper.insertBaseConfig(dingConfigDomain);
    }

    @Override
    public void updateBaseConfig(DingConfigDomain dingConfigDomain) {
        dingConfigMapper.updateBaseConfig(dingConfigDomain);
    }

    @Override
    public Map<String, Object> insertOrUpdateBaseConfig(DingConfigDomain dingConfigDomain) {

        if (dingConfigDomain == null) {
            return NewReturnUtils.failureResponse("数据不能为空!");
        }

        DingConfigDomain domain = dingConfigMapper.queryConfigByCompanyUUid(dingConfigDomain.getCompanyUUid());
        //插入
        if (domain == null) {
            //处理基础配置信息
            String configUUid = uuidService.getUUid();
            dingConfigDomain.setId(configUUid);
            dingConfigMapper.insertBaseConfig(dingConfigDomain);

            //处理扫码的配置信息
            String scanAppInfoJson = dingConfigDomain.getScanAppInfoList();
            List<DingScanInfoDomain> dingScanInfoDomains = JSON.parseArray(scanAppInfoJson, DingScanInfoDomain.class);
            dingScanInfoDomains.forEach(dingScanInfoDomain -> {
                dingScanInfoDomain.setId(uuidService.getUUid());
                dingScanInfoDomain.setCompanyId(dingConfigDomain.getCompanyUUid());
                dingScanInfoDomain.setConfigId(configUUid);
            });

            dingScanInfoMapper.insertList(dingScanInfoDomains);
            return NewReturnUtils.successResponse();
        }
        //更新
        dingConfigMapper.updateBaseConfig(dingConfigDomain);

        String scanAppInfoJson = dingConfigDomain.getScanAppInfoList();

        if (StringUtils.isNotEmpty(scanAppInfoJson)) {
            List<DingScanInfoDomain> dingScanInfoDomainList = JSON.parseArray(scanAppInfoJson, DingScanInfoDomain.class);
            //更新扫码的配置信息
            dingScanInfoDomainList.forEach(dingScanInfoDomain -> dingScanInfoMapper.updateScanAppInfo(dingScanInfoDomain));
        }


        return NewReturnUtils.successResponse();
    }

    @Override
    public DingConfigDomain queryConfig(String companyUUid) {

        DingConfigDomain dingConfigDomain = dingConfigMapper.queryConfigByCompanyUUid(companyUUid);
        if(dingConfigDomain!=null&&StringUtils.isNotEmpty(dingConfigDomain.getId())){
            List<DingScanInfoDomain> dingScanInfoDomains = dingScanInfoMapper.getScanInfoListByConfigId(dingConfigDomain.getId());
//        dingConfigDomain.setScanAppInfoList(JSON.toJSONString(dingScanInfoDomains));
            dingConfigDomain.setScanInfoDomainList(dingScanInfoDomains);
        }
        return dingConfigDomain;

    }

    @Override
    public void updateAutoSync(String companyUUid,String autoSyncInfo) {
        //先获取上一次的配置判断是否开启自动定时同步
        AutoSyncDingDomain autoSyncDingDomain = dingConfigMapper.selectAutoSyncDing(companyUUid);
        if(autoSyncDingDomain!=null){
            if(autoSyncDingDomain.getSyncConfig()!=null&&StringUtils.isNotEmpty(autoSyncDingDomain.getSyncConfig())){
                //上一次配置信息
                AutoSyncInfo autoSyncInfo1 = new Gson().fromJson(autoSyncDingDomain.getSyncConfig(), AutoSyncInfo.class);
                if(autoSyncInfo1.getIsAutoSync()==0){
                    //关闭上一次定时任务
                    autoSyncService.closeAutoSyncDingTask(autoSyncDingDomain.getId());
                }
            }
        }
        if(StringUtils.isNotEmpty(autoSyncInfo)){
            //当前配置信息
            AutoSyncInfo autoSyncInfo2 = new Gson().fromJson(autoSyncInfo, AutoSyncInfo.class);
            if(autoSyncInfo2!=null){
                if(autoSyncInfo2.getIsAutoSync()==0&&
                        StringUtils.isNotEmpty(autoSyncInfo2.getAutoSyncDate())&&autoSyncInfo2.getInterval()>-1){
                    //开启新一次定时任务
                    autoSyncService.autoSyncDingTask(autoSyncInfo2,autoSyncDingDomain.getId(),companyUUid);
                }
            }
        }
        dingConfigMapper.updateAutoSync(companyUUid,autoSyncInfo);
    }

    @Override
    public String queryAutoSync(String companyUUid) {
        return dingConfigMapper.queryAutoByCompanyUUid(companyUUid);
    }

    @Autowired
    private RedisClient<String, String> redisClient;

    /**
     * 获取accessToken的地址
     */

    @Override
    public String getAccessTokenByCompanyUUid(String companyUUid) {
        String cacheKey = CacheKey.getCacheKeyDingAccessToken(companyUUid);
        if (redisClient.containsKey(cacheKey)) {
            return (String) redisClient.get(cacheKey);
        }

        DingConfigDomain dingConfigDomain = queryConfig(companyUUid);
        //没有key和secret无法获取access_token
        if (dingConfigDomain == null
                || StringUtils.isEmpty(dingConfigDomain.getAppKey())
                || StringUtils.isEmpty(dingConfigDomain.getAppSecret())) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("appkey=").append(dingConfigDomain.getAppKey())
                .append("&appsecret=").append(dingConfigDomain.getAppSecret());

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(DingUrlConstants.ACCESS_TOKEN_URL, null, null, stringBuilder.toString());
        String json = (String) map.get(HttpKey.RES);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(json);
        int expiresIn = jsonObject.getInteger("expires_in");
        String token = jsonObject.getString("access_token");
        redisClient.put(cacheKey, token, expiresIn);
        return token;
    }


}
