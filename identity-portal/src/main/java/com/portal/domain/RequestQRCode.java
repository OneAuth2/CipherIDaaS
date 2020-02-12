package com.portal.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class RequestQRCode implements Serializable{

    String appid;//应用ID

    String secret;//用户密钥

    String url;//回调地址


    public RequestQRCode() {
    }

    public RequestQRCode(String appid,String url, String secret) {
        this.appid = appid;
        this.secret = secret;
        this.url = url;
    }

    public RequestQRCode(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RequestQRCode{" +
                "appid='" + appid + '\'' +
                ", secret='" + secret + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
