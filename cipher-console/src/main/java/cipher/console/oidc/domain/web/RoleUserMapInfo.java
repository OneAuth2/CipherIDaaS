package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.BaseRoleDomain;

public class RoleUserMapInfo extends BaseRoleDomain {
    private Integer id;

    private Integer roleId;

    private String userId;

    private String accountNumber;

    private String userName;

    public RoleUserMapInfo() {
        setType();
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
        setText(accountNumber);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public void setType() {
        this.type = "用户";
    }

    @Override
    public String toString() {
        return "RoleUserMapInfo{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", userId='" + userId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}