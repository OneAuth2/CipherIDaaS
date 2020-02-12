package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 *
 * @author yw
 */

/**
 * modify by 田扛
 * modify time 2019年3月19日09:25:50
 * 添加ad认证和动态密码字段
 *
 */

/**
 * modify by cozi
 * modify time 2019-5-7 19:22
 * 删除ad认证和动态密码字段
 * 增加认证源 二次认证 公司uuid
 */

public class IdentityStrategyDomain extends BaseDomain {
    private Integer secondAuth;//二次认证是否开启 1.5.4
    private Object secondAuthType;//二次认证方式 1.5.4
    private Integer switches;//二次认证模式1.6（并行、串行）

    private Integer id;

    private String companyUuid;//公司uuid 1.5.4

    private String strategyName;

    private Integer priority;

    private Integer authType;//认证源 1.5.4

    private Integer scaveAuthState;

    private Date createTime;

    private Date modifyTime;

    private String sidx;

    private String sord;

    private String groupIds;

    private String groupNames;

    private String staffAuthType;

    public String getStaffAuthType() {
        return staffAuthType;
    }

    public void setStaffAuthType(String staffAuthType) {
        this.staffAuthType = staffAuthType;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
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

    private List<GroupInfoDomain> groupInfoDomainList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }


    public List<GroupInfoDomain> getGroupInfoDomainList() {
        return groupInfoDomainList;
    }

    public void setGroupInfoDomainList(List<GroupInfoDomain> groupInfoDomainList) {
        this.groupInfoDomainList = groupInfoDomainList;
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

    public Integer getScaveAuthState() {
        return scaveAuthState;
    }

    public void setScaveAuthState(Integer scaveAuthState) {
        this.scaveAuthState = scaveAuthState;
    }

    public Integer getSecondAuth() {
        return secondAuth;
    }

    public void setSecondAuth(Integer secondAuth) {
        this.secondAuth = secondAuth;
    }

    public Object getSecondAuthType() {
        return secondAuthType;
    }

    public void setSecondAuthType(Object secondAuthType) {
        this.secondAuthType = secondAuthType;
    }

    public Integer getSwitches() {
        return switches;
    }

    public void setSwitches(Integer switches) {
        this.switches = switches;
    }
}
