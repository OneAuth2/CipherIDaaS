package com.portal.domain;

public class RealParamDomain {

    private String teamIdList;

    private String sfApp;

    private String appKey;

    private Long sessionTime;

    private String ssoUri;

    private String token;

    private String getParam;

    public String getTeamIdList() {
        return teamIdList;
    }

    public void setTeamIdList(String teamIdList) {
        this.teamIdList = teamIdList;
    }

    public String getSfApp() {
        return sfApp;
    }

    public void setSfApp(String sfApp) {
        this.sfApp = sfApp;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Long getSessionTime() {
        return sessionTime;
    }

    public void setSessionTime(Long sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getSsoUri() {
        return ssoUri;
    }

    public void setSsoUri(String ssoUri) {
        this.ssoUri = ssoUri;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGetParam() {
        return getParam;
    }

    public void setGetParam(String getParam) {
        this.getParam = getParam;
    }

    @Override
    public String toString() {
        return "RealParamDomain{" +
                "teamIdList='" + teamIdList + '\'' +
                ", sfApp='" + sfApp + '\'' +
                ", appKey='" + appKey + '\'' +
                ", sessionTime=" + sessionTime +
                ", ssoUri='" + ssoUri + '\'' +
                ", token='" + token + '\'' +
                ", getParam='" + getParam + '\'' +
                '}';
    }
}
