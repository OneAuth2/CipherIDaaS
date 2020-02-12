package cipher.jms.consumer.jms.consumer;

import cipher.jms.consumer.domain.SmsAccountInfoDomain;
import cipher.jms.consumer.domain.SmsCodeInfoDomain;
import cipher.jms.consumer.service.SmsSendService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.stereotype.Component;

@Component
public class SmsInformConsumer {
    private final static Logger logger = LoggerFactory.getLogger(SmsInformConsumer.class);

    @Autowired
    private SmsSendService smsSendService;

    @JmsListener(destination = "cipher.sms.inform")
    public void receiveQueue(String message){
        SmsAccountInfoDomain smsAccountInfoDomain = JSON.parseObject(message, new TypeReference<SmsAccountInfoDomain>() {});
        smsSendService.getSmdInform(smsAccountInfoDomain);
        logger.info("enter SmsInformConsumer.receiveQueue() message=[{}]=="+smsAccountInfoDomain.toString());
    }
}
