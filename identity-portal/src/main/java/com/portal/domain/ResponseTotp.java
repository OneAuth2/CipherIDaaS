package com.portal.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseTotp implements Serializable{
    /*身份验证状态*/
    private int identityStatus;

    /*TOTP服务器通信状态*/
    private int totpStatus;


    /*TOTP码校验状态*/
    private int checkCode;

    /*校验信息*/
    private String checkMessage;

    public ResponseTotp() {
    }

    public ResponseTotp(int identityStatus, int totpStatus, int checkCode, String checkMessage) {
        this.identityStatus = identityStatus;
        this.totpStatus = totpStatus;
        this.checkCode = checkCode;
        this.checkMessage = checkMessage;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public int getTotpStatus() {
        return totpStatus;
    }

    public void setTotpStatus(int totpStatus) {
        this.totpStatus = totpStatus;
    }

    public int getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(int checkCode) {
        this.checkCode = checkCode;
    }

    public String getCheckMessage() {
        return checkMessage;
    }

    public void setCheckMessage(String checkMessage) {
        this.checkMessage = checkMessage;
    }

    @Override
    public String toString() {
        return "ResponseUser{" +
                "identityStatus=" + identityStatus +
                ", totpStatus=" + totpStatus +
                ", checkCode=" + checkCode +
                ", checkMessage='" + checkMessage + '\'' +
                '}';
    }
}
