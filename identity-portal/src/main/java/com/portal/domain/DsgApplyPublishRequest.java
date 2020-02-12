package com.portal.domain;


import java.io.Serializable;

public class DsgApplyPublishRequest implements Serializable{

    private String accountNumber;
    private String token;
    private String appId;
    private String appKey;
    private String teamIdList;
    private String whichService;



    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getTeamIdList() {
        return teamIdList;
    }

    public void setTeamIdList(String teamIdList) {
        this.teamIdList = teamIdList;
    }

    public String getWhichService() {
        return whichService;
    }

    public void setWhichService(String whichService) {
        this.whichService = whichService;
    }

    public DsgApplyPublishRequest(String accountNumber, String appId, String appKey, String whichService) {
        this.accountNumber = accountNumber;
        this.appId = appId;
        this.appKey = appKey;
        this.whichService = whichService;
    }


    public DsgApplyPublishRequest(String accountNumber, String appId, String appKey, String teamIdList, String whichService) {
        this.accountNumber = accountNumber;
        this.appId = appId;
        this.appKey = appKey;
        this.teamIdList = teamIdList;
        this.whichService = whichService;
    }
}
