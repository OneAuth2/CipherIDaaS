package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class UserInfoDomain extends BaseDomain {


    private String idNum;
    private String uuid;


    private String accountNumber;

    private int teamId;

    /**
     * 会话的sessionId
     */
    private String sessionId;

    /**
     * 访问的ip
     */
    private String ip;

    /**
     * 账号状态：
     * 1表示有效,2:表示无效
     */
    private String accountStatus;


    private String nickname;

    private String mail;

    private String phoneNumber;

    private String job;
    private String jobNo;//工号
    private String birthday;//生日

    private String password;
    /**
     * 授权有效其开始时间
     */
    private Date accountAuthorizedDate;

    /**
     * 授权有效期结束时间
     */
    private Date accountAuthorizedValidDate;


    private Date createTime;

    private Date modifyTime;

    private String gender;

    private Integer isAdmin;

    private Integer deviceLimit;

    private Integer limitOpen;

    private String groupName;

    private String groupNames;

    private String source;

    private String userName;

    private String objectGUID;

    private String queryName;

    private String status;

    private Integer isDelete;

    /**
     * 钉钉userid
     */
    private String dingUserId;

    /**
     * 钉钉unionId
     */
    private String dingUnionId;


    /**
     * 企业微信userid
     */
    private String wxUserId;
    /**
     * Ad账号策略
     */
    private int userAccountControl;

    private Integer issueStatus;




    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(int userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

// private String jobNo;
    public String getWxUserId() {
    return wxUserId;
}

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
    }
    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObjectGUID() {
        return objectGUID;
    }

    public void setObjectGUID(String objectGUID) {
        this.objectGUID = objectGUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public UserInfoDomain() {
    }

    public UserInfoDomain(String accountNumber, Integer deviceLimit, Integer limitOpen) {
        this.accountNumber = accountNumber;
        this.deviceLimit = deviceLimit;
        this.limitOpen = limitOpen;
    }

    public void setAccountAuthorizedValidDate(Date accountAuthorizedValidDate) {
        this.accountAuthorizedValidDate = accountAuthorizedValidDate;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAccountAuthorizedDate() {
        return accountAuthorizedDate;
    }


    public void setAccountAuthorizedDate(Date accountAuthorizedDate) {
        this.accountAuthorizedDate = accountAuthorizedDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAccountAuthorizedValidDate() {
        return accountAuthorizedValidDate;
    }


    public void setAccountAuthorizedVlidDate(Date accountAuthorizedVlidDate) {
        this.accountAuthorizedValidDate = accountAuthorizedVlidDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDeviceLimit() {
        return deviceLimit;
    }

    public void setDeviceLimit(Integer deviceLimit) {
        this.deviceLimit = deviceLimit;
    }

    public Integer getLimitOpen() {
        return limitOpen;
    }

    public void setLimitOpen(Integer limitOpen) {
        this.limitOpen = limitOpen;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }


    public String getDingUnionId() {
        return dingUnionId;
    }

    public void setDingUnionId(String dingUnionId) {
        this.dingUnionId = dingUnionId;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public Integer getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Integer issueStatus) {
        this.issueStatus = issueStatus;
    }

    @Override
    public String toString() {
        return "UserInfoDomain{" +
                "uuid='" + uuid + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", teamId=" + teamId +
                ", sessionId='" + sessionId + '\'' +
                ", ip='" + ip + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", job='" + job + '\'' +
                ", jobNo='" + jobNo + '\'' +
                ", birthday='" + birthday + '\'' +
                ", accountAuthorizedDate=" + accountAuthorizedDate +
                ", accountAuthorizedValidDate=" + accountAuthorizedValidDate +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", gender='" + gender + '\'' +
                ", isAdmin=" + isAdmin +
                ", deviceLimit=" + deviceLimit +
                ", limitOpen=" + limitOpen +
                ", groupName='" + groupName + '\'' +
                ", groupNames='" + groupNames + '\'' +
                ", source='" + source + '\'' +
                ", userName='" + userName + '\'' +
                ", objectGUID='" + objectGUID + '\'' +
                ", queryName='" + queryName + '\'' +
                ", status='" + status + '\'' +
                ", isDelete=" + isDelete +
                ", userAccountControl=" + userAccountControl +
                ",dingUserId=" + dingUserId +
                '}';
    }
}
