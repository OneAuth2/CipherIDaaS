package com.portal.service.impl;

import com.portal.domain.PortalApplyInfo;
import com.portal.service.NcResService;
import com.portal.utils.TicketUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: 安歌
 * @Date: 2019/9/23 11:06
 */
@Service
public class NcResServiceImpl implements NcResService {

    private static Logger logger = LoggerFactory.getLogger(NcResServiceImpl.class);

    @Override
    public String getNcResInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public String getNcClientUrl(PortalApplyInfo portalApplyInfo, String map, String userCode) {
        //入参校验
        if (portalApplyInfo == null || map == null || userCode == null) {
            return null;
        }
        //获取ssoKey
        String ssoKey = map;
        try {
            //获取配置的Url的协议以及ip及端口
            URL url = new URL(portalApplyInfo.getServer());
            String protocol = url.getProtocol();
            String ip = url.getHost();
            int port = url.getPort();

            //构建cs的地址url
           // Uclient://start/http://192.168.0.12:82/?ssoKey=4e7df8e8-353d-4f31-8fb2-58f583f05461&uiloader=nc.login.sso.ui.SSOLoader
            //Uclient://start/http://host:port/?ssoKey=key&uid=usercode
            String ncClientUrl = "Uclient://start/" + protocol + "://" + ip + ":" + port + "/?ssoKey=" + ssoKey + "&uiloader=" + "nc.login.sso.ui.SSOLoader";
            logger.info(ncClientUrl);
            return ncClientUrl;
        } catch (MalformedURLException e) {
            logger.error("enter NcResServiceImpl.getNcUrl() error by new Url prams is", portalApplyInfo.getServer());
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String getNcIeUrl(PortalApplyInfo portalApplyInfo, String map) {

        //入参校验
        if (portalApplyInfo == null || map == null) {
            return null;
        }
        //获取ssoKey
        String ssoKey =map;
        try {
            //获取配置的Url的协议以及ip及端口
            URL url = new URL(portalApplyInfo.getServer());
            String protocol = url.getProtocol();
            String ip = url.getHost();
            int port = url.getPort();

            //构建cs的地址url
            // http://host:port/login.jsp?ssoKey=key
            String ncIeUrl = protocol + "://" + ip + ":" + port + "/login.jsp?ssoKey=" + ssoKey;
            logger.info(ncIeUrl);
            return ncIeUrl;
        } catch (MalformedURLException e) {
            logger.error("enter NcResServiceImpl.getNcUrl() error by new Url prams is", portalApplyInfo.getServer());
            logger.error(e.getMessage(), e);
            return null;
        }
    }


    @Override
    public List<NameValuePair> consNcReq(PortalApplyInfo portalApplyInfo, String userCode) {
        //入参校验
        if (portalApplyInfo == null) {
            return null;
        }
        //构建参数
        //请求用友服务器的请求参数
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("userCode", userCode));
       // nameValuePairList.add((new BasicNameValuePair("busiCenter", portalApplyInfo.getData())));
        nameValuePairList.add((new BasicNameValuePair("ssoKey", TicketUtil.getNcSsoKey())));
        //nameValuePairList.add((new BasicNameValuePair("langCode", portalApplyInfo.getLangCode())));
       // nameValuePairList.add((new BasicNameValuePair("password", pwd)));
        //返回请求
        return nameValuePairList;
    }



}
