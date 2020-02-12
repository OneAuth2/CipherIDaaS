package com.portal.domain;

public class UserGroupMap {

    private Integer groupId;

    private String accountNumber;

    public UserGroupMap(String accountNumber) {
        this.accountNumber = accountNumber;
        this.groupId = 0;
    }

    public UserGroupMap() {
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "UserGroupMap{" +
                "groupId=" + groupId +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
