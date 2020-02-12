package cipher.jms.consumer.service;

import cipher.jms.consumer.domain.SmsAccountInfoDomain;

public interface SmsSendService {

    public String getSmsCodeInfo(String telephone,String companyId);

    public String getSmdInform(SmsAccountInfoDomain smsAccountInfoDomain);


}
