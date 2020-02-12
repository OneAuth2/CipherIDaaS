package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: zt
 * 从账号绑定页面的列表
 * @Date: 2018/6/5 15:54
 */
public class SubAccountAuthModel extends BaseDomain {

    /**
     * 组的主键id
     */
    private Integer groupId;

    /**
     * 子账号的主键id
     */
    private Integer subId;

    /**
     * 该子账号所在的应用的应用名
     */
    private String applicationName;

    /**
     * 应用的主键id
     */
    private Integer applicationId;

    /**
     * 组名
     */
    private String groupName;


    /**
     * 主账号
     */
    private String accountNumber;

    /**
     * 用户名
     */
    private String userName;

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
}
