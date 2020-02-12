package com.portal.domain;


import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2019/2/19 9:02
 */
public class ShipLoginUserForm implements Serializable {

    /**
     * 操作员或管理员，与userType对应
     */
    private String userTypeText;
    /**
     * 0-管理员，1-操作员
     */
    private String userType;
    /**
     * 加密后的用户名
     */
    private String loginName;
    /**
     * 加密后的密码
     */
    private String loginPass;
    /**
     * 服务器ip地址
     * 68.56.7.201
     */
    private String loginServerName;

    private String userTypeId;

    /**
     * 明文密码
     */
    private String pwd;

    public String getUserTypeText() {
        return userTypeText;
    }

    public void setUserTypeText(String userTypeText) {
        this.userTypeText = userTypeText;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getLoginServerName() {
        return loginServerName;
    }

    public void setLoginServerName(String loginServerName) {
        this.loginServerName = loginServerName;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public ShipLoginUserForm(String userTypeText, String userType, String loginName, String loginPass, String loginServerName, String userTypeId, String pwd) {
        this.userTypeText = userTypeText;
        this.userType = userType;
        this.loginName = loginName;
        this.loginPass = loginPass;
        this.loginServerName = loginServerName;
        this.userTypeId = userTypeId;
        this.pwd = pwd;
    }
}
