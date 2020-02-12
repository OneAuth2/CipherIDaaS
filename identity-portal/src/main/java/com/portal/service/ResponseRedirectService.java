package com.portal.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/7/22 12:38
 */
public interface ResponseRedirectService {

    /**
     * 根据map传来的值进行重定向
     * create by 安歌
     * create time 2019年7月22日12:40:43
     *
     * @param map
     * @param response
     */
    public void redirctMap(Map<String, Object> map, HttpServletResponse response);

    /**
     * 根据传来的url确定是否进行重定向
     * @param url
     * @param response
     */
    public Boolean redirectUrl(String url, String applyId,String uuid,HttpServletResponse response,String appHost);

    public void redirectPortalBind(String url, String applyId,String uuid,HttpServletResponse response,String appHost);
}
