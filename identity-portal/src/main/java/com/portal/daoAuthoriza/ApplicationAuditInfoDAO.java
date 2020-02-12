package com.portal.daoAuthoriza;


import com.portal.domain.AppAuditInfo;
import com.portal.domain.ApplicationAuditInfo;

public interface ApplicationAuditInfoDAO {

    int insertSelective(ApplicationAuditInfo record);

    int insertAppAuditInfo(AppAuditInfo appAuditInfo);



}