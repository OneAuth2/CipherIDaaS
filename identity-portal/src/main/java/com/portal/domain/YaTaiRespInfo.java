package com.portal.domain;


import java.io.Serializable;

public class YaTaiRespInfo implements Serializable{
    private String token;
    private String stime;
    private String nonce;
    private String body;
    private String domain;
    private String pwd;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public YaTaiRespInfo(String token, String stime, String nonce, String body, String domain, String pwd) {
        this.token = token;
        this.stime = stime;
        this.nonce = nonce;
        this.body = body;
        this.domain = domain;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "YaTaiRespInfo{" +
                "token='" + token + '\'' +
                ", stime='" + stime + '\'' +
                ", nonce='" + nonce + '\'' +
                ", body='" + body + '\'' +
                ", domain='" + domain + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }
}
