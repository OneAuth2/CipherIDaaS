package com.portal.domain;


import java.io.Serializable;

public class WpsRespInfoDomain implements Serializable {

    private String accessid;
    private String companyid;
    private String loginmode;
    private String result;
    private String secretkey;
    private String userid;
    private String username;
    private String wps_sid;

    public String getAccessid() {
        return accessid;
    }

    public void setAccessid(String accessid) {
        this.accessid = accessid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getLoginmode() {
        return loginmode;
    }

    public void setLoginmode(String loginmode) {
        this.loginmode = loginmode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWps_sid() {
        return wps_sid;
    }

    public void setWps_sid(String wps_sid) {
        this.wps_sid = wps_sid;
    }
}
