package cipher.jms.consumer.service;


import cipher.jms.consumer.domain.SmsAccountInfoDomain;

/**
 * 荣联云讯通短信验证码接口
 *
 * */
public interface RonglianSmsService {

    /**
     * 生成并发送短信验证码
     *
     * @param phone
     * @return
     * */
    public String getAndSendMobilePhoneSmsStr(String phone,String companyId);

    /**
     * 开通账号发送短信
     * @param smsAccountInfoDomain
     * @return
     */
    public String getAndSendMobilePhoneSmsInformStr(SmsAccountInfoDomain smsAccountInfoDomain);


}
