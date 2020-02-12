package com.portal.service.impl;

import com.portal.daoAuthoriza.VpnConfigDAO;
import com.portal.domain.VpnConfigDomain;
import com.portal.service.VpnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2019-08-08 16:14
 */
@Service
public class VpnConfigServiceImpl implements VpnConfigService {

    @Autowired
    private VpnConfigDAO vpnConfigDAO;

    @Override
    public VpnConfigDomain getConfigByIp(String ip) {
        return vpnConfigDAO.getConfigByIp(ip);
    }


}
