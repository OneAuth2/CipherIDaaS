package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/10/14
 *
 * @author liuying
 * @since 2019/10/14 16:52
 */
public class BindWxInfoDomain implements Serializable {

    private String userId;
    private String wxUserId;
    private String companyId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "BindWxInfoDomain{" +
                "userId='" + userId + '\'' +
                ", wxUserId='" + wxUserId + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
