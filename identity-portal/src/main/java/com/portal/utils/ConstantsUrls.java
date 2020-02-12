package com.portal.utils;


import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class ConstantsUrls {

    /**
     * totp认证服务器前缀
     */
    public static final String URL_PRE="http://101.132.145.69:7080";

//    public static final String URL_PRE="http://192.168.0.132:8081";

    public final static String    USER_SESSION_INFO = "userName";


    public final static String    CONSOLE_SESSION_INFO = "account";


    public final static String    CONSOLE_SESSION_UUID = "uuid";


    public final static String    CONSOLE_SESSION_COMPANYID = "companyUUid";
    /**
     * 应用注册时生成的唯一的应用id
     */
    public static final String APPID="cipher01";

    /**
     * 应用注册时生成的秘钥
     */
    public static final String SECRET="OOBB7OFDSSQTE72K";

    /**
     * 授权回调地址,传输时需要经过UrlEncode编码
     */
    public static final String CALLBACK_URL="http://101.132.145.69:9443/callBack";

    /**
     * 二维码扫描登录时，返回的uuid存储到redis的key前缀
     */
    public static final String LOGIN_UUID="LOGIN_UUID_";


    /**
     * 门户页面的地址
     */
    public static final String PORTAL_URL="http://101.132.145.69:7080/portal/index";



    public static String getSessionUser(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute(USER_SESSION_INFO));
    }

    public static String getConsoleSessionUuid(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute(CONSOLE_SESSION_UUID));
    }

    public static String getConsoleSessionCompanyid(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute(CONSOLE_SESSION_COMPANYID));
    }


/*

    public static String getSubstr(String str){
        String[] s= StringUtils.substringsBetween(str,"https://",".");
        return  s[0];
    }
*/



    public static String getServerHost(String str){
        String reg = ".*\\/\\/([^\\/\\:]*).*";
        String result=str.replaceAll (reg, "$1");
        return  result;

    }


    public static String getServerPort(String str){
        String[] result=str.split(":");
        return result[2];

    }


}
