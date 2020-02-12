package com.portal.domain;


public class AppRegister {

    private String appid;

    private String secret;

    public AppRegister(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
    }

    public AppRegister() {
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "AppRegister{" +
                "appid='" + appid + '\'' +
                ", secret='" + secret + '\'' +
                '}';
    }
}
