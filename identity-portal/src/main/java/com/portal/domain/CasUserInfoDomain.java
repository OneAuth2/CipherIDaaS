package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: 安歌
 * @Date: 2019/7/25 14:38
 */
public class CasUserInfoDomain implements Serializable {

    /**  姓名 **/
    private String userName;

    /**  工号 **/
    private String jonNo;

    /**  部门 **/
    private String groups;

    /**  岗位 **/
    private String job;

    /**  手机 **/
    private String phone;

    /**  邮箱 **/
    private String mail;

    public CasUserInfoDomain(String userName, String jonNo, String groups, String job, String phone, String mail) {
        this.userName = userName;
        this.jonNo = jonNo;
        this.groups = groups;
        this.job = job;
        this.phone = phone;
        this.mail = mail;
    }

    public CasUserInfoDomain() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJonNo() {
        return jonNo;
    }

    public void setJonNo(String jonNo) {
        this.jonNo = jonNo;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "CasUserInfoDomain{" +
                "userName='" + userName + '\'' +
                ", jonNo='" + jonNo + '\'' +
                ", groups='" + groups + '\'' +
                ", job='" + job + '\'' +
                ", phone='" + phone + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
