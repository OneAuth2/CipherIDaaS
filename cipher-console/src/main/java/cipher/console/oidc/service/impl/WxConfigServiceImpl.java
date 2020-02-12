package cipher.console.oidc.service.impl;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.WxConfigMapper;
import cipher.console.oidc.mapper.WxScanInfoMapper;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AutoSyncService;
import cipher.console.oidc.service.UUIDService;
import cipher.console.oidc.service.WxConfigService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019-10-1115:46
 **/
@Service
public class WxConfigServiceImpl implements WxConfigService {

    @Autowired
    private RedisClient<String, String> redisClient;

    @Autowired
    private WxConfigMapper wxConfigMapper;

    @Autowired
    private UUIDService uuidService;

    @Autowired
    private AutoSyncService autoSyncService;

    @Autowired
    private WxScanInfoMapper wxScanInfoMapper;

    private Logger logger = LoggerFactory.getLogger(WxConfigServiceImpl.class);

    @Override
    public void updateAutoSync(String companyUUid, String autoSyncInfo) {
        //先获取上一次的配置判断是否开启自动定时同步
        AutoSyncWxDomain autoSyncWxDomain = wxConfigMapper.selectAutoSyncWx(companyUUid);
        if (autoSyncWxDomain != null) {
            if (autoSyncWxDomain.getSyncConfig() != null && StringUtils.isNotEmpty(autoSyncWxDomain.getSyncConfig())) {
                //上一次配置信息
                AutoSyncInfo autoSyncInfo1 = new Gson().fromJson(autoSyncWxDomain.getSyncConfig(), AutoSyncInfo.class);
                if (autoSyncInfo1.getIsAutoSync() == 0) {
                    //关闭上一次定时任务
                    autoSyncService.closeAutoSyncWxTask(autoSyncWxDomain.getId());
                }
            }
        }
        if (StringUtils.isNotEmpty(autoSyncInfo)) {
            //当前配置信息
            AutoSyncInfo autoSyncInfo2 = new Gson().fromJson(autoSyncInfo, AutoSyncInfo.class);
            if (autoSyncInfo2 != null) {
                if (autoSyncInfo2.getIsAutoSync() == 0 &&
                        StringUtils.isNotEmpty(autoSyncInfo2.getAutoSyncDate()) && autoSyncInfo2.getInterval() > -1) {
                    //开启新一次定时任务
                    autoSyncService.autoSyncWxTask(autoSyncInfo2, autoSyncWxDomain.getId(), companyUUid);
                }
            }
        }
        wxConfigMapper.updateAutoSync(companyUUid, autoSyncInfo);
    }

    @Override
    public String queryAutoSync(String companyId) {
        return wxConfigMapper.queryAutoByCompanyUUid(companyId);
    }

    @Override
    public Map<String, Object> insertOrUpdateBaseConfig(WxConfigDomain wxConfigDomain) {
        if (wxConfigDomain == null) {
            return NewReturnUtils.failureResponse("数据不能为空!");
        }
        WxConfigDomain domain = wxConfigMapper.queryConfigByCompanyUUid(wxConfigDomain.getCompanyUUid());
        //插入
        if (domain == null) {
            //处理基础配置信息
            String configUUid = uuidService.getUUid();
            wxConfigDomain.setId(configUUid);

            //处理扫码的配置信息
            String scanAppInfoJson = wxConfigDomain.getScanAppInfoList();
            List<WxScanInfoDomain> wxScanInfoDomains = JSON.parseArray(scanAppInfoJson, WxScanInfoDomain.class);
            wxScanInfoDomains.forEach(wxScanInfoDomain -> {
                wxScanInfoDomain.setId(uuidService.getUUid());
                wxScanInfoDomain.setCompanyId(wxConfigDomain.getCompanyUUid());
                wxScanInfoDomain.setConfigId(configUUid);
                wxConfigDomain.setCorpSecret(wxScanInfoDomain.getScanAppSecret());
                wxConfigDomain.setAgentId(wxScanInfoDomain.getScanAppId());
                wxConfigDomain.setCallbackUrl(wxScanInfoDomain.getCallBackUrl());
            });
            wxScanInfoMapper.insertList(wxScanInfoDomains);
            wxConfigMapper.insertBaseConfig(wxConfigDomain);
            return NewReturnUtils.successResponse();
        }
        //更新

        String scanAppInfoJson = wxConfigDomain.getScanAppInfoList();

        if (StringUtils.isNotEmpty(scanAppInfoJson)) {
            List<WxScanInfoDomain> wxScanInfoDomainList = JSON.parseArray(scanAppInfoJson, WxScanInfoDomain.class);
            for(WxScanInfoDomain wxScanInfoDomain:wxScanInfoDomainList){
                wxConfigDomain.setCorpSecret(wxScanInfoDomain.getScanAppSecret());
                wxConfigDomain.setAgentId(wxScanInfoDomain.getScanAppId());
                wxConfigDomain.setCallbackUrl(wxScanInfoDomain.getCallBackUrl());
                wxConfigMapper.updateBaseConfig(wxConfigDomain);
            }


            //更新扫码的配置信息
            wxScanInfoDomainList.forEach(wxScanInfoDomain ->
                    wxScanInfoMapper.updateScanAppInfo(wxScanInfoDomain));

        }
        return NewReturnUtils.successResponse();
    }

    @Override
    public WxConfigDomain queryConfig(String companyUUid) {
        WxConfigDomain wxConfigDomain = wxConfigMapper.queryConfigByCompanyUUid(companyUUid);
        if(wxConfigDomain!=null&&StringUtils.isNotEmpty(wxConfigDomain.getId())){
            List<WxScanInfoDomain> wxScanInfoDomains = wxScanInfoMapper.getScanInfoListByConfigId(wxConfigDomain.getId());
//         wxConfigDomain.setScanAppInfoList(JSON.toJSONString(wxScanInfoDomains));
            wxConfigDomain.setScanInfoDomainList(wxScanInfoDomains);
        }
        return wxConfigDomain;
    }

    @Override
    public String getAccessTokenByCompanyUUid(String companyUUid) {
        String cacheKey = CacheKey.getCacheKeyWxAccessToken(companyUUid);
        if (redisClient.containsKey(cacheKey)) {
            return (String) redisClient.get(cacheKey);
        }

        WxConfigDomain wxConfigDomain = queryConfig(companyUUid);
        //没有key和secret无法获取access_token
        if (wxConfigDomain == null
                || StringUtils.isEmpty(wxConfigDomain.getCorpId())
                || StringUtils.isEmpty(wxConfigDomain.getCorpSecret())) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("corpid=").append(wxConfigDomain.getCorpId())
                .append("&corpsecret=").append(wxConfigDomain.getCorpSecret());

        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(WxUrlConstants.ACCESS_TOKEN_URL, null, null, stringBuilder.toString());
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
