package com.portal.jms.base;

import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.Destination;

public abstract class BaseProducer {

    private String address;

    protected Destination createDestination(){
        return new ActiveMQQueue(address);
    }

    public BaseProducer setAddress(String address) {
        this.address = address;
        return this;
    }

    public abstract <T extends BaseMessage> void sendMessage(T message);
}
