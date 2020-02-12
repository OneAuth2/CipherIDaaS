package cipher.jms.consumer.jms.consumer;

import cipher.jms.consumer.domain.EmailCodeInfoDomain;
import cipher.jms.consumer.service.EmailService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final static Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "cipher.mail.code")
    public void receiveQueue(String message){
        EmailCodeInfoDomain emailCodeInfoDomain = JSON.parseObject(message, new TypeReference<EmailCodeInfoDomain>() {});
        String code = emailService.getAndSendEmailStr(emailCodeInfoDomain.getMail(),emailCodeInfoDomain.getCompanyId());
        logger.info("Enter EmailConsumer.receiveQueue the mail is {} and the code is {} and companyId is {}",emailCodeInfoDomain.getMail(),code,emailCodeInfoDomain.getCompanyId());
    }

}
