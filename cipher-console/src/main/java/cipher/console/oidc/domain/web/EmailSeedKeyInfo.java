package cipher.console.oidc.domain.web;

import cipher.console.oidc.jms.base.BaseMessage;

public class EmailSeedKeyInfo extends BaseMessage {
    private String companyId;
    private String mail;
    private String dynamicPassword;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDynamicPassword() {
        return dynamicPassword;
    }

    public void setDynamicPassword(String dynamicPassword) {
        this.dynamicPassword = dynamicPassword;
    }

    @Override
    public String toString() {
        return "EmailSeedKeyInfo{" +
                "companyId='" + companyId + '\'' +
                ", mail='" + mail + '\'' +
                ", dynamicPassword='" + dynamicPassword + '\'' +
                '}';
    }
}
