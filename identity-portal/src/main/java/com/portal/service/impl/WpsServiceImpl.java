package com.portal.service.impl;
import com.alibaba.dubbo.config.annotation.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.portal.commons.HttpKey;
import com.portal.domain.WpsCommonReqDomin;
import com.portal.domain.WpsQueryUserListDomain;
import com.portal.domain.WpsUserDomain;
import com.portal.service.WpsService;
import com.portal.utils.httpclient.HttpClientUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WpsServiceImpl implements WpsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WpsServiceImpl.class);

    @Override
    public String createSession(WpsCommonReqDomin wpsCommonReqDomin, String userId) {
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
        return res;
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
