package com.portal.utils;

import com.portal.domain.PortalApplyInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: TK
 * @Date: 2019/7/1 20:22
 */
public final class UrlUtils {

    private static Logger logger = LoggerFactory.getLogger(UrlUtils.class);

    /**
     * 根据key-value的规则生成url
     */
    public static String getUrl(String key, String value, String url) {
        String realUrl = url + key + "=" + value;
        return realUrl;
    }

    /**
     * 生成rDsg的地址
     */
    public static String getRDsgUrl(String url, String ticket, String appUrl) {
        //定义网络类型取出协议  端口   ip地址
        String finalUrl = "";
        String protocol = "";
        int port = 0;
        String ip = "";
        String uri = null;

        //获取协议 端口 ip地址的值
        try {
            URL destinationUrl = new URL(url);
            protocol = destinationUrl.getProtocol();
            port = destinationUrl.getPort();
            ip = destinationUrl.getHost();
            uri = destinationUrl.getPath();
        } catch (MalformedURLException e) {
            logger.error("new URL() is Error the error is");
            logger.error(e.getMessage(), e);
        }

        String sfApp = "";
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(appUrl)) {
            GetParamUtil.UrlEntity parse = GetParamUtil.parse(appUrl);
            parse.getParams();
            sfApp = parse.getParams().get("sfApp");
        }


        //设置最终finalUrl
        finalUrl = protocol + "://" + ip + ":" + port + uri + "?ticket=" + ticket + "&sfApp=" + sfApp;

        //判断端口为-1 默认端口为80
        if (port == -1) {
            finalUrl = protocol + "://" + ip + uri + "?ticket=" + ticket + "&sfApp=" + sfApp;
        }

        return finalUrl;
    }

    /**
     * 獲取ticket
     *
     * @param url
     * @param ticket
     * @return
     */
    public static String getUrl(String url, String ticket) {

        //定义网络类型取出协议  端口   ip地址
        String finalUrl = "";
        String protocol = "";
        int port = 0;
        String ip = "";
        String uri = null;

        //获取协议 端口 ip地址的值
        try {
            URL destinationUrl = new URL(url);
            protocol = destinationUrl.getProtocol();
            port = destinationUrl.getPort();
            ip = destinationUrl.getHost();
            uri = destinationUrl.getPath();
        } catch (MalformedURLException e) {
            logger.error("new URL() is Error the error is");
            logger.error(e.getMessage(), e);
        }

        //设置最终finalUrl
        finalUrl = protocol + "://" + ip + ":" + port + uri + "?ticket=" + ticket;

        //判断端口为-1 默认端口为80
        if (port == -1) {
            finalUrl = protocol + "://" + ip + uri + "?ticket=" + ticket;
        }

        return finalUrl;
    }

    public static String getDynamicUrl(String applicationUrl, String authUrl) {
        //入参校验
        if (StringUtils.isEmpty(applicationUrl) || StringUtils.isEmpty(authUrl)) {
            return null;
        }
        int port = 0;
        String uri = "";
        String host = "";
        String finalUrl = "";
        String protocol = "";
        try {
            //构建目标地址
            URL destinationUrl = new URL(applicationUrl);
            //构建源地址
            URL sourceUrl = new URL(authUrl);
            //获取源地址的端口
            port = sourceUrl.getPort();
            //获取源地址的路径
            uri = sourceUrl.getPath();
            //获取目标地址的host
            host = destinationUrl.getHost();
            //获取目标地址的协议
            protocol=destinationUrl.getProtocol();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //设置最终finalUrl
        finalUrl = protocol + "://" + host + ":" + port + uri ;

        //判断端口为-1 默认端口为80
        if (port == -1) {
            finalUrl = protocol + "://" + host + uri;
        }

        return finalUrl;
    }

    public static void main(String... args){
      String url=  getDynamicUrl("http://mes.hengyi.com","http://192.168.0.154:80/sfLogin");
      System.out.println(url);
    }

    /**
     * 构建NcGet请求的路径
     * @param portalApplyInfo
     * @param userCode
     * @return
     */
    public static String getNcUrl(PortalApplyInfo portalApplyInfo, String userCode) {
        //构建get请求的路径
        String url=portalApplyInfo.getServer();
        String busiCenter=portalApplyInfo.getData();
        String groupCode=portalApplyInfo.getGroupCode();
        String langCode=portalApplyInfo.getLangCode();
        url+="?userCode="+userCode+"&busiCenter="+busiCenter+"&groupCode="+groupCode+"&langCode="+langCode;
        return url;
    }

    /**
     * 走Rdsg的地址
     */
    public String getUrlNoNext(String url, String ticket) {
        //定义网络类型取出协议  端口   ip地址
        String finalUrl = "";
        String protocol = "";
        int port = 0;
        String ip = "";
        String uri = null;

        //获取协议 端口 ip地址的值
        try {
            URL destinationUrl = new URL(url);
            protocol = destinationUrl.getProtocol();
            port = destinationUrl.getPort();
            ip = destinationUrl.getHost();
            uri = destinationUrl.getPath();
        } catch (MalformedURLException e) {
            logger.error("new URL() is Error the error is");
            logger.error(e.getMessage(), e);
        }

        //设置最终finalUrl
        finalUrl = protocol + "://" + ip + ":" + port + uri + "?ticket=" + ticket;

        //判断端口为-1 默认端口为80
        if (port == -1) {
            finalUrl = protocol + "://" + ip + uri + "?ticket=" + ticket;
        }
        return finalUrl;
    }

    /**
     * 获取真实的ip地址
     * create by 安歌
     *
     * @param serviceUrl
     * @return
     */
    public static String getUrl(String serviceUrl) {

        //入参校验
        if (StringUtils.isEmpty(serviceUrl)) {

            return "";
        }

        //定义标准地址service
        String service = "";

        //获取该url的标准地址
        try {
            //根据URL类获取协议 ip 端口
            URL url = new URL(serviceUrl);
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();

            //给service赋值 端口如果为-1则是默认端口8080 默认可以不加
            service = protocol + "://" + host + ':' + port;
            if (port == -1) {
                service = protocol + "://" + host;
            }

            //service为空直接返回
            if (StringUtils.isEmpty(service)) {

                return "";
            }
        } catch (MalformedURLException e) {
            logger.error("new URL() is Error the error is");
            logger.error(e.getMessage(), e);

            return "";
        }

        return service;
    }
}
