package com.portal.service;

import com.portal.domain.VpnConfigDomain;

/**
 * @Author: zt
 * @Date: 2019-08-08 16:13
 */
public interface VpnConfigService {

    /**
     * 根据VPN的ip查找对应的vpn配置
     *
     * @param ip vpn的ip
     * @return
     */
    public VpnConfigDomain getConfigByIp(String ip);



}
