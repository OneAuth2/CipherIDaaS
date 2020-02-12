package com.portal.auth.response;

import java.io.Serializable;

public class GetQrCodeCallBackResp implements Serializable {
    private String certToken;
    private String userName;
    private String phoneNumber;
    private String mail;
    private int certRes;
    private String certString;
    private String sig;
    private int uuid;

    public GetQrCodeCallBackResp() {
    }

    public GetQrCodeCallBackResp(String certToken, String userName, String phoneNumber, String mail, int certRes, String certString, String sig, int uuid) {
        this.certToken = certToken;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.certRes = certRes;
        this.certString = certString;
        this.sig = sig;
        this.uuid = uuid;
    }


    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public String getCertToken() {
        return certToken;
    }

    public void setCertToken(String certToken) {
        this.certToken = certToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getCertRes() {
        return certRes;
    }

    public void setCertRes(int certRes) {
        this.certRes = certRes;
    }

    public String getCertString() {
        return certString;
    }

    public void setCertString(String certString) {
        this.certString = certString;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    @Override
    public String toString() {
        return "GetQrCodeCallBackResp{" +
                "certToken='" + certToken + '\'' +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", certRes=" + certRes +
                ", certString='" + certString + '\'' +
                ", sig='" + sig + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}


