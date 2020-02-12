package com.portal.domain;

import java.io.Serializable;
import java.util.Date;

public class UserSessionDomain implements Serializable {

  private String userName;
  private Date expireTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
