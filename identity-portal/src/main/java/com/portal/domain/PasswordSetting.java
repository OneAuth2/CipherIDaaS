package com.portal.domain;


import java.io.Serializable;

/**
 * 密码策略的实体类
 * create by shizhao at 2019/5/20
 *
 * @author shizhao
 * @since  2019/5/20
 * */
public class PasswordSetting implements Serializable{

    private int id;

    private int length;

    private String companyId;

    private String init;

    private String passwordType;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getInit() {
        return init;
    }

    public void setInit(String init) {
        this.init = init;
    }

    public String getPasswordType() {
        return passwordType;
    }

    public void setPasswordType(String passwordType) {
        this.passwordType = passwordType;
    }

    @Override
    public String toString() {
        return "PasswordSetting{" +
                "id=" + id +
                ", length=" + length +
                ", companyId='" + companyId + '\'' +
                ", init='" + init + '\'' +
                ", passwordType='" + passwordType + '\'' +
                '}';
    }
}
