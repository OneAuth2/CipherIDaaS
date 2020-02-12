package cipher.jms.consumer.jms.consumer;

import cipher.jms.consumer.domain.EmailAccountInfoDomain;
import cipher.jms.consumer.domain.EmailSeedKeyInfo;
import cipher.jms.consumer.service.EmailService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailSeedkeyConsumer {
    private final static Logger logger = LoggerFactory.getLogger(EmailSeedkeyConsumer.class);

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "cipher.mail.seedkey")
    public void receiveQueue(String message){
        EmailSeedKeyInfo emailSeedKeyInfo = JSON.parseObject(message, new TypeReference<EmailSeedKeyInfo>() {});
        String code = emailService.sendEmailSeedkey(emailSeedKeyInfo);
        logger.info("Enter EmailInformConsumer.receiveQueue() the mail is {} and the code is {} and companyId is {}",emailSeedKeyInfo.getMail(),code,emailSeedKeyInfo.getCompanyId());
    }

}
