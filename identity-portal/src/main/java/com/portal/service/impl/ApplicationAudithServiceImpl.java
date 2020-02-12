package com.portal.service.impl;



import com.portal.daoAuthoriza.ApplicationAuditInfoDAO;
import com.portal.domain.AppAuditInfo;
import com.portal.domain.ApplicationAuditInfo;
import com.portal.service.ApplicationAudithService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 95744 on 2018/6/14.
 */

@Service
public class ApplicationAudithServiceImpl  implements ApplicationAudithService {

    @Autowired
    private ApplicationAuditInfoDAO applicationAuditInfoDAO;

    @Override
    public int insertApplicationAuditInfo(ApplicationAuditInfo applicationAuditInfo) {
        return applicationAuditInfoDAO.insertSelective(applicationAuditInfo);
    }

    @Override
    public int insertAppAuditInfo(AppAuditInfo appAuditInfo) {
        return applicationAuditInfoDAO.insertAppAuditInfo(appAuditInfo);
    }
}
