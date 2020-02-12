package com.portal.service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/7/18 19:27
 */
public interface PortalToXDsgService  {

    /**
     * create by 安歌
     * create time 2019年7月18日19:44:29
     * 从portal到xdsg请求 并把返回结果封装成Map返回
     * @param applyId
     * @param userId
     * @return
     */
    public Map<String,Object> portalToXDsg(HttpServletResponse response, String applyId, String userId, String companyId);
}
