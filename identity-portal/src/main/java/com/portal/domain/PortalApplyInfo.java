package com.portal.domain;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/5/21.
 */
@Data
public class PortalApplyInfo implements Serializable {

  private Integer id;
  private String applyName;
  private String state;
  private String clientSecret;
  private String clientId;
  private String redirectUrl;
  private String applyUrl;
  private String userName;
  private String subAccount;
  private int applicationType;
  private String applicationAuthUrl;
  private String jsMethod;
  private String mail;
  private String reqUrl;
  private String configInfo;
  private String dsgApiUrl;
  private Integer isSameAccount;
  private String xdsgUrl;

  private String associatedAccount;

    private String server;
    private String data;
    private int pos;
    private String appSysId;

    //指定登录的集团编码
    private String groupCode;
    //指定登录的y语言 默认为中文
    private String langCode;

    public String getAppSysId() {
        return appSysId;
    }

    public void setAppSysId(String appSysId) {
        this.appSysId = appSysId;
    }

    public PortalApplyInfo(String clientId) {
        this.clientId = clientId;
    }

    public PortalApplyInfo() {
    }
    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getAssociatedAccount() {
        return associatedAccount;
    }

    public void setAssociatedAccount(String associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public String getXdsgUrl() {
        return xdsgUrl;
    }

    public void setXdsgUrl(String xdsgUrl) {
        this.xdsgUrl = xdsgUrl;
    }

    public String getDsgApiUrl() {
        return dsgApiUrl;
    }

    public void setDsgApiUrl(String dsgApiUrl) {
        this.dsgApiUrl = dsgApiUrl;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getApplicationAuthUrl() {
        return applicationAuthUrl;
    }

    public void setApplicationAuthUrl(String applicationAuthUrl) {
        this.applicationAuthUrl = applicationAuthUrl;
    }

    public String getJsMethod() {
        return jsMethod;
    }

    public void setJsMethod(String jsMethod) {
        this.jsMethod = jsMethod;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    private String imgUrl;

    public String getApplyUrl() {
        return applyUrl;
    }

    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsSameAccount() {
        return isSameAccount;
    }

    public void setIsSameAccount(Integer isSameAccount) {
        this.isSameAccount = isSameAccount;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "PortalApplyInfo{" +
                "id=" + id +
                ", applyName='" + applyName + '\'' +
                ", state='" + state + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", clientId='" + clientId + '\'' +
                ", redirectUrl='" + redirectUrl + '\'' +
                ", applyUrl='" + applyUrl + '\'' +
                ", userName='" + userName + '\'' +
                ", subAccount='" + subAccount + '\'' +
                ", applicationType=" + applicationType +
                ", applicationAuthUrl='" + applicationAuthUrl + '\'' +
                ", jsMethod='" + jsMethod + '\'' +
                ", mail='" + mail + '\'' +
                ", reqUrl='" + reqUrl + '\'' +
                ", configInfo='" + configInfo + '\'' +
                ", dsgApiUrl='" + dsgApiUrl + '\'' +
                ", isSameAccount=" + isSameAccount +
                ", xdsgUrl='" + xdsgUrl + '\'' +
                ", associatedAccount='" + associatedAccount + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
