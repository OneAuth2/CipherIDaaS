package com.portal.domain;


import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/5/6 16:10
 */
public class AuthFlow implements Serializable {

    public static final int SUCCESS=0 ; //验证通过

    public static final  int ERROR=1; //验证未通过

    private  int phoneNumber;
    private  int mail;
    private String dingBindLocalUuid;
    private String saiFuBindLocalUuid;
    private String daBaiBindLocalUuid;
    private String saiFuTotpLocalUuid;
    private String dingPushLocalUuid;
    private String weixinLocalUuid;



    public AuthFlow() {
        phoneNumber = ERROR;
        mail = ERROR;
    }

    public String getWeixinLocalUuid() {
        return weixinLocalUuid;
    }

    public void setWeixinLocalUuid(String weixinLocalUuid) {
        this.weixinLocalUuid = weixinLocalUuid;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getMail() {
        return mail;
    }

    public void setMail(int mail) {
        this.mail = mail;
    }

    public String getDingBindLocalUuid() {
        return dingBindLocalUuid;
    }

    public void setDingBindLocalUuid(String dingBindLocalUuid) {
        this.dingBindLocalUuid = dingBindLocalUuid;
    }

    public String getSaiFuBindLocalUuid() {
        return saiFuBindLocalUuid;
    }

    public void setSaiFuBindLocalUuid(String saiFuBindLocalUuid) {
        this.saiFuBindLocalUuid = saiFuBindLocalUuid;
    }

    public String getDaBaiBindLocalUuid() {
        return daBaiBindLocalUuid;
    }

    public void setDaBaiBindLocalUuid(String daBaiBindLocalUuid) {
        this.daBaiBindLocalUuid = daBaiBindLocalUuid;
    }

    public String getSaiFuTotpLocalUuid() {
        return saiFuTotpLocalUuid;
    }

    public void setSaiFuTotpLocalUuid(String saiFuTotpLocalUuid) {
        this.saiFuTotpLocalUuid = saiFuTotpLocalUuid;
    }


    public String getDingPushLocalUuid() {
        return dingPushLocalUuid;
    }

    public void setDingPushLocalUuid(String dingPushLocalUuid) {
        this.dingPushLocalUuid = dingPushLocalUuid;
    }
}
