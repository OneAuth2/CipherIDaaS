package com.portal.service;

import com.sun.istack.Nullable;

/**
 * radius push认证
 *
 * @Author: zt
 * @Date: 2019-08-08 16:49
 */
public interface RadiusPushAuth {

    /**
     * @param userName 用户名
     * @param pwd      密码,如果密码为空，则为CHAP认证方式,密码不为空，则是PAP认证方式
     * @param ip       vpn的ip
     * @return 成功则返回密码，失败返回空
     */
    public String radiusAuth(String userName, @Nullable String pwd, String ip);


}
