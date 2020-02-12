package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/9/20
 *
 * @author liuying
 * @since 2019/9/20 15:28
 */
public class OtpDynamicInfo implements Serializable {

    private String userId;
    private String dynamicPassword;
    private String uuid;
    private String companyId;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDynamicPassword() {
        return dynamicPassword;
    }

    public void setDynamicPassword(String dynamicPassword) {
        this.dynamicPassword = dynamicPassword;
    }

    public OtpDynamicInfo() {
    }

    public OtpDynamicInfo(String userId, String dynamicPassword, String uuid, String companyId) {
        this.userId = userId;
        this.dynamicPassword = dynamicPassword;
        this.uuid = uuid;
        this.companyId = companyId;
    }
}
