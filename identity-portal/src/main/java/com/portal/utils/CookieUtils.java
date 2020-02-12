package com.portal.utils;

import com.portal.commons.CacheKey;
import com.portal.commons.CookieKey;
import com.portal.listener.CookieListener;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CookieUtils {

    public static String getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void writeCookie(HttpServletResponse response, String cookieName, String value){
        Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        cookie.setMaxAge(72000);
        cookie.setHttpOnly(true);
        //设置session中的值
        response.addCookie(cookie);

    }

    public static void writePortalCookie(HttpServletResponse response, String userId, CookieListener listener){
        String cookieName= CookieKey.PORTAL_COOKIE_KEY;
        String cipherParam=NumberUtil.createRandomCharData(15);
        listener.setCookies(CacheKey.getCacheKeyCipherPortalCookieInfo(userId),cipherParam);
        writeCookie(response,cookieName,cipherParam);
    }

}
