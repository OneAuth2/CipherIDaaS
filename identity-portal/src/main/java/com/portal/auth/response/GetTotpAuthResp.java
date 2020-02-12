package com.portal.auth.response;


import com.portal.ctid.response.BaseResponse;

public class GetTotpAuthResp implements BaseResponse {

    private String respMessage;
    private int respCode;

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    @Override
    public boolean isSuccess() {
        if (respCode == 0){
            return true;
        }
        return false;
    }
    }

