package com.portal.filter;

import com.alibaba.fastjson.JSONObject;
import com.portal.commons.*;
import com.portal.redis.RedisClient;
import com.portal.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

/**
 * session过期拦截器
 * 当session过期时跳转到登录页
 */
public class LoginInterCeptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(LoginInterCeptor.class);


    @Autowired
    private RedisClient redisClient;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.info(httpServletRequest.getRequestURI());
        //不拦截登录页
        String url = httpServletRequest.getRequestURI();
        List<EnumUrlTypeVo> list = UrlEnums.getEuumUrlList();
        for (EnumUrlTypeVo enumUrlTypeVo : list) {
            if (url.contains(enumUrlTypeVo.getDesc())) {
                return true;
            }
        }

        if (null == redisClient) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            redisClient = (RedisClient) factory.getBean("redisClient");
        }
        String uuid = (String) httpServletRequest.getSession().getAttribute(Constants.USERNAME);
        System.out.println("userId--------"+ uuid);

        String cookieStr = CookieUtils.getCookie(httpServletRequest, CookieKey.PORTAL_COOKIE_KEY);

        Object userSession = redisClient.get(CacheKey.getUserSessionCacheKey(uuid));

        logger.info("+++++++++++userSession+++++++++++++++++++"+userSession);
        String cookie = (String) redisClient.get(CacheKey.getCacheKeyCipherPortalCookieInfo(uuid));
        logger.info("cookie-----" + cookieStr + "----usersession---" + userSession + "----cookieredis----" + cookie);
        if (null == uuid) {
            return sessionExpire(httpServletResponse, "登录超时，请重新登录");
        }
        if (StringUtils.isNotEmpty(cookieStr) && StringUtils.isNotEmpty(cookie) && !cookieStr.equals(cookie)) {
            return sessionExpire(httpServletResponse, "账号已在其他位置登录");
        }
        return true;
    }


    // reDirect(httpServletRequest, httpServletResponse);

      /*  // 对于请求是ajax请求重定向问题的处理方法
        public void reDirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
            // 获取当前请求的路径
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            // 如果request.getHeader("X-Requested-With") 返回的是"XMLHttpRequest"说明就是ajax请求，需要特殊处理
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                // 告诉ajax我是重定向
                response.setHeader("REDIRECT", "REDIRECT");
                // 告诉ajax我重定向的路径
                response.setHeader("CONTENTPATH", basePath + "/login");
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                response.sendRedirect(basePath + "/login");
            }
        }
*/


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    public boolean sessionExpire(HttpServletResponse response, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("return_code", ReturnCode.SESSION_EXPIRE);
        jsonObject.put("return_msg", msg);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.append(jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
