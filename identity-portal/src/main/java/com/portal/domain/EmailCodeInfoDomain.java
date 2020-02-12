package com.portal.domain;


import com.portal.jms.base.BaseMessage;

public class EmailCodeInfoDomain extends BaseMessage {

    private String code;

    private String mail;

    private String companyUuid;

    private int  type;


    public EmailCodeInfoDomain(String mail) {
        this.mail = mail;
    }

    public EmailCodeInfoDomain() {
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

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EmailCodeInfoDomain{" +
                "code='" + code + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
