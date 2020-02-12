package cipher.jms.consumer.domain;

import cipher.jms.consumer.jms.base.BaseMessage;

public class SmsCodeInfoDomain extends BaseMessage {

    private String code;

    private String phoneNumber;

    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public SmsCodeInfoDomain(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SmsCodeInfoDomain() {
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
                "code='" + code + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
