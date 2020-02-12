package cipher.jms.consumer;


import cipher.jms.consumer.jms.base.BaseMessage;

public class SmsCodeInfoDomain extends BaseMessage {

    private String phoneNumber;

    public SmsCodeInfoDomain(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SmsCodeInfoDomain() {
    }





    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "SmsCodeInfoDomain{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
