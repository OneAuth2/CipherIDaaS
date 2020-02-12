package cipher.console.oidc.domain.gold_mantis;

import cipher.console.oidc.domain.BaseDomain;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2018/9/17 16:30
 */
public class GoldMantisUser extends BaseDomain implements Serializable {

    private Integer id;

    /**
     * 管理员登录名 必填
     */
    private String	adminLoginName;

    /**
     * 管理员登录密码 必填
     */
    private String adminPassword;

    /**
     * 站点名称 必填
     */
    private String tenantName;

    /**
     * 用户库名称 必填
     */
    private String 	identityStoreName;

    /**
     * 用户登录名,必填
     */
    private String loginName;

    /**
     * 组路径 可选
     */
    private String groupPath;

    /**
     * 用户姓名 可选
     */
    private String personalName;

    /**
     * 用户密码,可选
     */
    private String password;

    /**
     * 密码策略,密码复杂度 可选
     */
    private String needPasswordPolicy;

    /**
     * 用户手机号 可选
     */
    private String mobile;

    /**
     * 用户角色, 可选
     */
    private String roleName;

    /**
     * 用户角色,可选
     */
    private String roleNames;

    /**
     * 生效时间,必须和endTime同时有或者同时为空
     */
    private String startTime;

    /**
     * 失效时间
     */
    private String endTime;


    private String unit;

    private String roleMail;

    private String roleMobile;

    private String queryName;


    private String fksqCode;


    public String getFksqCode() {
        return fksqCode;
    }

    public void setFksqCode(String fksqCode) {
        this.fksqCode = fksqCode;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRoleMail() {
        return roleMail;
    }

    public void setRoleMail(String roleMail) {
        this.roleMail = roleMail;
    }

    public String getRoleMobile() {
        return roleMobile;
    }

    public void setRoleMobile(String roleMobile) {
        this.roleMobile = roleMobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminLoginName() {
        return adminLoginName;
    }

    public void setAdminLoginName(String adminLoginName) {
        this.adminLoginName = adminLoginName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getIdentityStoreName() {
        return identityStoreName;
    }

    public void setIdentityStoreName(String identityStoreName) {
        this.identityStoreName = identityStoreName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getGroupPath() {
        return groupPath;
    }

    public void setGroupPath(String groupPath) {
        this.groupPath = groupPath;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNeedPasswordPolicy() {
        return needPasswordPolicy;
    }

    public void setNeedPasswordPolicy(String needPasswordPolicy) {
        this.needPasswordPolicy = needPasswordPolicy;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
