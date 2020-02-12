package com.portal.model;

import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.PortalApplyInfo;

import java.io.Serializable;

public class GroupApplicationModel extends PortalApplyInfo implements Serializable{

    private int groupId;
    private String groupName;
    private int parentGroupId;
    private String applicationId;
    private String applicationName;
    private String subAccount;
    private String applicationUrl;
    private int applicationType;
    private String applicationDescription;


    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    @Override
    public int getApplicationType() {
        return applicationType;
    }

    @Override
    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public GroupApplicationModel() {
    }

    public GroupApplicationModel(String applicationId, String applicationName, String subAccount) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.subAccount = subAccount;
    }

    public GroupApplicationModel(ApplicationInfoDomain applicationInfoDomain) {
        this(applicationInfoDomain.getApplicationId(),applicationInfoDomain.getApplicationName(),applicationInfoDomain.getSubAccount());
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

  /*  @Override
    public String toString() {
        return "GroupApplicationModel{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", parentGroupId=" + parentGroupId +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", subAccount='" + subAccount + '\'' +
                '}';
    }*/
}
