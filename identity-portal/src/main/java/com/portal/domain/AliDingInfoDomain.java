package com.portal.domain;


import java.io.Serializable;

public class AliDingInfoDomain implements Serializable{

  private int id;
  private String openId;
  private String accountNumber;
  private String  unionId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }


    @Override
    public String toString() {
        return "AliDingInfoDomain{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", unionId='" + unionId + '\'' +
                '}';
    }
}
