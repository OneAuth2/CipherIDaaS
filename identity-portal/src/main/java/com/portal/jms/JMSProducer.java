package com.portal.jms;



import com.portal.domain.EmailCodeInfoDomain;
import com.portal.domain.SmsCodeInfoDomain;
import com.portal.jms.base.BaseMessage;
import com.portal.jms.base.BaseProducer;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JMSProducer extends BaseProducer {
    @Autowired
    private JmsTemplate jmsTemplate;


    public <T extends BaseMessage> void sendMessage(T message, JMSType type) {
        setAddress(type.getAddress());
        this.jmsTemplate.convertAndSend(createDestination(),message.toMessage());
        return;
    }



    public <T extends BaseMessage> void sendMessage(T message, String address) {
        setAddress(address);
        this.jmsTemplate.convertAndSend(createDestination(),message.toMessage());
        return;
    }

    @Override
    public <T extends BaseMessage> void sendMessage(T message) {
        this.jmsTemplate.convertAndSend(createDestination(),message.toMessage());
        return;
    }





}
