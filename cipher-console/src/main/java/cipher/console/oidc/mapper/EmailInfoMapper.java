package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.EmailInfoDomain;

/**
 * Created by 95744 on 2018/8/23.
 */
public interface EmailInfoMapper {

    public EmailInfoDomain getEmailInfo(EmailInfoDomain emailInfoDomain);

    public int updateEmailInfo(EmailInfoDomain emailInfoDomain);

    public int insertEmailInfo(EmailInfoDomain emailInfoDomain);

}
