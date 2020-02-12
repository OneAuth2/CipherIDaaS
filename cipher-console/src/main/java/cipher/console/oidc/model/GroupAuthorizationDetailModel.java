package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @author shizhao
 * @since 2018/5/30
 * 组授权详细信息实体类
 * */
public class GroupAuthorizationDetailModel extends BaseDomain{

    /*组号*/
    int groupId;

    /*应用ID*/
    int applicationId;

    /*账号*/
    String accountNumber;


    /*姓名*/
    String usenName;

    /*昵称*/
    String nickname;

    /*应用名称*/
    String applicationName;

    /*应用的唯一ID*/
    String applicationClientId;

    /*认证方式*/
    String methods;

    /*子账户名*/
    String subAccount;

    /*账户状态*/
    String accountStatus;

    private String sidx;
    private String sord;


    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUsenName() {
        return usenName;
    }

    public void setUsenName(String usenName) {
        this.usenName = usenName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationClientId() {
        return applicationClientId;
    }

    public void setApplicationClientId(String applicationClientId) {
        this.applicationClientId = applicationClientId;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }
}
