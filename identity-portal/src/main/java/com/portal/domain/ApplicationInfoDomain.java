package com.portal.domain;



import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

public class ApplicationInfoDomain implements Serializable{

    private Integer id;

    /**
     * 应用的全局唯一id
     */
    private String applicationId;
    /**
     *应用名称
     */
    private String applicationName;

    /**
     * 应用描述
     */
    private String applicationDescription;
    /**
     * 应用授权开始日期
     */
    private Date applicationAuthorizedDate;

    /**
     * 应用授权结束日期
     */
    private Date applicationAuthorizedValidDate;

    /**
     * 应用的首页地址,以htp://开头的绝对地址
     */
    private String applicationUrl;

    /**
     * 应用端口
     */
    private Integer applicationPortId;

    /**
     * xdsg的地址
     */
    private String xDsgUrl;

    /**
     * 应用图标地址
     */
    private String applicationIconUrl;

    private String configInfo;


    /**
     * 应用状态，1可用，2.禁用
     */
    private String applicationStatus;


    /**
     * 一个应用中所有的组的拼接
     */
    private String concatName;


    /**
     * 能看到该应用的人数的统计
     */
    private int userCount;


    /**
     * 创建人的账号
     */
    private String accountNumber;

    private Date createTime;

    private Date modifyTime;


    private String applicationRedirctUrl;


    private String applicationSecrect;

    private int applicationType;

    private String sord;
    private String sidx;

    private String subAccount;

    private String accountType;

    private String  relayState;

    private String assertionConsumerServiceUrl;

    private String  exitTime;

    private String entityId;

    private String privateKey;

    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getRelayState() {
        return relayState;
    }

    public void setRelayState(String relayState) {
        this.relayState = relayState;
    }

    public String getAssertionConsumerServiceUrl() {
        return assertionConsumerServiceUrl;
    }

    public void setAssertionConsumerServiceUrl(String assertionConsumerServiceUrl) {
        this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSubAccount() {
        if (StringUtils.isNotBlank(subAccount)){
            return subAccount;
        }
        return "";
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName == null ? null : applicationName.trim();
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription == null ? null : applicationDescription.trim();
    }

    public Date getApplicationAuthorizedDate() {
        return applicationAuthorizedDate;
    }

    public void setApplicationAuthorizedDate(Date applicationAuthorizedDate) {
        this.applicationAuthorizedDate = applicationAuthorizedDate;
    }

    public Date getApplicationAuthorizedValidDate() {
        return applicationAuthorizedValidDate;
    }

    public void setApplicationAuthorizedValidDate(Date applicationAuthorizedValidDate) {
        this.applicationAuthorizedValidDate = applicationAuthorizedValidDate;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl == null ? null : applicationUrl.trim();
    }

    public Integer getApplicationPortId() {
        return applicationPortId;
    }

    public void setApplicationPortId(Integer applicationPortId) {
        this.applicationPortId = applicationPortId;
    }

    public String getApplicationIconUrl() {
        return applicationIconUrl;
    }

    public void setApplicationIconUrl(String applicationIconUrl) {
        this.applicationIconUrl = applicationIconUrl == null ? null : applicationIconUrl.trim();
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
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

    public String getApplicationRedirctUrl() {
        return applicationRedirctUrl;
    }

    public void setApplicationRedirctUrl(String applicationRedirctUrl) {
        this.applicationRedirctUrl = applicationRedirctUrl;
    }

    public String getApplicationSecrect() {
        return applicationSecrect;
    }

    public void setApplicationSecrect(String applicationSecrect) {
        this.applicationSecrect = applicationSecrect;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    @Override
    public String toString() {
        return "ApplicationInfoDomain{" +
                "id=" + id +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationDescription='" + applicationDescription + '\'' +
                ", applicationAuthorizedDate=" + applicationAuthorizedDate +
                ", applicationAuthorizedValidDate=" + applicationAuthorizedValidDate +
                ", applicationUrl='" + applicationUrl + '\'' +
                ", applicationPortId=" + applicationPortId +
                ", xDsgUrl='" + xDsgUrl + '\'' +
                ", applicationIconUrl='" + applicationIconUrl + '\'' +
                ", configInfo='" + configInfo + '\'' +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", concatName='" + concatName + '\'' +
                ", userCount=" + userCount +
                ", accountNumber='" + accountNumber + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", applicationRedirctUrl='" + applicationRedirctUrl + '\'' +
                ", applicationSecrect='" + applicationSecrect + '\'' +
                ", applicationType=" + applicationType +
                ", sord='" + sord + '\'' +
                ", sidx='" + sidx + '\'' +
                ", subAccount='" + subAccount + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }

    public String getxDsgUrl() {
        return xDsgUrl;
    }

    public void setxDsgUrl(String xDsgUrl) {
        this.xDsgUrl = xDsgUrl;
    }
}
