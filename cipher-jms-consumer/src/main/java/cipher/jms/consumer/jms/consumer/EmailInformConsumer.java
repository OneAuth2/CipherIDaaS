package cipher.jms.consumer.jms.consumer;

import cipher.jms.consumer.domain.EmailAccountInfoDomain;
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
public class EmailInformConsumer {
    private final static Logger logger = LoggerFactory.getLogger(EmailInformConsumer.class);

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "cipher.mail.inform")
    public void receiveQueue(String message){
        EmailAccountInfoDomain emailAccountInfoDomain = JSON.parseObject(message, new TypeReference<EmailAccountInfoDomain>() {});
        String code = emailService.getAndSendEmailInformStr(emailAccountInfoDomain);
        logger.info("Enter EmailInformConsumer.receiveQueue() the mail is {} and the code is {} and companyId is {}",emailAccountInfoDomain.getMail(),code,emailAccountInfoDomain.getCompanyId());
    }
}
