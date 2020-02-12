package com.portal.utils;

import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HeadUtils {



    private static final Logger LOGGER = LoggerFactory.getLogger(HeadUtils.class);


    public static void setResponseHeader(HttpServletResponse response, Map<String, Object> map) {
        Header[] headers = (Header[]) map.get(HttpKey.HEADERS);
        for (Header header : headers) {
            response.addHeader(header.getName(),header.getValue());
        }
    }


    public static void setResponseCookie(HttpServletResponse response, Map<String, Object> map) {
        Header[] headers = (Header[]) map.get(HttpKey.HEADERS);
        for (Header header : headers) {
            if ("Set-Cookie".equals(header.getName())) {
                try {
                    String cookieVal = header.getValue().split(";")[0];
                    String[] cookies=cookieVal.split("=");
                    String name=cookies[0];
                    String val=cookies[1];
                    response.addCookie(new Cookie(name,val));
                }catch (Exception e){
                    LOGGER.error("HeadUtils.setResponseHeader Enter the Exception: {},{}",e.getCause(),e.getStackTrace());
                    e.printStackTrace();
                }
            }
        }
    }



    public static String constructCookieStr(Map<String, Object> map) {
        Header[] headers = (Header[]) map.get(HttpKey.HEADERS);
        StringBuilder cookie = new StringBuilder();
        for (Header header : headers) {
            if ("Set-Cookie".equals(header.getName())) {
                String cookieInfo = header.getValue().split(";")[0];
                cookie.append(cookieInfo).append("; ");
            }
        }
        return cookie.toString();
    }




}
