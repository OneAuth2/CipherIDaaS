package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/5/31 18:35
 */
public class SubAccountDomain implements Serializable {

    private Integer id;

    /**
     * 子账号
     */
    private String subAccount;

    private String subPwd;

    /**
     * 应用唯一id
     */
    private String appClientId;

    private Date createTime;

    private Date modifyTime;


    private String password;


    private String accountNumber;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSubPwd() {
        return subPwd;
    }

    public void setSubPwd(String subPwd) {
        this.subPwd = subPwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
