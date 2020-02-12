package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * 金山云创建用户的实体
 * @Author: zt
 * @Date: 2018/12/12 14:29
 */
public class WpsCreateUserReqDomain implements Serializable {

    /**
     * 登录名 必填
     */
    private String unique_name;

    /**
     * 密码 必填
     */
    private String password;

    /**
     * 可选
     * 用户来源，值为sync时，管理后台无法修改用户所在部门，password参数不生效
     */
    private String source;


    /**
     * 昵称 可选
     */
    private String nick_name;

    /**
     * 邮箱 可选
     */
    private String email;

    /**
     * 手机号 可选
     */
    private String phone_number;

    /**
     * 性别，0：未知，1：男性，2：女性 可选
     */
    private int sex;

    /**
     * 头像 可选
     */
    private String avatar;

    /**
     * 国家 可选
     */
    private String country;

    /**
     * 省份 可选
     */
    private String province;

    /**
     * 城市 可选
     */
    private String city;

    /**
     * 地址 可选
     */
    private String address;

    /**
     * 邮编 可选
     */
    private String postal;

    public String getUnique_name() {
        return unique_name;
    }

    public void setUnique_name(String unique_name) {
        this.unique_name = unique_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }
}
