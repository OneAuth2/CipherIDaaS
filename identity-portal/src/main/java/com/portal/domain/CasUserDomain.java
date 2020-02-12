package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: 安歌
 * @Date: 2019/7/25 14:31
 */
public class CasUserDomain implements Serializable {

    /** 用户的唯一标识 **/
    private String uuid;

    /**  子账号的账号 **/
    private String subAccount;

    public CasUserDomain(String uuid, String subAccount) {
        this.uuid = uuid;
        this.subAccount = subAccount;
    }

    public CasUserDomain() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    @Override
    public String toString() {
        return "CasUserDomain{" +
                "uuid='" + uuid + '\'' +
                ", subAccount='" + subAccount + '\'' +
                '}';
    }
}
