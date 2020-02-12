package com.portal.domain;


import java.io.Serializable;

public class XdsgReqInfo implements Serializable {

    private String appKey;
    private String subName;
    private String subPwd;
    private String teamIds;
    private String userName;
    private String token;
    private String sign;
    private String applyId;
    private String apiDsgUrl;
    private String appSession;
    private String appSessionMaxAge;
    private String config;
    private String xdsgUrl;

    public String getXdsgUrl() {
        return xdsgUrl;
    }

    public void setXdsgUrl(String xdsgUrl) {
        this.xdsgUrl = xdsgUrl;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getAppSession() {
        return appSession;
    }

    public void setAppSession(String appSession) {
        this.appSession = appSession;
    }

    public String getAppSessionMaxAge() {
        return appSessionMaxAge;
    }

    public void setAppSessionMaxAge(String appSessionMaxAge) {
        this.appSessionMaxAge = appSessionMaxAge;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getSubPwd() {
        return subPwd;
    }

    public void setSubPwd(String subPwd) {
        this.subPwd = subPwd;
    }

    public String getTeamIds() {
        return teamIds;
    }

    public void setTeamIds(String teamIds) {
        this.teamIds = teamIds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApiDsgUrl() {
        return apiDsgUrl;
    }

    public void setApiDsgUrl(String apiDsgUrl) {
        this.apiDsgUrl = apiDsgUrl;
    }

    public XdsgReqInfo(String appKey, String subName, String subPwd, String teamIds, String userName, String token, String sign, String applyId, String apiDsgUrl, String appSession, String appSessionMaxAge, String config) {
        this.appKey = appKey;
        this.subName = subName;
        this.subPwd = subPwd;
        this.teamIds = teamIds;
        this.userName = userName;
        this.token = token;
        this.sign = sign;
        this.applyId = applyId;
        this.apiDsgUrl = apiDsgUrl;
        this.appSession = appSession;
        this.appSessionMaxAge = appSessionMaxAge;
        this.config = config;
    }

    public XdsgReqInfo() {
    }

    public XdsgReqInfo(String subName, String subPwd, String config) {
        super();
        this.subName = subName;
        this.subPwd = subPwd;
        this.config = config;
    }

    public XdsgReqInfo(String appKey, String teamIds, String userName, String token, String sign, String applyId, String apiDsgUrl, String appSession, String appSessionMaxAge,String xdsgUrl) {
        this.appKey = appKey;
        this.teamIds = teamIds;
        this.userName = userName;
        this.token = token;
        this.sign = sign;
        this.applyId = applyId;
        this.apiDsgUrl = apiDsgUrl;
        this.appSession = appSession;
        this.appSessionMaxAge = appSessionMaxAge;
        this.xdsgUrl=xdsgUrl;
    }

    @Override
    public String toString() {
        return "XdsgReqInfo{" +
                "appKey='" + appKey + '\'' +
                ", subName='" + subName + '\'' +
                ", subPwd='" + subPwd + '\'' +
                ", teamIds='" + teamIds + '\'' +
                ", userName='" + userName + '\'' +
                ", token='" + token + '\'' +
                ", sign='" + sign + '\'' +
                ", applyId='" + applyId + '\'' +
                ", apiDsgUrl='" + apiDsgUrl + '\'' +
                ", appSession='" + appSession + '\'' +
                ", appSessionMaxAge='" + appSessionMaxAge + '\'' +
                ", config='" + config + '\'' +
                ", xdsgUrl='" + xdsgUrl + '\'' +
                '}';
    }
}
