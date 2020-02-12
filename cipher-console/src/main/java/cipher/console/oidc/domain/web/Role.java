package cipher.console.oidc.domain.web;


import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

public class Role extends BaseDomain {


    private Integer roleId;

    private String roleName;

    private String roles;

    private String description;

    private String createTime;

    private String modifyTime;

    private List<RoleMenu> menus;// 角色的权限

    public List<RoleMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<RoleMenu> menus) {
        this.menus = menus;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}
