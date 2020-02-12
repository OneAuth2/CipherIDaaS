package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: 安歌
 * @Date: 2019/9/23 10:59
 */
public class NcReqInfo implements Serializable {

    private String  userCode; //请求用友的用户标识

    private String  ssoKey; //请求成功用友的SSOKEY

    private String busiCenter; //帐套编码

    private String groupCode; //登录的集团编码

    private String langCode ; //登录的语言 默认为中文

    public NcReqInfo() {
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getSsoKey() {
        return ssoKey;
    }

    public void setSsoKey(String ssoKey) {
        this.ssoKey = ssoKey;
    }

    public String getBusiCenter() {
        return busiCenter;
    }

    public void setBusiCenter(String busiCenter) {
        this.busiCenter = busiCenter;
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

    @Override
    public String toString() {
        return "NcResInfo{" +
                "userCode='" + userCode + '\'' +
                ", ssoKey='" + ssoKey + '\'' +
                ", busiCenter='" + busiCenter + '\'' +
                ", groupCode='" + groupCode + '\'' +
                ", langCode='" + langCode + '\'' +
                '}';
    }
}
