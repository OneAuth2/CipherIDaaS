package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.BaseRoleDomain;

import java.io.Serializable;

public class RoleGroupMapDomain extends BaseRoleDomain implements Serializable{


    private Integer id;

    private Integer roleId;

    private Integer groupId;

    private String groupName;

    private String text;

    private String type;

    public RoleGroupMapDomain() {
        setType();
    }

    private String roleName;


    private String roleIds;

    private String appliationIds;


    public String getAppliationIds() {
        return appliationIds;
    }

    public void setAppliationIds(String appliationIds) {
        this.appliationIds = appliationIds;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
        setText(groupName);
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public void setType(){
        this.type = "部门";
    }

    @Override
    public String toString() {
        return super.toString()+"RoleGroupMapDomain{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", text='" + text + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
