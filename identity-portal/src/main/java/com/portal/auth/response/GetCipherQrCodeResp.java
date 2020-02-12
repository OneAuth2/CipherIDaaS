package com.portal.auth.response;



import com.portal.auth.pojo.TokenInfo;
import com.portal.ctid.response.BaseResponse;

public class GetCipherQrCodeResp implements BaseResponse {

   private String qrcodeContent;
   private int expireSeconds;
   private String certToken;
   private String qrcodeImage;
    private TokenInfo tokenInfo;
    private int respCode=0;
    private String respMessage;




    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getQrcodeContent() {
        return qrcodeContent;
    }

    public void setQrcodeContent(String qrcodeContent) {
        this.qrcodeContent = qrcodeContent;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getCertToken() {
        return certToken;
    }

    public void setCertToken(String certToken) {
        this.certToken = certToken;
    }

    public String getQrcodeImage() {
        return qrcodeImage;
    }

    public void setQrcodeImage(String qrcodeImage) {
        this.qrcodeImage = qrcodeImage;
    }

    @Override
    public boolean isSuccess() {
        if (respCode == 0){
            return true;
        }
        return false;
    }



}
