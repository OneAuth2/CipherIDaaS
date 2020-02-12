package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by 95744 on 2018/9/25.
 */
public class NewUserInfo extends BaseDomain {

    private String userName;//用户名
    private String accountNumber;//账号
    private String roleName;
    private String status;
    private String queryName;
    private String sidx;
    private String sord;
    private String groupId;
    private String groupName;//部门
    private String mail;//邮箱
    private String job;//职位
    private String phoneNumber;//电话号码
    private String gender;//性别
    private int type;
    private int queryType;
    private String jobNo;//工号
    private String nickName;//昵称

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public int getQueryType() {
        return queryType;
    }

    public void setQueryType(int queryType) {
        this.queryType = queryType;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String source;

    private String teamName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private List<GroupInfoDomain> groupList;
    private List<RoleInfoDomain> roleList;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GroupInfoDomain> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<GroupInfoDomain> groupList) {
        this.groupList = groupList;
    }

    public List<RoleInfoDomain> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleInfoDomain> roleList) {
        this.roleList = roleList;
    }

    @Override
    public boolean equals(Object obj) {
        NewUserInfo u = (NewUserInfo) obj;
        if(StringUtils.isEmpty(accountNumber)){
          return true;
        }
        return accountNumber.equals(u.getAccountNumber());
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }

    @Override
    public String toString() {
        return "NewUserInfo{" +
                "userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", roleName='" + roleName + '\'' +
                ", status='" + status + '\'' +
                ", queryName='" + queryName + '\'' +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                ", groupId='" + groupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", mail='" + mail + '\'' +
                ", job='" + job + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", type=" + type +
                ", queryType=" + queryType +
                ", source='" + source + '\'' +
                ", teamName='" + teamName + '\'' +
                ", groupList=" + groupList +
                ", roleList=" + roleList +
                '}';
    }
}
