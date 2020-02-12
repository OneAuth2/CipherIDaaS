package com.portal.domain;


import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/7/21.
 */
public class SubClientInfoDomain implements Serializable{

    private String subAccount;
    private String  clientId;
    private String  password;
    private String accountNumber;
    private int subId;
    private String applyId;


    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void passwordDecrypt(){
        if (StringUtils.isNotBlank(password)&&StringUtils.isNotBlank(password.replace(" ",""))){
           //System.out.println("before:"+password);
            password = AES.decryptFromBase64(password, AesKey.AES_KEY);
           // System.out.println("after:"+password);
        }
    }

    public void passwordEncrypt(){
        if (StringUtils.isNotBlank(password)&&StringUtils.isNotBlank(password.replace(" ",""))){
           // System.out.println("before:"+password);
            password = AES.encryptToBase64(password,AesKey.AES_KEY);
            //System.out.println("after:"+password);
        }
    }

    @Override
    public String toString() {
        return "SubClientInfoDomain{" +
                "subAccount='" + subAccount + '\'' +
                ", clientId='" + clientId + '\'' +
                ", password='" + password + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", subId=" + subId +
                ", applyId='" + applyId + '\'' +
                '}';
    }
}
