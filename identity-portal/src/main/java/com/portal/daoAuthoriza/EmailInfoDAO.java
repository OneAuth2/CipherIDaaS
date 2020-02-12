package com.portal.daoAuthoriza;


import com.portal.domain.EmailInfoDomain;

/**
 * Created by 95744 on 2018/8/23.
 */
public interface EmailInfoDAO {

    public EmailInfoDomain getEmailInfo(EmailInfoDomain emailInfoDomain);

    public  EmailInfoDomain getEmailConfig(EmailInfoDomain emailInfoDomain);

}
