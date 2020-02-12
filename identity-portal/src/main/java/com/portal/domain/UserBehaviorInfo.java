package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

public class UserBehaviorInfo implements Serializable {
    private Integer id;

    private String  userId;

    private Integer groupId;

    private Integer type;

    private String typeStr;

    private String ip;

    private String msg;

    private Date createTime;

    private String queryName;

    private String startTime;

    private String endTime;


    private String userName;
    private String nickName;
    private String mail;
    private String phoneNumber;
    private String groupName;
    private String userInfo;


    private String operation;

    private String companyId;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public UserBehaviorInfo(String userId, Integer groupId, Integer type, String ip, String msg, String operation, Date createTime,String companyId) {
        this.userId = userId;
        this.groupId = groupId;
        this.type = type;
        this.ip = ip;
        this.msg = msg;
        this.createTime = createTime;
        this.operation = operation;
        this.companyId=companyId;
    }
    public UserBehaviorInfo(String userId, Integer groupId, Integer type, String ip, String msg,String companyId) {
        this.userId = userId;
        this.groupId = groupId;
        this.type = type;
        this.ip = ip;
        this.msg = msg;
        this.companyId=companyId;
    }


    public UserBehaviorInfo() {
    }
}