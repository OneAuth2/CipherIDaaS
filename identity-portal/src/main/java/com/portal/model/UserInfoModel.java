package com.portal.model;



import java.io.Serializable;
import java.util.List;

public class UserInfoModel implements Serializable{

    private String accountNumber;
    private String nickname;
    private String phoneNumber;
    private String mail;
    private String gender;
    private String start;
    private String end;
    private int groupId;
    private String job;
    private Integer isAdmin;
    private String groupNames;
    private List<ApplicationModel> applist;

    public UserInfoModel() {
    }

    public UserInfoModel(String accountNumber, int groupId) {
        this.accountNumber = accountNumber;
        this.groupId = groupId;
    }

    public List<ApplicationModel> getApplist() {
        return applist;
    }

    public void setApplist(List<ApplicationModel> applist) {
        this.applist = applist;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "accountNumber='" + accountNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", gender='" + gender + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
