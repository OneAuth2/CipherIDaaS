package com.portal.domain;


import com.portal.jms.base.BaseMessage;

public class SmsCodeInfoDomain extends BaseMessage {

    private String code;

    private String phoneNumber;

    private String companyUuid;

    private int descType;

    public SmsCodeInfoDomain(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SmsCodeInfoDomain() {
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public int getDescType() {
        return descType;
    }

    public void setDescType(int descType) {
        this.descType = descType;
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
                ", companyUuid='" + companyUuid + '\'' +
                ", descType='" + descType + '\'' +
                '}';
    }
}
