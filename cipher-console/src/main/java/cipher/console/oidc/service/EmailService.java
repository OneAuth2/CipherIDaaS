package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.EmailInfoDomain;

/**
 * Created by 95744 on 2018/8/27.
 */
public interface EmailService {


    public EmailInfoDomain getEmailInfo(EmailInfoDomain emailInfoDomain);

    public int updateEmailInfo(EmailInfoDomain emailInfoDomain);


    public int insertEmailInfo(EmailInfoDomain emailInfoDomain);
}
