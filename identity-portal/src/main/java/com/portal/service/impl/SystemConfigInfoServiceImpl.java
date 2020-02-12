package com.portal.service.impl;

import com.portal.daoAuthoriza.SystemConfigInfoDAO;
import com.portal.domain.SystemConfigInfo;
import com.portal.service.SystemConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO:
 * create by liuying at 2019/8/20
 *
 * @author liuying
 * @since 2019/8/20 15:18
 */
@Service
public class SystemConfigInfoServiceImpl implements SystemConfigInfoService {

    @Autowired
    private SystemConfigInfoDAO systemConfigInfoDAO;

    @Override
    public SystemConfigInfo getSystemConfigInfo() {
        return systemConfigInfoDAO.getSystemConfigInfo();
    }
}
