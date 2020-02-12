package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-12
 */

import cipher.console.oidc.domain.BaseDomain;

import java.io.Serializable;

/**
 * @Author qiaoxi
 * @Date 2019-10-1210:54
 **/
public class WxUserDomain extends BaseDomain implements Serializable {

    public static final Integer ALL = 0;
    public static final Integer NEW_ADD = 1;
    public static final Integer WAIT_BIND = 2;
    private String userid;
    private String status;
    private String name;
    private String department;
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private String address;
    private String departmentStr;



    public static Integer getALL() {
        return ALL;
    }

    public static Integer getNewAdd() {
        return NEW_ADD;
    }

    public static Integer getWaitBind() {
        return WAIT_BIND;
    }

    public String getPosition() {
        return position;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDepartmentStr() {
        return departmentStr;
    }

    public void setDepartmentStr(String departmentStr) {
        this.departmentStr = departmentStr;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
