package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

public class UserPhoneExcle implements Serializable {

   /* *//**
     * 应用
     *//*
    @Excel(name = "姓名",orderNum = "0")
    private String userName;

    *//**
     * 从账号
     *//*
    @Excel(name = "电子邮件",orderNum = "1")
    private String mail;

    *//**
     * 主账号
     *//*

    @Excel(name = "电话",orderNum = "2")
    private String phoneNumber;*/

    @Excel(name = "姓名",orderNum = "0")
    private String userName;

    @Excel(name = "邮箱",orderNum = "1")
    private String mail;

    @Excel(name = "电话",orderNum = "2")
    private String phoneNumber;

/*
    @Excel(name = "工号",orderNum = "2")
    private String jobNo;*/

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
