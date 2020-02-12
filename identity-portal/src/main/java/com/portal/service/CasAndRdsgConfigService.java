package com.portal.service;

/**
 * @Author: 安歌
 * @Date: 2019/8/21 11:13
 */
public interface CasAndRdsgConfigService {

    /**
     * 获取CasServer的授权页面地址
     * @return
     */
    String getCasServerAuthUrl();

    /**
     * 获取Rdsg的授权页面地址
     * @return
     */
    String getRdsgServerAuthUrl();

    /**
     * 获取Rdsg接收票据的地址
     * @return
     */
    String getRdsgServerUrl();

    /**
     * 获取子账号绑定页面的地址
     * @return
     */
    String getBindingAccountUrl();
}
