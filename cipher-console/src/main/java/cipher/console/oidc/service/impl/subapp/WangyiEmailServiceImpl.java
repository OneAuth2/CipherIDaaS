package cipher.console.oidc.service.impl.subapp;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.subapp.SecretManager;
import cipher.console.oidc.common.subapp.SubAppReturnKey;
import cipher.console.oidc.domain.subapp.WangyiCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.WangyiUnReadMailCountDomain;
import cipher.console.oidc.service.subapp.WangyiEmailService;
import cipher.console.oidc.util.HttpsClientUtils;
import cipher.console.oidc.util.rsa.RSASignatureToQiye;
import cipher.console.oidc.util.rsa.RSATool;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: zt
 * @Date: 2018/12/3 10:10
 */
@Service
public class WangyiEmailServiceImpl implements WangyiEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WangyiEmailServiceImpl.class);

    @Override
    public String getSSOUrl(String priKey, String domain, String accountName) {
        LOGGER.info("Enter the WangyiEmailServiceImpl.getSSOUrl 获取网易邮箱的单点登录地址:accountName:{}", accountName);
        String time = System.currentTimeMillis() + "";
        String lang = "0";
        String src = accountName + domain + time;
        RSATool rsa = new RSATool();
        String enc = rsa.generateSHA1withRSASigature(src, priKey);
        String url = "https://entryhz.qiye.163.com/domain/oa/Entry?domain=" + domain + "&account_name=" + accountName + "&time=" + time + "&enc=" + enc + "&lang=" + lang;
        LOGGER.info("网易邮箱单点登录的地址url:{}", url);
        return url;
    }


    @Override
    public Map<String, Object> createSubAccount(WangyiCreateSubReqDomain wangyiCreateSubReqDomain, String priKey) {
        String queryStr = getQueryStrByDomain(wangyiCreateSubReqDomain);
        long time = System.currentTimeMillis();
        queryStr += "&time=" + time;
        String sign = RSASignatureToQiye.generateSigature(priKey, queryStr);

        String encodeStr = getQueryStrByDomainWithEncode(wangyiCreateSubReqDomain);
        encodeStr += "&time=" + time;
        encodeStr += "&sign=" + sign;
        LOGGER.info("创建网易邮箱账号时的queryStr:{}", queryStr);
        Map<String, Object> map = HttpsClientUtils.doPostWithNoHeader(SecretManager.WANGYI_API_PRE + "/account/createAccount", encodeStr);
        String res = (String) map.get(HttpKey.RES);
        LOGGER.info("创建网易邮箱账号返回的json:" + res);

        JSONObject jsonObject = JSONObject.fromObject(res);
        boolean isSuccess = jsonObject.getBoolean("suc");
        Map<String, Object> resMap = new HashMap<>();
        if (isSuccess) {
            resMap.put(SubAppReturnKey.errCode, SubAppReturnKey.success);
            resMap.put(SubAppReturnKey.errMsg, "创建用户成功");
            return resMap;
        }
        resMap.put(SubAppReturnKey.errCode, 1);
        resMap.put(SubAppReturnKey.errMsg, jsonObject.getString("error_code"));
        return resMap;
    }

    @Override
    public Map<String, Object> getUnReadMailCount(WangyiUnReadMailCountDomain wangyiUnReadMailCountDomain, String priKey) {

        String queryStr = getQueryStrByDomain(wangyiUnReadMailCountDomain);
        long time = System.currentTimeMillis();
        queryStr += "&time=" + time;
        String sign = RSASignatureToQiye.generateSigature(priKey, queryStr);

        String encodeQueryStr = getQueryStrByDomainWithEncode(wangyiUnReadMailCountDomain);
        encodeQueryStr += "&time=" + time;
        encodeQueryStr += "&sign=" + sign;

        LOGGER.info("获取网易邮箱账号未读邮件时的queryStr:{}", encodeQueryStr);
        Map<String, Object> map = HttpsClientUtils.doPostWithNoHeader(SecretManager.WANGYI_API_PRE + "/mailbox/getUnreadMsg", encodeQueryStr);
        String res = (String) map.get(HttpKey.RES);
        LOGGER.info("获取网易邮箱账号未读邮件返回的json:" + res);

        JSONObject jsonObject = JSONObject.fromObject(res);
        boolean isSuccess = jsonObject.getBoolean("suc");

        Map<String, Object> resMap = new HashMap<>();
        if (isSuccess) {
            resMap.put(SubAppReturnKey.errCode, SubAppReturnKey.success);
            resMap.put(SubAppReturnKey.errMsg, "获取未读邮件数量成功");
            String countStr = jsonObject.getString("con");
            JSONObject jsonObject1 = JSONObject.fromObject(countStr);
            int count = jsonObject1.getInt("count");
            resMap.put("count", count);
            return resMap;
        }

        resMap.put(SubAppReturnKey.errCode, SubAppReturnKey.fail);
        resMap.put(SubAppReturnKey.errMsg, jsonObject.getString("error_code"));

        return resMap;

    }


    /**
     * 根据实体获取get请求参数拼接
     *
     * @param obj
     * @return 参数拼接(get类型)
     */
    private String getQueryStrByDomain(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String queryStr = null;

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String val = "";
            try {
                val = (String) fields[i].get(obj);
//                if (!StringUtils.isBlank(val)) {
//                    val = URLEncoder.encode(val, "utf-8");
//                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            String key = fields[i].getName();

            if (StringUtils.isBlank(val)) {
//                val = "";
                continue;
            }

            if (i == 0) {
                queryStr = key + "=" + val;
            } else {
                queryStr += "&" + key + "=" + val;
            }
        }
        return queryStr;
    }


    private String getQueryStrByDomainWithEncode(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String queryStr = null;

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String val = "";
            try {
                val = (String) fields[i].get(obj);
                if (!StringUtils.isBlank(val)) {
                    val = URLEncoder.encode(val, "utf-8");
                }
            } catch (IllegalAccessException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String key = fields[i].getName();

            if (StringUtils.isBlank(val)) {
//                val = "";
                continue;
            }

            if (i == 0) {
                queryStr = key + "=" + val;
            } else {
                queryStr += "&" + key + "=" + val;
            }
        }
        return queryStr;
    }

}
