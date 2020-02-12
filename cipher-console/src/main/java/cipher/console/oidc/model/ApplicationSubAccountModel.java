package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: zt
 * 能看到某个应用的所有账号的详情
 * @Date: 2018/6/1 9:03
 */
public class ApplicationSubAccountModel extends BaseDomain {

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组的描述
     */
    private String groupDescription;

    /**
     * 主账号
     */
    private String accountNumber;


    /**
     * 子账号
     */
    private String subAccount;



    /**
     * 完整姓名
     */
    private String userName;

    private String nickName;

    /**
     * 账号状态
     */
    private String accountStatus;


    private Integer applicationId;

    /**
     * 账号认证方式
     */
    private String methodType;

    /**
     * 该主账号被授权应用
     */
    private String authApp;

    /**
     * 条件查询字段
     */
    private String queryName;

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

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getAuthApp() {
        return authApp;
    }

    public void setAuthApp(String authApp) {
        this.authApp = authApp;
    }
}
