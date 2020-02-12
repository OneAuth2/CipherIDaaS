package cipher.jms.consumer.service;

import cipher.jms.consumer.domain.SmsAccountInfoDomain;

public interface AliyunSmsService {

    /**
     * 生成并发送短信验证码
     *
     * @param phone
     * @return
     * */
    public String getAliyunSendMobilePhoneSmsStr(String phone,String companyId);

    /**
     * 开通账号发送短信
     * @param smsAccountInfoDomain
     * @return
     */
    public String getAliyunSendMobilePhoneSmsInformStr(SmsAccountInfoDomain smsAccountInfoDomain);



}
