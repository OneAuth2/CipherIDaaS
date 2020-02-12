package cipher.console.oidc.service.impl.subapp;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.subapp.SubAppReturnKey;
import cipher.console.oidc.domain.subapp.TencentCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.TencentDepDomain;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.subapp.TencentEmailService;
import cipher.console.oidc.util.HttpsClientUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/11/29 9:23
 */

@Service
public class TencentEmailServiceImpl implements TencentEmailService {


    private static final Logger LOGGER = LoggerFactory.getLogger(TencentEmailServiceImpl.class);

    @Autowired
    private RedisClient redisClient;

    @Override
    public String getAccessToken(String ID, String secret) {
        String accessToken = "";
      /* String accessToken = (String) redisClient.get(secret);
        if (StringUtils.isNotBlank(accessToken)) {
            return accessToken;
        }*/
        Map<String, Object> tokenMap = HttpsClientUtils.doGetWithNoHeader("https://api.exmail.qq.com/cgi-bin/gettoken", null, null, "corpid=" + ID + "&corpsecret=" + secret);
        String res = (String) tokenMap.get(HttpKey.RES);
        LOGGER.info("获取到的腾讯邮箱Access_Token:=" + res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        int errcode = jsonObject.getInt("errcode");
        String errmsg = jsonObject.getString("errmsg");
        if (errcode != 0) {
            LOGGER.error("获取腾讯邮箱的access_token失败，" + errmsg);
            return null;
        }

        accessToken = jsonObject.getString("access_token");
        String expires_in = jsonObject.getString("expires_in");
        redisClient.put(secret, accessToken, Long.parseLong(expires_in));
        return accessToken;
    }


    @Override
    public Map<String, Object> createSubAccount(String accessToken, TencentCreateSubReqDomain tencentCreateSubReqDomain) {
        Gson gson = new GsonBuilder().create();

        if (tencentCreateSubReqDomain.getName() != null) {
            String name = null;
            name = new String(tencentCreateSubReqDomain.getName().getBytes(), StandardCharsets.ISO_8859_1);
            tencentCreateSubReqDomain.setName(name);
        }
        
        String payload = gson.toJson(tencentCreateSubReqDomain);
        LOGGER.info("腾讯邮箱待创建的用户:" + payload);
        Map<String, Object> resMap = HttpsClientUtils.doPostWithPayload("https://api.exmail.qq.com/cgi-bin/user/create", null, payload, null, null, null, "access_token=" + accessToken);
        String res = (String) resMap.get(HttpKey.RES);
        LOGGER.info("创建腾讯邮箱账号返回的数据:" + res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        int errcode = jsonObject.getInt("errcode");
        String errmsg = jsonObject.getString("errmsg");

        Map<String, Object> map = new HashMap<>();
        map.put(SubAppReturnKey.errCode, errcode);
        map.put(SubAppReturnKey.errMsg, errmsg);
        return map;
    }

    @Override
    public TencentDepDomain qeryDept(String accessToken, Long id) {
        if (null == id) {
            id = 1L;
        }
        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader("https://api.exmail.qq.com/cgi-bin/department/list", null, null, "access_token=" + accessToken + "&id=" + id);
        String res = (String) map.get(HttpKey.RES);
        LOGGER.info("获取到的腾讯邮箱的部门信息:" + res);
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(res, TencentDepDomain.class);
    }


}
