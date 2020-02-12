package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.SmsInfoDomain;

/**
 * Created by 95744 on 2018/8/23.
 */
public interface SmsInfoMapper {

    public SmsInfoDomain getSmsInfo(SmsInfoDomain smsInfoDomain);

    public int updateSmsInfo(SmsInfoDomain smsInfoDomain);

    public int insertSmsInfo(SmsInfoDomain smsInfoDomain);
}
