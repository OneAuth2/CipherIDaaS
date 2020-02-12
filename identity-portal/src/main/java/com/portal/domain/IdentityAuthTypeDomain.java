package com.portal.domain;

import java.io.Serializable;
import java.util.Date;

public class IdentityAuthTypeDomain implements Serializable {

    private Integer id;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 策略名称
     */
    private String strategyName;

    /**
     * 认证方式(1-用户名+密码+动态码，2-用户名+动态密码，3-用户名+密码)，多个，拼接
     */
    private Integer authType;

    /**
     * 二次认证开启
     */
    private int secondAuth;

    /**
     * 二次认证的方式
     */
    private String secondAuthType;

    /**
     * 扫码认证状态(1-开启，2-关闭)
     */
    private Integer scaveAuthState;

    private Date createTime;

    private Date modifyTime;
    /**
     * 串行和并行的开关 0：并行 1：串行
     */

    private int switches;

    public int getSwitches() {
        return switches;
    }

    public void setSwitches(int switches) {
        this.switches = switches;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

    public Integer getScaveAuthState() {
        return scaveAuthState;
    }

    public void setScaveAuthState(Integer scaveAuthState) {
        this.scaveAuthState = scaveAuthState;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getSecondAuth() {
        return secondAuth;
    }

    public void setSecondAuth(int secondAuth) {
        this.secondAuth = secondAuth;
    }

    public String getSecondAuthType() {
        return secondAuthType;
    }

    public void setSecondAuthType(String secondAuthType) {
        this.secondAuthType = secondAuthType;
    }
}
