package cipher.jms.consumer;

import cipher.jms.consumer.jms.JMSProducer;
import cipher.jms.consumer.jms.JMSType;
import cipher.jms.consumer.jms.base.BaseMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CipherJmsConsumerApplicationTests {

    @Autowired
    private JMSProducer jmsProducer;

    @Test
    public void contextLoads() {

//        EmailCodeInfoDomain emailCodeInfoDomain = new EmailCodeInfoDomain("tao.zhou@cipherchina.com");
//        jmsProducer.sendMessage(emailCodeInfoDomain, JMSType.EMAIL_SEND_SERVICE);

        SmsCodeInfoDomain smsCodeInfoDomain=new SmsCodeInfoDomain("15157603561");
        jmsProducer.sendMessage(smsCodeInfoDomain,JMSType.SMS_SEND_SERVICE);

    }

}

