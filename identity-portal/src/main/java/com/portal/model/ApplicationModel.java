package com.portal.model;

import java.io.Serializable;

/**
 * Created by ly on 2018/10/14.
 */
public class ApplicationModel implements Serializable {
    private String applicationId;
    private String subAccount;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    @Override
    public String toString() {
        return "ApplicationModel{" +
                "applicationId='" + applicationId + '\'' +
                ", subAccount='" + subAccount + '\'' +
                '}';
    }
}
