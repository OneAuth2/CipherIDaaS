package com.portal.jms;

public enum JMSType {

    SMS_SEND_SERVICE(0, "cipher.sms.code"),
    EMAIL_SEND_SERVICE(1, "cipher.mail.code"),
    VPN_DEVICE_SERVICE(2, "cipher.vpn.auth.code");

    private int type;
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

