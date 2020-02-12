package com.portal.domain;


import java.io.Serializable;

public class GatewayRequest implements Serializable {

   private String accountNumber;
   private String token;
   private String appId;
   private String whichService;

    public GatewayRequest(String accountNumber, String token, String appId, String whichService) {
        this.accountNumber = accountNumber;
        this.token = token;
        this.appId = appId;
        this.whichService = whichService;
    }

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

    public String getWhichService() {
        return whichService;
    }

    public void setWhichService(String whichService) {
        this.whichService = whichService;
    }


    @Override
    public String toString() {
        return "GatewayRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", token='" + token + '\'' +
                ", appId='" + appId + '\'' +
                ", whichService='" + whichService + '\'' +
                '}';
    }
}
