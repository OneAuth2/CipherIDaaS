package cipher.jms.consumer.domain;

import cipher.jms.consumer.jms.base.BaseMessage;

public class EmailCodeInfoDomain extends BaseMessage {

    private String code;

    private String mail;

    private String companyId;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }



    public EmailCodeInfoDomain() {
    }

    public EmailCodeInfoDomain(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "EmailCodeInfoDomain{" +
                "code='" + code + '\'' +
                ", mail='" + mail + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
