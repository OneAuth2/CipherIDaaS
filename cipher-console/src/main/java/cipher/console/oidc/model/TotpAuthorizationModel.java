package cipher.console.oidc.model;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * Totp信息列表
 * @author shizhao
 * @since 2018/5/30
 * */
public class TotpAuthorizationModel extends BaseDomain{

    /**
     * 姓名
     * */
    String name;
    /**
     * 账号
     * */
    String accountNumber;
    /**
     * 邮箱
     * */
    String mail;
    /**
     * 手机号
     * */
    String phoneNumber;
    /**
     * 动态验证码绑定时间
     * */
    Date totpBindingDate;
    /**
     * 组号
     * */
    int groupId;
    /**
     * 组名
     * */
    String groupName;
    /**
     * 临时码数量
     * */
    int count;

    private String sord;
    private String sidx;

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
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

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getTotpBindingDate() {
        return totpBindingDate;
    }

    public void setTotpBindingDate(Date totpBindingDate) {
        this.totpBindingDate = totpBindingDate;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
