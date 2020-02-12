package com.portal.domain;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * @Author: 安歌
 * @Date: 2019/7/24 9:38
 */
public class RdsgDomain implements Serializable {

    /** 用户的userId*/
    private String userId;

    /** 应用的id*/
    private String appId;

    /** 公司id*/
    private String companyUuid;

    /** http返回*/
    private HttpServletResponse response;

    /** 前端地址*/
    private String platformUrl;

    /** appHost保存之后跳转接口*/
    private  String appHost;

    /** 应用的applyId*/
    private String applicationId;

    public RdsgDomain(String userId, String appId, String companyUuid, HttpServletResponse response, String platformUrl, String appHost, String applicationId) {
        this.userId = userId;
        this.appId = appId;
        this.companyUuid = companyUuid;
        this.response = response;
        this.platformUrl = platformUrl;
        this.appHost = appHost;
        this.applicationId = applicationId;
    }

    public RdsgDomain() {
    }

    @Override
    public String toString() {
        return "RdsgDomain{" +
                "userId='" + userId + '\'' +
                ", appId='" + appId + '\'' +
                ", companyUuid='" + companyUuid + '\'' +
                ", response=" + response +
                ", platformUrl='" + platformUrl + '\'' +
                ", appHost='" + appHost + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getPlatformUrl() {
        return platformUrl;
    }

    public void setPlatformUrl(String platformUrl) {
        this.platformUrl = platformUrl;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
