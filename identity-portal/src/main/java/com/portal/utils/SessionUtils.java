package com.portal.utils;

import org.apache.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: TK
 * @Date: 2019/7/2 15:04
 */
public final class SessionUtils {

    /**
     * 获取session对象中userName的值
     * @param request
     * @return
     */
    public static Object getSessionByName(HttpServletRequest request, String sessionName) {

        return  request.getSession().getAttribute(sessionName);
    }

    /**
     * 设置session中的userName
     *
     */
    public static void setSession(HttpServletRequest request, HttpServletResponse response, String key, String value){
        //设置session中的值
      //  HttpSession session = request.getSession(true);
      //  session.setMaxInactiveInterval(43200);
        request.getSession().setAttribute(key,value);
       /* Cookie cookie = new Cookie("JSESSIONID",session.getId());
        cookie.setPath(request.getContextPath()+"/");
        cookie.setMaxAge(72000);*/
       // response.addCookie(cookie);
        return;
    }
}
