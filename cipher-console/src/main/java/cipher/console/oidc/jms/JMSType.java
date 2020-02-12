package cipher.console.oidc.jms;

public enum JMSType {

    SMS_SEND_SERVICE(0,"cipher.sms.code"),
    EMAIL_SEND_SERVICE(1,"cipher.mail.code"),

    WIFI_OFF_LINE(2,"cipher.wifi.action"),

    SMS_SEND_INFORM(3,"cipher.sms.inform"),

    EMAIL_SEND_INFORM(4,"cipher.mail.inform"),

    EMAIL_ISSUE_SEEDKEY(5,"cipher.mail.seedkey");

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

