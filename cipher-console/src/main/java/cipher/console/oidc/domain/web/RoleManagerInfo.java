package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleManagerInfo extends BaseDomain implements Serializable  {

    private static final long serialVersionUID = -1799662878703763493L;

    private Integer           roleId;

    private String            roleName;

    private String            roles;

    private String            description;

    private Date createTime;

    private Date              modifyTime;

    private List<RoleMenu> menus;                                    // 角色的权限

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
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
