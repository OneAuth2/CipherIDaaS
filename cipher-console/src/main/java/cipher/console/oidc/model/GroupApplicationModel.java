package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.PathInfo;
import cipher.console.oidc.domain.web.SubAccountRuleInfo;

public class GroupApplicationModel extends BaseDomain{

    private int id;
    private int groupId;
    private String groupName;
    private int parentGroupId;
    private String applicationId;
    private String applicationName;
    private String subAccount;
    private String accountNumber;
    private int subId;
    private SubAccountRuleInfo subAccountRuleInfo;

    private PathInfo pathInfo;

    private int  isSynchron;

    private String isSyschronTime;




    public int getIsSynchron() {
        return isSynchron;
    }

    public void setIsSynchron(int isSynchron) {
        this.isSynchron = isSynchron;
    }

    public String getIsSyschronTime() {
        return isSyschronTime;
    }

    public void setIsSyschronTime(String isSyschronTime) {
        this.isSyschronTime = isSyschronTime;
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public SubAccountRuleInfo getSubAccountRuleInfo() {
        return subAccountRuleInfo;
    }

    public void setSubAccountRuleInfo(SubAccountRuleInfo subAccountRuleInfo) {
        this.subAccountRuleInfo = subAccountRuleInfo;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public GroupApplicationModel() {
    }

    public GroupApplicationModel(String applicationId, String applicationName, String subAccount) {
        this.applicationId = applicationId;
        this.applicationName = applicationName;
        this.subAccount = subAccount;
    }

    public GroupApplicationModel(ApplicationInfoDomain applicationInfoDomain) {
        this(applicationInfoDomain.getApplicationId(),applicationInfoDomain.getApplicationName(),applicationInfoDomain.getSubAccount());
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
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

    @Override
    public String toString() {
        return "\nGroupApplicationModel{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", parentGroupId=" + parentGroupId +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", subAccount='" + subAccount + '\'' +
                '}';
    }
}
