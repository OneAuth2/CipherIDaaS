package com.portal.auth.response;


import com.portal.ctid.response.BaseResponse;

public class GetAuthAccessTokenResp implements BaseResponse {

    private String respMessage;
    private String expireSeconds;
    private String accessToken;
       private int respCode=0;
    private String timestamp;

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(String expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GetAuthAccessTokenResp{" +
                "respMessage='" + respMessage + '\'' +
                ", expireSeconds='" + expireSeconds + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", respCode=" + respCode +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public boolean isSuccess() {
        if (respCode == 0){
            return true;
        }
        return false;
    }
    }


