package cipher.console.oidc.domain.web;

import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/9/26 20:23
 */
public class DatailCasualUserInfo  {
    private String userName;
    private String accountNumber;//主场号
    private String applications;//应用
   private String nickName;//昵称
    private String phoneNumber;//电话
    private String mail;//电子邮箱
    private String job;//工作
    private String gender;//性别
    private Date  accountAuthorizedDate;//账号授权时间
    private Date accountAuthorizedValidDate;//账号截至日期

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getApplications() {
        return applications;
    }

    public void setApplications(String applications) {
        this.applications = applications;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    @Override
    public String toString() {
        return "DatailCasualUserInfo{" +
                ", accountNumber='" + accountNumber + '\'' +
                ", applications='" + applications + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", job='" + job + '\'' +
                ", gender='" + gender + '\'' +
                ", accountAuthorizedDate=" + accountAuthorizedDate +
                ", accountAuthorizedValidDate=" + accountAuthorizedValidDate +
                '}';
    }
}
