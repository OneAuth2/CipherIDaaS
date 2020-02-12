package cipher.console.oidc.domain.subapp;

import java.io.Serializable;
import java.util.List;

/**
 * 腾讯邮箱创建子账号的请求实体
 * https://exmail.qq.com/qy_mng_logic/doc#10014
 *
 * @Author: zt
 * @Date: 2018/11/29 11:04
 */
public class TencentCreateSubReqDomain implements Serializable {


    /**
     * userid :  zhangsan@gzdev.com
     * name : 张三
     * department : [1,2]
     * position : 产品经理
     * mobile : 15913215XXX
     * tel : 123456
     * extid : 01
     * gender : 1
     * password : ******
     * cpwd_login : 0
     * "slaves": [zhangsan@gz.com, zhangsan@bjdev.com],
     */

    /**
     * 	是	成员UserID。企业邮帐号名，邮箱格式
     */
    private String userid;

    /**
     *是	成员名称。长度为1~64个字节
     */
    private String name;
    /**
     * 	否	职位信息。长度为0~64个字节
     */
    private String position;
    /**
     * 	否	手机号码
     */
    private String mobile;
    /**
     * 	否	座机号码
     */
    private String tel;
    /**
     * 否	编号
     */
    private String extid;
    /**
     * 	否	性别。1表示男性，2表示女性
     */
    private String gender;

    /**
     * 是	密码
     */
    private String password;
    /**
     * cpwd_login	否	用户重新登录时是否重设密码, 登陆重设密码后，该标志位还原。0表示否，1表示是，缺省为0
     */
    private int cpwd_login;

    /**
     * 是	成员所属部门id列表，不超过20个
     */
    private List<Long> department;

    /**
     * 否	别名列表
     * 1.Slaves 上限为5个
     * 2.Slaves 为邮箱格式
     */
    private List<String> slaves;


    private String  accountNumber;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getExtid() {
        return extid;
    }

    public void setExtid(String extid) {
        this.extid = extid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCpwd_login() {
        return cpwd_login;
    }

    public void setCpwd_login(int cpwd_login) {
        this.cpwd_login = cpwd_login;
    }

    public List<Long> getDepartment() {
        return department;
    }

    public void setDepartment(List<Long> department) {
        this.department = department;
    }

    public List<String> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<String> slaves) {
        this.slaves = slaves;
    }


    @Override
    public String toString() {
        return "TencentCreateSubReqDomain{" +
                "userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", tel='" + tel + '\'' +
                ", extid='" + extid + '\'' +
                ", gender='" + gender + '\'' +
                ", password='" + password + '\'' +
                ", cpwd_login=" + cpwd_login +
                ", department=" + department +
                ", slaves=" + slaves +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
