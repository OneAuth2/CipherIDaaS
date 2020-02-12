package com.portal.domain;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseQRCode implements Serializable{

    int status;//最终状态：0-完全正确，1-扫码失败

    int ErrorCode;//0-无错误，1-超时，2-数据错误

    String ErrorMessage;//错误信息

    long expiresIn;

    String qrCodeURL;

    String uuid;

    public ResponseQRCode() {
    }

    public ResponseQRCode(int status, int errorCode, String errorMessage, long expiresIn, String qrCodeURL,String uuid) {
        this.status = status;
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        this.expiresIn = expiresIn;
        this.qrCodeURL = qrCodeURL;
        this.uuid = uuid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }


    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getQrCodeURL() {
        return qrCodeURL;
    }

    public void setQrCodeURL(String qrCodeURL) {
        this.qrCodeURL = qrCodeURL;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "ResponseQRCode{" +
                "status=" + status +
                ", ErrorCode=" + ErrorCode +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", expiresIn=" + expiresIn +
                ", qrCodeURL='" + qrCodeURL + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
