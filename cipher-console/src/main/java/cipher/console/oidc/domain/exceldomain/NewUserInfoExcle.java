package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class NewUserInfoExcle implements Serializable {

    @Excel(name = "姓名", orderNum = "0")
    private String userName;

    @Excel(name = "主账号（用户名）", orderNum = "1")
    private String accountNumber;

    @Excel(name = "邮箱", orderNum = "2")
    private String mail;

    @Excel(name = "手机号", orderNum = "3")
    private String phoneNumber;

    @Excel(name = "昵称",orderNum="4")
    private String nickname;

    @Excel(name = "工号",orderNum="5")
    private String jobNo;

    @Excel(name = "职位",orderNum="6")
    private String job;

    @Excel(name = "性别",orderNum="7")
    private String  gender;

    private String uuid;

    private String companyId;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "NewUserInfoExcle{" +
                "userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", jobNo='" + jobNo + '\'' +
                ", job='" + job + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
