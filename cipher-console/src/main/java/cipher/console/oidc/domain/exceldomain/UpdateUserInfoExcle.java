package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class UpdateUserInfoExcle {

    @Excel(name = "主账号（用户名）", orderNum = "0")
    private String accountNumber;
    @Excel(name = "姓名", orderNum = "1")
    private String userName;
    @Excel(name = "邮箱", orderNum = "2")
    private String mail;
    @Excel(name = "手机号", orderNum = "3")
    private String phoneNumber;
    @Excel(name = "身份证号", orderNum = "4")
    private String idNum;
    @Excel(name = "性别", orderNum = "5")
    private String gender;
    @Excel(name = "职位", orderNum = "6")
    private String job;
    @Excel(name = "工号", orderNum = "7")
    private String jobNo;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
}
