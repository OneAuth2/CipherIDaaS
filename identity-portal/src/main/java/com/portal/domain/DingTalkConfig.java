package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @author shizhao
 */
public class DingTalkConfig implements Serializable{
    //配置信息ID
    private String id;

    //公司ID
    private String companyUUid;

    //扫码ID
    private String scanAppId;

    //扫码KEY
    private String scanAppSecret;

    //公司ID
    private String corpId;

    //公司secret
    private String corpSecret;

    //应用KEY
    private String appKey;

    //应用Secret
    private String appSecret;

    //属性映射
    private String attrMap;

    //匹配规则
    private int matchRule;

    //创建时间
    private Date createTime;

    //修改时间
    private Date modifyTime;

    private String callbackUrl;

    private String authUrl;

    private String application;

    private String applicationName;

    private String configId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyUUid() {
        return companyUUid;
    }

    public void setCompanyUUid(String companyUUid) {
        this.companyUUid = companyUUid;
    }

    public String getScanAppId() {
        return scanAppId;
    }

    public void setScanAppId(String scanAppId) {
        this.scanAppId = scanAppId;
    }

    public String getScanAppSecret() {
        return scanAppSecret;
    }

    public void setScanAppSecret(String scanAppSecret) {
        this.scanAppSecret = scanAppSecret;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(String attrMap) {
        this.attrMap = attrMap;
    }

    public int getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(int matchRule) {
        this.matchRule = matchRule;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "DingTalkConfig{" +
                "id='" + id + '\'' +
                ", companyUUid='" + companyUUid + '\'' +
                ", scanAppId='" + scanAppId + '\'' +
                ", scanAppSecret='" + scanAppSecret + '\'' +
                ", corpId='" + corpId + '\'' +
                ", corpSecret='" + corpSecret + '\'' +
                ", appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", attrMap='" + attrMap + '\'' +
                ", matchRule=" + matchRule +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
