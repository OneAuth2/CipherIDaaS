package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: TK
 * 账号下发规则实体类
 * @Date: 2018/11/19 14:21
 */
public class RuleInfoDomain extends BaseDomain {

   // private Integer accountType;
    private String userName;//用户名
    private String department;//部门
    private String phone;//手机号
    private String job;//职位
    private String jobNumver;//工号
    private String sex;//性别
    //private String passwordType;
    //private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobNumver() {
        return jobNumver;
    }

    public void setJobNumver(String jobNumver) {
        this.jobNumver = jobNumver;
    }


    @Override
    public String toString() {
        return "RuleInfoDomain{" +
                "userName='" + userName + '\'' +
                ", department='" + department + '\'' +
                ", phone='" + phone + '\'' +
                ", job='" + job + '\'' +
                ", jobNumver='" + jobNumver + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
