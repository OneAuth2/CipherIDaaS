package com.portal.daoAuthoriza;


import com.portal.domain.SmsInfoDomain;

/**
 * Created by 95744 on 2018/8/23.
 */
public interface SmsInfoDAO {

    public SmsInfoDomain getSmsInfo(SmsInfoDomain smsInfoDomain);

    public  SmsInfoDomain  getSmsConfig(String companyUuid);

}
