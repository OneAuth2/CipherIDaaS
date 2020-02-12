package cipher.console.oidc.domain.web;

import java.util.List;

public class DynamicStateDomain {
    private Integer id;
    private String uuid;
    private String userId;
    private String userName;
    private String accountNumber;
    private Integer groupId;
    private String groupNames;
    private List<GroupInfoDomain> groupList;
    private Integer issueStatus;
    private String issueStatusDescribe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public List<GroupInfoDomain> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupInfoDomain> groupList) {
        this.groupList = groupList;
    }

    public Integer getIssueStatus() {
        return issueStatus;
    }

    public void setIssueStatus(Integer issueStatus) {
        this.issueStatus = issueStatus;
    }

    public String getIssueStatusDescribe() {
        return issueStatusDescribe;
    }

    public void setIssueStatusDescribe(String issueStatusDescribe) {
        this.issueStatusDescribe = issueStatusDescribe;
    }

    @Override
    public String toString() {
        return "DynamicStateDomain{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", groupId=" + groupId +
                ", groupNames='" + groupNames + '\'' +
                ", issueStatus=" + issueStatus +
                ", issueStatusDescribe='" + issueStatusDescribe + '\'' +
                '}';
    }
}
