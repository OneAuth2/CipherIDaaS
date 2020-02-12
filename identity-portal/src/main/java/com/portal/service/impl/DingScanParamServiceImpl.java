package com.portal.service.impl;



import com.portal.daoAuthoriza.DingScanParamDAO;
import com.portal.domain.DingScanParam;
import com.portal.service.DingScanParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:23
 */
@Service
public class DingScanParamServiceImpl implements DingScanParamService {
    @Autowired
    private DingScanParamDAO dingScanParamDAO;
    @Override
    public DingScanParam getDingScanParamByCompanyUuid(String companyUuid) {
        //入参校验
        if (companyUuid.isEmpty()){
            return null;
        }

        return dingScanParamDAO.getDingScanParamByCompanyUuid(companyUuid);
    }
}


