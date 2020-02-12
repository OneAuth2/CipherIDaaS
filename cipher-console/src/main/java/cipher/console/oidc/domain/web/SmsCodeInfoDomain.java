package cipher.console.oidc.domain.web;


import cipher.console.oidc.jms.base.BaseMessage;

public class SmsCodeInfoDomain extends BaseMessage {

    private String code;

    private String phoneNumber;

    private String companyId;

    public SmsCodeInfoDomain(String phoneNumber,String companyId) {
        this.phoneNumber = phoneNumber;
        this.companyId = companyId;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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
