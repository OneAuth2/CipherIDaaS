package com.portal.domain;


import java.io.Serializable;

public class BaseParamDomain implements Serializable {

    private String userId;
    private String companyId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
