package com.portal.service.impl;

import com.portal.daoAuthoriza.CasAndRdsgConfigDAO;
import com.portal.domain.CasAndRdsgConfigInfoDomain;
import com.portal.service.CasAndRdsgConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 安歌
 * @Date: 2019/8/21 11:29
 */
@Service
public class CasAndRdsgConfigServiceImpl implements CasAndRdsgConfigService {

    @Autowired
    private CasAndRdsgConfigDAO casAndRdsgConfigDAO;

    @Override
    public String getCasServerAuthUrl() {
        //入参校验
        if (this.getCasAndRdsgConfigInfoDomain() == null) {
            return null;
        }
        return this.getCasAndRdsgConfigInfoDomain().getCasServerAuthUrl();
    }

    @Override
    public String getRdsgServerAuthUrl() {
        //入参校验
        if (this.getCasAndRdsgConfigInfoDomain() == null) {
            return null;
        }
        return this.getCasAndRdsgConfigInfoDomain().getRdsgServerAuthUrl();
    }

    @Override
    public String getRdsgServerUrl() {
        //入参校验
        if (this.getCasAndRdsgConfigInfoDomain() == null) {
            return null;
        }
        return this.getCasAndRdsgConfigInfoDomain().getRdsgServerUrl();
    }

    @Override
    public String getBindingAccountUrl() {
        //入参校验
        if (this.getCasAndRdsgConfigInfoDomain() == null) {
            return null;
        }
        return this.getCasAndRdsgConfigInfoDomain().getBindingAccountUrl();
    }

    private CasAndRdsgConfigInfoDomain getCasAndRdsgConfigInfoDomain() {
        return casAndRdsgConfigDAO.getCasAndRdsgConfigInfoDomain();
    }
}
