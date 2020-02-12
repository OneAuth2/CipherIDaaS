package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * 网易邮箱创建子账号接口请求实体
 *
 * @Author: zt
 * @Date: 2018/12/3 10:40
 */
public class WangyiCreateSubReqDomain implements Serializable {


    /**
     * 帐号名	必填 录入项
     */
    private String account_name;

    /**
     * 是否有查看企业通讯录权限	选填 录入项	0-没有，1-有
     */
    private String addr_right;

    /**
     * 是否在通讯录中显示	选填 录入项	0-不显示，1-显示
     */
    private String addr_visible;


    /**
     * 域名	必填 匹配项
     */
    private String domain;


    /**
     * 过期时间	选填 录入项	格式yyyy-MM-dd，不传表示无过期时间
     */
    private String exp_time;


    /**
     * 是否有自动转发权限	选填 录入项	0-否，1-是
     */
    private String fwd;



    /**
     * 自动转发是否需要手机验证	 选填 录入项	0-否，1-是
     */
    private String fwdmauth;

    /**
     * 工号	选填 录入项
     */
    private String job_no;

    /**
     * 手机号码	选填 录入项	用户资料手机号码信息
     */
    private String mobile;



    /**
     * 姓名	必填 录入项
     */
    private String nickname;





    /**
     * 密码类型	选填 录入项	密码是否md5编码过,0-明文，1-md5编码
     */
    private String pass_type;

    /**
     * 帐号首次登录是否需要修改密码	 选填 录入项	0-不需要，1-web登录需要，客户端不需要，2-web登录需要且未改密码前客户端不能登录
     */
    private String passchange_req="0";


    /**
     * 密码	必填 录入项
     */
    private String password;


    /**
     * 企业标识	必填 匹配项
     */
    private String product;

    /**
     * 邮箱总容量	选填 录入项	如果企业未开通容量自由分配功能，此参数无效
     */
    private String quota;

    /**
     * 是否允许将军令重置密码	选填 录入项	0-否，1-是
     */
    private String resetpass_general;

    /**
     * 是否允许通过手机重置密码	选填 录入项	0-否，1-是
     */
    private String resetpass_mobile;

    /**
     * 所属部门id	选填 录入项	默认部门传default
     */
    private String unit_id;

    /**
     * 性别	选填 录入项	0-男，1-女，-1-保密
     */
    private String gender;

    /**
     * 办公电话	选填 录入项
     */
    private String tel;

    /**
     * 传真	选填 录入项
     */
    private String fax;

    /**
     * 备注	选填 录入项
     */
    private String remark;



    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAddr_right() {
        return addr_right;
    }

    public void setAddr_right(String addr_right) {
        this.addr_right = addr_right;
    }

    public String getAddr_visible() {
        return addr_visible;
    }

    public void setAddr_visible(String addr_visible) {
        this.addr_visible = addr_visible;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getExp_time() {
        return exp_time;
    }

    public void setExp_time(String exp_time) {
        this.exp_time = exp_time;
    }

    public String getFwd() {
        return fwd;
    }

    public void setFwd(String fwd) {
        this.fwd = fwd;
    }

    public String getFwdmauth() {
        return fwdmauth;
    }


    public void setFwdmauth(String fwdmauth) {
        this.fwdmauth = fwdmauth;
    }

    public String getJob_no() {
        return job_no;
    }

    public void setJob_no(String job_no) {
        this.job_no = job_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPass_type() {
        return pass_type;
    }

    public void setPass_type(String pass_type) {
        this.pass_type = pass_type;
    }

    public String getPasschange_req() {
        if(passchange_req==null){
            passchange_req="0";
        }
        return passchange_req;
    }

    public void setPasschange_req(String passchange_req) {
        this.passchange_req = passchange_req;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuota() {
        return quota;
    }

    public void setQuota(String quota) {
        this.quota = quota;
    }

    public String getResetpass_general() {
        return resetpass_general;
    }

    public void setResetpass_general(String resetpass_general) {
        this.resetpass_general = resetpass_general;
    }

    public String getResetpass_mobile() {
        return resetpass_mobile;
    }

    public void setResetpass_mobile(String resetpass_mobile) {
        this.resetpass_mobile = resetpass_mobile;
    }

    public String getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(String unit_id) {
        this.unit_id = unit_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
