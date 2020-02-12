package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

public class UserLoginRecInfo implements Serializable {

    //数据的唯一ID
    private Integer id;
    //用户名
    private String accountNumber;
    //首次登陆时间
    private String firstFaceLoginTime;
    //用户的唯一ID
    private String uuid;
    //数据创建时间
    private Date createTime;
    //数据修改时间
    private Date modifyTime;
    //用户的唯一ID
    private String userId;

    public UserLoginRecInfo() {
    }

    public UserLoginRecInfo(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstFaceLoginTime() {
        return firstFaceLoginTime;
    }

    public void setFirstFaceLoginTime(String firstFaceLoginTime) {
        this.firstFaceLoginTime = firstFaceLoginTime;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
