package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: zt
 * 从账号列表
 * @Date: 2018/6/4 18:50
 */
public class MainSubAppAccountModel extends BaseDomain {

    /**
     * 子账号表的主键
     */
    private Integer subAccountId;

    /**
     * 主账号
     */
    private String accountNumber;


    /**
     * 应用名
     */
    private String applicationName;

    /**
     * 子账号
     */
    private String subAccount;

    /**
     * 应用的全局唯一id
     */
    private String appClientId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 搜索方式
     * 0查询全部账号
     * 1.查询未分配账号
     * 2.查询已分配账号
     */
    private int queryType;

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

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }
}
