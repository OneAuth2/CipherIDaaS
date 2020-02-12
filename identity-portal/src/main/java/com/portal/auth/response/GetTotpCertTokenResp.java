package com.portal.auth.response;


import com.portal.ctid.response.BaseResponse;

public class GetTotpCertTokenResp implements BaseResponse {

   private String respMessage;
   private String certToken;
   private int respCode=0;


    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getCertToken() {
        return certToken;
    }

    public void setCertToken(String certToken) {
        this.certToken = certToken;
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


    @Override
    public String toString() {
        return "GetTotpCertTokenResp{" +
                "respMessage='" + respMessage + '\'' +
                ", certToken='" + certToken + '\'' +
                ", respCode=" + respCode +
                '}';
    }
}



