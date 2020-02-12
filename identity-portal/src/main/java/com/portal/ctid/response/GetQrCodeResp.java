package com.portal.ctid.response;

import com.portal.ctid.pojo.TokenInfo;

public class GetQrCodeResp implements BaseResponse {

    private String apiVersion;

    private int retCode = -1;

    private String qrcodeImage;

    private String retMessage;

    private TokenInfo tokenInfo;


    @Override
    public boolean isSuccess() {
        if (retCode == 0){
            return true;
        }
        return false;
    }

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


    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public String getQrcodeImage() {
        return qrcodeImage;
    }

    public void setQrcodeImage(String qrcodeImage) {
        this.qrcodeImage = qrcodeImage;
    }

    @Override
    public String toString() {
        return "GetQrCodeResp{" +
                "apiVersion='" + apiVersion + '\'' +
                ", retCode=" + retCode +
                ", retMessage='" + retMessage + '\'' +
                ", tokenInfo=" + tokenInfo +
                '}';
    }
}
