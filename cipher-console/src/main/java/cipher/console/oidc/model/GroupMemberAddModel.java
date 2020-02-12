package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * 添加组成员列表项
 * @author shizhao
 * @since 2018/5/30
 * */
public class GroupMemberAddModel extends BaseDomain {

    /**
     * 用户姓名
     * */
    private String name;
    /**
     * 用户昵称
     * */
    private String nickname;
    /**
     * 用户主账号
     * */
    private String accountNumber;
    /**
     * 用户电话号码
     * */
    private String phoneNumber;
    /**
     * 用户状态
     * */
    private String accountStatus;
    /**
     * 用户授权方式
     * */
    private String methods;
    /**
     * 用户组号
     * */
    private int groupId;
    /**
     * 用户组名
     * */
    private String groupName;

    private String sord;
    private String sidx;

    public GroupMemberAddModel() {
    }

    public GroupMemberAddModel(int groupId) {
        this.groupId = groupId;
    }

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "GroupMemberAddModel{" +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountStatus='" + accountStatus + '\'' +
                ", methods='" + methods + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                '}'+"\n";
    }
}
