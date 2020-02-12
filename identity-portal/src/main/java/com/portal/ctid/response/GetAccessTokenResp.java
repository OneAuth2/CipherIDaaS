package com.portal.ctid.response;

public class GetAccessTokenResp implements BaseResponse {

    private String apiVersion;

    private int retCode = 1;

    private String retMessage;

    private String accessToken;

    private String expireSeconds;

    private String timestamp;


    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public int getRetCode() {
        return retCode;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public String getRetMessage() {
        return retMessage;
    }

    public void setRetMessage(String retMessage) {
        this.retMessage = retMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(String expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GetAccessTokenResp{" +
                "apiVersion='" + apiVersion + '\'' +
                ", retCode='" + retCode + '\'' +
                ", retMessage='" + retMessage + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expireSeconds='" + expireSeconds + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean isSuccess() {
        if (retCode == 0){
            return true;
        }
        return false;
    }
}
