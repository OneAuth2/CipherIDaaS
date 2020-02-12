package com.portal.domain;


import java.io.Serializable;

public class IdentityAuthentication implements Serializable {
    private String companyUuid;
    private String authMethod;
    private int otherAuthSf;
    private int otherAuthDd;
    private int otherAuthDb;
    private int otherAuthNum;
    private int otherAuthDt;
    private int otherAuthWx;
    private int firstLogin;
    private int infoCollection;

   private int infoCollectionMail;//强制收集邮箱


    public int getOtherAuthWx() {
        return otherAuthWx;
    }

    public void setOtherAuthWx(int otherAuthWx) {
        this.otherAuthWx = otherAuthWx;
    }

    public int getInfoCollectionMail() {
        return infoCollectionMail;
    }

    public void setInfoCollectionMail(int infoCollectionMail) {
        this.infoCollectionMail = infoCollectionMail;
    }

    public String getAuthMethod() {
        return authMethod;
    }

    public void setAuthMethod(String authMethod) {
        this.authMethod = authMethod;
    }

    public int getOtherAuthSf() {
        return otherAuthSf = 1;
    }

    public void setOtherAuthSf(int otherAuthSf) {
        this.otherAuthSf = otherAuthSf;
    }

    public int getOtherAuthDd() {
        return otherAuthDd;
    }

    public void setOtherAuthDd(int otherAuthDd) {
        this.otherAuthDd = otherAuthDd;
    }

    public int getOtherAuthDb() {
        return otherAuthDb;
    }

    public void setOtherAuthDb(int otherAuthDb) {
        this.otherAuthDb = otherAuthDb;
    }

    public int getOtherAuthNum() {
        return otherAuthNum;
    }

    public void setOtherAuthNum(int otherAuthNum) {
        this.otherAuthNum = otherAuthNum;
    }

    public int getFirstLogin() {
        return firstLogin;
    }

    public void setFirstLogin(int firstLogin) {
        this.firstLogin = firstLogin;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public int getInfoCollection() {
        return infoCollection;
    }

    public void setInfoCollection(int infoCollection) {
        this.infoCollection = infoCollection;
    }

    public int getOtherAuthDt() {
        return otherAuthDt;
    }

    public void setOtherAuthDt(int otherAuthDt) {
        this.otherAuthDt = otherAuthDt;
    }

    @Override
    public String toString() {
        return "IdentityAuthentication{" +
                "companyUuid='" + companyUuid + '\'' +
                ", authMethod='" + authMethod + '\'' +
                ", otherAuthSf=" + otherAuthSf +
                ", otherAuthDd=" + otherAuthDd +
                ", otherAuthDb=" + otherAuthDb +
                ", otherAuthNum=" + otherAuthNum +
                ", otherAuthDt=" + otherAuthDt +
                ", firstLogin=" + firstLogin +
                ", infoCollection=" + infoCollection +
                ", infoCollectionMail=" + infoCollectionMail +
                '}';
    }
}
