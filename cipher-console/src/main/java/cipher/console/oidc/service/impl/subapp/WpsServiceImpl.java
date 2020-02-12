package cipher.console.oidc.service.impl.subapp;

import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.subapp.SubAppReturnKey;
import cipher.console.oidc.domain.subapp.*;
import cipher.console.oidc.service.subapp.WpsService;
import cipher.console.oidc.util.HttpClientUtils;
import cipher.console.oidc.util.WpsHttpClientUtil;
import cipher.console.oidc.util.aes.EncryptionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/12/7 14:11
 */
@Service
public class WpsServiceImpl implements WpsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WpsServiceImpl.class);

    @Override
    public Map<String, Object> createSession(WpsCommonReqDomin wpsCommonReqDomin, String userId) {
        Map<String, String> paramStr = new HashMap<>();
        paramStr.put("userid", userId);
        String payload = new Gson().toJson(paramStr);
        LOGGER.info("创建session时的payload:{}", payload);
        Map<String, String> headerMap = getAuthOrization(wpsCommonReqDomin, payload);
        String sessionApi = wpsCommonReqDomin.getSsoApiPre() + "/account/api/session/create";
        LOGGER.info("创建session的api地址:" + sessionApi);
        Map<String, Object> map = HttpClientUtils.doPostWithPayload(sessionApi, payload, headerMap);
        String res = (String) map.get(HttpKey.RES);
        LOGGER.info("金山云创建session返回的数据:{}", res);
        return map;
    }

    /**
     * uri:/v1/companies/:company_id/3rd_users/:thirdid/wps_user_id
     * 此接口为 restful调用方式
     *
     * @param wpsCommonReqDomin 都需要
     * @param userId            本系统用户的id
     * @return
     */
    @Override
    public Long getWpsUserIdBy3rdUser(WpsCommonReqDomin wpsCommonReqDomin, String userId) {
        String uri = "/v1/companies/" + wpsCommonReqDomin.getCompanyId() + "/3rd_users/" + userId + "/wps_user_id";

        LOGGER.info("进入wps获取云协作用户userId service:本地userId=>{},api: {}", userId, uri);
        Map<String, String> headerMap = getAuthOrization(wpsCommonReqDomin, uri);
        Map<String, Object> resMap = HttpClientUtils.doGet(wpsCommonReqDomin.getApiPre() + uri, headerMap);
        String res = (String) resMap.get(HttpKey.RES);
        int status = (int) resMap.get(HttpKey.STATUS_CODE);
        LOGGER.info("换取wps云协作用户userId的返回的状态码:{},数据:{}", status, res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        if (!jsonObject.containsKey("id")) {
            LOGGER.info("换取wps云协作用户userId未成功");
            return null;
        }
        Long wpsUserId = null;
        try {
            wpsUserId = jsonObject.getLong("id");
        } catch (Exception e) {
            LOGGER.error("请求云协作用户id失败:{}", res);
            return null;
        }
        LOGGER.info("请求云协作用户id成功:{}", wpsUserId);
        return wpsUserId;
    }

    /**
     * 创建用户 /v1/companies/:company_id/depts/:dept_id/users
     *
     * @param wpsCommonReqDomin
     * @param wpsCreateUserReqDomain
     * @param deptId
     * @return
     */
    @Override
    public Map<String, Object> createUser(WpsCommonReqDomin wpsCommonReqDomin, WpsCreateUserReqDomain wpsCreateUserReqDomain, long deptId) {
//        String payload = new Gson().toJson(wpsCreateUserReqDomain);
        GsonBuilder gsonBuilder = new GsonBuilder();
        String payload = gsonBuilder.serializeNulls().create().toJson(wpsCreateUserReqDomain);
        LOGGER.info("创建金山云用户时的payload:{}", payload);
        Map<String, String> authMap = getAuthOrization(wpsCommonReqDomin, payload);
        String uri = "/v1/companies/" + wpsCommonReqDomin.getCompanyId() + "/depts/" + deptId + "/users";
        Map<String, Object> resMap = HttpClientUtils.doPostWithPayload(wpsCommonReqDomin.getApiPre() + uri, payload, authMap);
        String res = (String) resMap.get(HttpKey.RES);
        int status = (int) resMap.get(HttpKey.STATUS_CODE);
        LOGGER.info("创建金山用户返回的状态码:{},数据:{}", status, res);
        JSONObject jsonObject = JSONObject.fromObject(res);
        Map<String, Object> resultMap = new HashMap<>();
        if (jsonObject.containsKey("id")) {
            resultMap.put(SubAppReturnKey.errCode, SubAppReturnKey.success);
            resultMap.put(SubAppReturnKey.errMsg, jsonObject.getString("id"));
            return resultMap;
        }
        resultMap.put(SubAppReturnKey.errCode, SubAppReturnKey.fail);
        resultMap.put(SubAppReturnKey.errMsg, jsonObject.getString("msg"));
        return resultMap;
    }

    /**
     * /v1/companies/:company_id/depts/:dept_id/depts
     *
     * @param wpsCommonReqDomin
     * @param deptId
     * @return
     */
    @Override
    public Map<String, Object> createDept(WpsCommonReqDomin wpsCommonReqDomin, Long deptId) {
        String uri = "/v1/companies/" + wpsCommonReqDomin.getCompanyId() + "/depts/" + deptId + "/depts";
        Map<String, String> authMap = getAuthOrization(wpsCommonReqDomin, uri);
        Map<String, Object> resMap = HttpClientUtils.doPost(wpsCommonReqDomin.getApiPre() + uri, null, authMap);
        String res = (String) resMap.get(HttpKey.RES);
        LOGGER.info("在:{} 部门下创建子部门时返回的数据为:{}", deptId, res);
        return resMap;
    }


    @Override
    public List<WpsDeptDomain> queryWpsDeptList(WpsCommonReqDomin wpsCommonReqDomin, int offset, int limit) {
        String uri = "/v1/companies/" + wpsCommonReqDomin.getCompanyId() + "/depts";

        //某个部门下的子部门
//        String uri = "/v1/depts/" + wpsCommonReqDomin.getCompanyId() + "/depts";

        String queryStr = "";
        if (offset > 0) {
            queryStr = "offset=" + offset + "&limit=" + limit;
        }
        String payload = uri;
        if (StringUtils.isNotEmpty(queryStr)) {
            payload += "?" + queryStr;
        }
        Map<String, String> authMap = getAuthOrization(wpsCommonReqDomin, payload);
        LOGGER.info("部门列表的api_address:{}", wpsCommonReqDomin.getApiPre() + uri);
        Map<String, Object> resMap = HttpClientUtils.doGet(wpsCommonReqDomin.getApiPre() + uri, authMap, null, null, queryStr, null);
        String res = (String) resMap.get(HttpKey.RES);
        int status = (int) resMap.get(HttpKey.STATUS_CODE);
        LOGGER.info("查询wps部门列表时返回的状态码:{},数据:{}", status, res);
        return null;
    }


    @Override
    public List<WpsUserDomain> queryUserByConpanyId(WpsCommonReqDomin wpsCommonReqDomin, WpsQueryUserListDomain wpsQueryUserListDomain) {
        String uri = "/v1/companies/" + wpsCommonReqDomin.getCompanyId() + "/members";

        String queryStr = "";

        if (StringUtils.isNotEmpty(wpsQueryUserListDomain.getUnique_name())) {
            queryStr = "unique_name=" + wpsQueryUserListDomain.getUnique_name();
        }

        if (wpsQueryUserListDomain.getOffset() >= 0) {
            if (StringUtils.isNotEmpty(queryStr)) {
                queryStr += "&offset=" + wpsQueryUserListDomain.getOffset() + "&limit=" + wpsQueryUserListDomain.getLimit();
            } else {
                queryStr += "&offset=" + wpsQueryUserListDomain.getOffset() + "&limit=" + wpsQueryUserListDomain.getLimit();
            }
        }
        String payload = "";
        if (StringUtils.isNotEmpty(queryStr)) {
            payload = uri + "?" + queryStr;
        } else {
            payload = uri;
        }

        Map<String, String> headerMap = getAuthOrization(wpsCommonReqDomin, payload);
        Map<String, Object> resMap = HttpClientUtils.doGet(wpsCommonReqDomin.getApiPre() + uri, headerMap, null, null, queryStr, null);
        String res = (String) resMap.get(HttpKey.RES);
        int status = (int) resMap.get(HttpKey.STATUS_CODE);
        LOGGER.info("获取金山云用户列表返回的状态:{},数据:{}", status, res);

        List<WpsUserDomain> list = new Gson().fromJson(res, new TypeToken<List<WpsUserDomain>>() {
        }.getType());
        return list;
    }

    /**
     * 获取接口签名内容
     *
     * @param wpsCommonReqDomin 通用信息
     * @param payload           参数包体，若为空则传uri
     * @return 接口签名串
     */
    private Map<String, String> getAuthOrization(WpsCommonReqDomin wpsCommonReqDomin, String payload) {
        Map<String, String> headerMap = new HashMap<>();
        String RFC1123_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
        Locale LOCALE_US = Locale.US;
        DateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);
        String gmtStr = rfc1123Format.format(new Date());

        LOGGER.info("GMT时间:{}", gmtStr);
        String contentMd5 = DigestUtils.md5Hex(payload);
        String authOrization = "WPS-2:" + wpsCommonReqDomin.getAccessId() + ":" + DigestUtils.sha1Hex(wpsCommonReqDomin.getSecret() + contentMd5 + "application/json" + gmtStr);
        headerMap.put("Authorization", authOrization);
        headerMap.put("Content-Md5", contentMd5);
        headerMap.put("content-type", "application/json");
        headerMap.put("Date", gmtStr);
        return headerMap;
    }

}
