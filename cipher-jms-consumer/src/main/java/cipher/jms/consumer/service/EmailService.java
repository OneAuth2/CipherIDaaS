package cipher.jms.consumer.service;

import cipher.jms.consumer.domain.EmailAccountInfoDomain;
import cipher.jms.consumer.domain.EmailInfoDomain;
import cipher.jms.consumer.domain.EmailSeedKeyInfo;

public interface EmailService {

    /**
     * 生成并发送邮件验证码
     *
     * @param phone
     * @return
     * */
    public String getAndSendEmailStr(String phone,String companyId);

    /**
     * 获取缓存的验证码
     *
     * @param phone
     * */
    public String getEmailStr(String phone);


    public EmailInfoDomain getEmailInfo(String companyId);

    /**
     * 开通账号发送短信
     * @param emailAccountInfoDomain
     * @return
     */
    public String getAndSendEmailInformStr(EmailAccountInfoDomain emailAccountInfoDomain);

    /**
     * 下发种子密钥
     * @param emailSeedKeyInfo
     * @return
     */
    public String sendEmailSeedkey(EmailSeedKeyInfo emailSeedKeyInfo);

}
