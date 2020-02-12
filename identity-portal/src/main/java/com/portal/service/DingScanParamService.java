package com.portal.service;


import com.portal.domain.DingScanParam;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:21
 */
public interface DingScanParamService {

    /**
     * 获取后台配置的钉钉clientid 和clientSecret
     * @param companyUuid
     * @return
     */
    DingScanParam getDingScanParamByCompanyUuid(String companyUuid);

}
