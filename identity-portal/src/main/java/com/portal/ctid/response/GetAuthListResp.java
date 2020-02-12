package com.portal.ctid.response;

public class GetAuthListResp implements BaseResponse {

    private String apiVersion;

    private int retCode;

    private String retMessage;

    private String authData;

    private int authMode;

    private int resCode;

    private String resStr;

    private String portrait;

    private String authObject;

    private String idNumber;

    private String fullName;

    private String idStartDate;

    private String idEndDate;

    @Override
    public boolean isSuccess() {
        if (resCode == 0){
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

    public String getErrorMsg() {
        return retMessage;
    }

    public void setErrorMsg(String errorMsg) {
        this.retMessage = errorMsg;
    }

    public String getAuthData() {
        return authData;
    }

    public void setAuthData(String authData) {
        this.authData = authData;
    }

    public int getAuthMode() {
        return authMode;
    }

    public void setAuthMode(int authMode) {
        this.authMode = authMode;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResStr() {
        return resStr;
    }

    public void setResStr(String resStr) {
        this.resStr = resStr;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getAuthObject() {
        return authObject;
    }

    public void setAuthObject(String authObject) {
        this.authObject = authObject;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdStartDate() {
        return idStartDate;
    }

    public void setIdStartDate(String idStartDate) {
        this.idStartDate = idStartDate;
    }

    public String getIdEndDate() {
        return idEndDate;
    }

    public void setIdEndDate(String idEndDate) {
        this.idEndDate = idEndDate;
    }

    @Override
    public String toString() {
        return "GetAuthListResp{" +
                "apiVersion='" + apiVersion + '\'' +
                ", retCode=" + retCode +
                ", errorMsg='" + retMessage + '\'' +
                ", authData='" + authData + '\'' +
                ", authMode=" + authMode +
                ", resCode=" + resCode +
                ", resStr='" + resStr + '\'' +
                ", portrait='" + portrait + '\'' +
                ", authObject='" + authObject + '\'' +
                ", idNumber='" + idNumber + '\'' +
                ", fullName='" + fullName + '\'' +
                ", idStartDate='" + idStartDate + '\'' +
                ", idEndDate='" + idEndDate + '\'' +
                '}';
    }
}
