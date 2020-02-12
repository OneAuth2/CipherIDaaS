package cipher.jms.consumer.jms.consumer;

import cipher.jms.consumer.domain.SmsCodeInfoDomain;
import cipher.jms.consumer.service.RonglianSmsService;
import cipher.jms.consumer.service.SmsSendService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SmsConsumer{

    private final static Logger logger = LoggerFactory.getLogger(SmsConsumer.class);

    @Autowired
    private RonglianSmsService smsService;

    @Autowired
    private SmsSendService smsSendService;

    @JmsListener(destination = "cipher.sms.code")
    public void receiveQueue(String message){
        SmsCodeInfoDomain smsCodeInfoDomain = JSON.parseObject(message, new TypeReference<SmsCodeInfoDomain>() {});
       // String code = smsService.getAndSendMobilePhoneSmsStr(smsCodeInfoDomain.getPhoneNumber());
        String code=smsSendService.getSmsCodeInfo(smsCodeInfoDomain.getPhoneNumber(),smsCodeInfoDomain.getCompanyId());
        logger.info("Enter SmsConsumer.receiveQueue the phone is {} and the code is {}",smsCodeInfoDomain.getPhoneNumber(),code);
        return;
    }

}
