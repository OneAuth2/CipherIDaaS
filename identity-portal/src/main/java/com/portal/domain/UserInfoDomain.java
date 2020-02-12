package com.portal.domain;


import org.apache.commons.lang3.StringUtils;

import java.util.Date;

;

public class UserInfoDomain extends BaseParamDomain{

    private String accountNumber;

    //用户的唯一ID
    private String uuid;

    /**
     * 账号状态：
     * 1表示有效,2:表示无效
     */
    private String accountStatus;

    private String nickname;
    private String mail;
    private String phoneNumber;
    private String userName;
    private String password;
    private String newPwd;
    private String groupId;
    private Integer isAdmin;
    private String userId;

    /**
     * 账号授权日期
     */
    private Date accountAuthorizedDate;

    /**
     * 账号授权有效期
     */
    private Date accountAuthorizedValidDate;

    /**
     * 账号是否已经删除 1-删除，0-未删除
     * */
    private int isDelete;

    /**
     * 账号来源
     */
    private String source;

    /**
     * Ad账号策略
     */
    private int userAccountControl;

    /**
     * 用户的公司ID
     * */
    private String companyId;


    /**
     * 赛赋认证ID
     * */
    private Integer platId;

    //钉钉账号内的唯一ID
    private String dingUnionId;

    //钉钉公司内的用户ID
    private String dingUserId;

    private String groupName;

    private String idNum;

    private String job;

    private String upn;

    private String adIp;

    private int adPort;

    private String adUserName;

    private String jobNo;

    private Integer issueStatus;

    public Integer getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Integer issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getUpn() {
        return upn;
    }

    public void setUpn(String upn) {
        this.upn = upn;
    }

    public String getAdIp() {
        return adIp;
    }

    public void setAdIp(String adIp) {
        this.adIp = adIp;
    }

    public int getAdPort() {
        return adPort;
    }

    public void setAdPort(int adPort) {
        this.adPort = adPort;
    }

    public String getAdUserName() {
        return adUserName;
    }

    public void setAdUserName(String adUserName) {
        this.adUserName = adUserName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public UserInfoDomain() {
    }

    public UserInfoDomain(String accountNumber, String companyId) {
        this.accountNumber = accountNumber;
        this.companyId = companyId;
    }


    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getAccountAuthorizedDate() {
        return accountAuthorizedDate;
    }

    public void setAccountAuthorizedDate(Date accountAuthorizedDate) {
        this.accountAuthorizedDate = accountAuthorizedDate;
    }

    public Date getAccountAuthorizedValidDate() {
        return accountAuthorizedValidDate;
    }

    public void setAccountAuthorizedValidDate(Date accountAuthorizedValidDate) {
        this.accountAuthorizedValidDate = accountAuthorizedValidDate;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(int userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public boolean isAvailable(){
        //用户已经被删除
        if (isDelete == 1){
            return false;
        }
        //无法判断用户状态
        if (StringUtils.isEmpty(accountStatus)){
            return false;
        }
        //用户的状态不是启用
        if (!accountStatus.equals("启用")){
            return false;
        }
        return true;
    }

    public Integer getPlatId() {
        return platId;
    }

    public void setPlatId(Integer platId) {
        this.platId = platId;
    }

    public String getDingUnionId() {
        return dingUnionId;
    }

    public void setDingUnionId(String dingUnionId) {
        this.dingUnionId = dingUnionId;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public UserInfoDomain(String uuid, String nickname, String mail, String phoneNumber, String userName) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
    }
}
