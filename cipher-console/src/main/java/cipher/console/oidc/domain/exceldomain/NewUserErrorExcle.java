package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class NewUserErrorExcle implements Serializable {

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


    @Excel(name = "失败原因", orderNum = "8")
    private String errorMsg;

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

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public NewUserErrorExcle() {
    }

    public NewUserErrorExcle(NewUserInfoExcle newUserInfoExcle ) {
        this.userName = newUserInfoExcle.getUserName();
        this.accountNumber = newUserInfoExcle.getAccountNumber();
        this.mail = newUserInfoExcle.getMail();
        this.phoneNumber = newUserInfoExcle.getPhoneNumber();
        this.nickname = newUserInfoExcle.getNickname();
        this.jobNo = newUserInfoExcle.getJobNo();
        this.job = newUserInfoExcle.getJob();
        this.gender = newUserInfoExcle.getGender();
    }
}
