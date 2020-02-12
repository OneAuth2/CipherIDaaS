package com.jms;

import com.jms.base.BaseMessage;
import com.jms.base.BaseProducer;
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

    public <T extends BaseMessage> void sendMessage(T message,String address) {
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
