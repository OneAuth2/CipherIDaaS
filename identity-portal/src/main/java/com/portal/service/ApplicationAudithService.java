package com.portal.service;


import com.portal.domain.AppAuditInfo;
import com.portal.domain.ApplicationAuditInfo;

/**
 * Created by 95744 on 2018/6/14.
 */
public interface ApplicationAudithService {

    public int insertApplicationAuditInfo(ApplicationAuditInfo applicationAuditInfo);


    int insertAppAuditInfo(AppAuditInfo appAuditInfo);
}
