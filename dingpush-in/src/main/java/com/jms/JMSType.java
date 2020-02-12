package com.jms;

public enum JMSType {

    SMS_SEND_SERVICE(0,"cipher.sms.code"),
    EMAIL_SEND_SERVICE(1,"cipher.mail.code");

    private int    type;
    private String address;

    private JMSType(int type, String address) {
        this.type = type;
        this.address = address;
    }

    public int getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

}

