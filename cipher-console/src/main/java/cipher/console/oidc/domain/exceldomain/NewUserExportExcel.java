package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class NewUserExportExcel implements Serializable{

    @Excel(name = "姓名", orderNum = "0")
    private String userName;
    @Excel(name = "主账号（用户名）", orderNum = "1")
    private String accountNumber;
    @Excel(name = "邮箱", orderNum = "2")
    private String mail;
    @Excel(name = "手机号", orderNum = "3")
    private String phoneNumber;
    @Excel(name = "昵称", orderNum = "4")
    private String nickname;
    @Excel(name = "工号", orderNum = "5")
    private String jobNo;
    @Excel(name = "职位", orderNum = "6")
    private String job;
    @Excel(name = "性别", orderNum = "7")
    private String gender;
    @Excel(name = "部门1", orderNum = "8")
    private String dept1;
    @Excel(name = "部门2", orderNum = "9")
    private String dept2;
    @Excel(name = "部门3", orderNum = "10")
    private String dept3;
    @Excel(name = "部门4", orderNum = "11")
    private String dept4;
    @Excel(name = "部门5", orderNum = "12")
    private String dept5;
    @Excel(name = "部门6", orderNum = "13")
    private String dept6;
    @Excel(name = "部门7", orderNum = "14")
    private String dept7;
    @Excel(name = "部门8", orderNum = "15")
    private String dept8;

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

    public String getDept1() {
        return dept1;
    }

    public void setDept1(String dept1) {
        this.dept1 = dept1;
    }

    public String getDept2() {
        return dept2;
    }

    public void setDept2(String dept2) {
        this.dept2 = dept2;
    }

    public String getDept3() {
        return dept3;
    }

    public void setDept3(String dept3) {
        this.dept3 = dept3;
    }

    public String getDept4() {
        return dept4;
    }

    public void setDept4(String dept4) {
        this.dept4 = dept4;
    }

    public String getDept5() {
        return dept5;
    }

    public void setDept5(String dept5) {
        this.dept5 = dept5;
    }

    public String getDept6() {
        return dept6;
    }

    public void setDept6(String dept6) {
        this.dept6 = dept6;
    }

    public String getDept7() {
        return dept7;
    }

    public void setDept7(String dept7) {
        this.dept7 = dept7;
    }

    public String getDept8() {
        return dept8;
    }

    public void setDept8(String dept8) {
        this.dept8 = dept8;
    }
}
