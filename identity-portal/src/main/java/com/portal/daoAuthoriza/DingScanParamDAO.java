package com.portal.daoAuthoriza;


import com.portal.domain.DingScanParam;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:24
 */
public interface DingScanParamDAO {

     DingScanParam getDingScanParamByCompanyUuid(String companyUuid);
}
