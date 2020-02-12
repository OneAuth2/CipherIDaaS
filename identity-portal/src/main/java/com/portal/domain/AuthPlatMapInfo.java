package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

public class AuthPlatMapInfo implements Serializable {
    private String accountNumber;
    private String platId;
    private Date createTime;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPlatId() {
        return platId;
    }

    public void setPlatId(String platId) {
        this.platId = platId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AuthPlatMapInfo{" +
                "accountNumber='" + accountNumber + '\'' +
                ", platId='" + platId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
