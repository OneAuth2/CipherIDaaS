package cipher.console.oidc.service.impl.subapp;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.subapp.SecretManager;
import cipher.console.oidc.common.subapp.SubAppReturnKey;
import cipher.console.oidc.domain.subapp.MaycurCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.MaycurTokenReqDomain;
import cipher.console.oidc.service.subapp.MaycurService;
import cipher.console.oidc.util.HttpClientUtils;
import cipher.console.oidc.util.HttpsClientUtils;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/12/4 11:45
 */

@Service
public class MaycurServiceImpl implements MaycurService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaycurServiceImpl.class);


    @Override
    public String getSSOUrl(String apiPre, String ent_code, String userName, String secret) {
        StringBuilder beforEncode = new StringBuilder();
        long timeStamp = System.currentTimeMillis();
        beforEncode.append(secret)
                .append(":")
                .append(userName)
                .append(":")
                .append(timeStamp);
        String ssoToken = null;
        ssoToken = sha256Encode(beforEncode.toString());

        StringBuilder ssoQueryStr = new StringBuilder();
        ssoQueryStr.append("?entCode=")
                .append(ent_code)
                .append("&userId=")
                .append(userName)
                .append("&timestamp=")
                .append(timeStamp)
                .append("&token=")
                .append(ssoToken)
                .append("&language=zh")
                .append("&url=approve/corp");
        return apiPre + "/sso" + ssoQueryStr;
    }

    @Override
    public Map<String, Object> getTokenInfo(String apiPre, String ent_code, String userName, String secret) {
        String url = getSSOUrl(apiPre + "/api/web/auth", ent_code, userName, secret);
        LOGGER.info("tokenUrl=>{}", url);
        Map<String, Object> map = HttpsClientUtils.doGetWithNoHeader(url, null, null, null);
        String res = (String) map.get(HttpKey.RES);

        JSONObject jsonObject = JSONObject.fromObject(res);
        String code = jsonObject.getString("code");
        Map<String, Object> resMap = new HashMap<>();
        if ("ACK".equals(code)) {
            resMap.put(SubAppReturnKey.errCode, SubAppReturnKey.success);
            resMap.put(SubAppReturnKey.errMsg, "ok");
            String data = jsonObject.getString("data");
            String token = JSONObject.fromObject(data).getString("tokenId");
            LOGGER.info("获取到的tokenId信息=>{}", token);
            resMap.put("tokenId", token);
            return resMap;
        }

        resMap.put(SubAppReturnKey.errCode, SubAppReturnKey.fail);
        String msg = jsonObject.getString("message");
        LOGGER.info("获取每刻token失败,原因=>{}", msg);
        resMap.put(SubAppReturnKey.errMsg, msg);
        return resMap;
    }


    @Override
    public Map<String, Object> createAccount(String apiPre, String token, String entCode, MaycurCreateSubReqDomain maycurCreateSubReqDomain) {
        String payload = new Gson().toJson(maycurCreateSubReqDomain);
        LOGGER.info("创建每刻账号时的payload:{}", payload);
        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("entCode", entCode);
        customHeaders.put("tokenId", token);
        Map<String, Object> map = HttpsClientUtils.doPostWithPayloadNoHeader(apiPre + "/api/employee/save", payload, customHeaders);
        String res = (String) map.get(HttpKey.RES);
        int status = (int) map.get(HttpKey.STATUS_CODE);
        LOGGER.info("创建每刻报销系统账号时返回的http状态码:{},数据：{}", status, res);
        return null;
    }


    @Override
    public void queryDepartmen(String apiPre, String token, String entCode, Long startTime, Long endTime) {
        Map<String, Long> map = new HashMap<>();
        map.put("start", startTime);
        map.put("end", endTime);
        String payload = new Gson().toJson(map);
        LOGGER.info("获取部门列表时的payload:{}",payload);
        Map<String,String> customHader=new HashMap<>();
        customHader.put("entCode", entCode);
        customHader.put("tokenId", token);

        Map<String, Object> resMap = HttpsClientUtils.doPostWithPayloadNoHeader(apiPre + "/api/org/departments", payload,customHader);
        String res = (String) resMap.get(HttpKey.RES);
        int status= (int) resMap.get(HttpKey.STATUS_CODE);
        LOGGER.info("获取每刻部门列表返回的状态码：{}，数据：{}",status,res);
    }


    /**
     * sha256
     *
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    private String sha256Encode(String str) {
        return DigestUtils.sha256Hex(str.getBytes());
    }

}
