package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

public class ApplicationAuditInfo implements Serializable {
    private Integer id;

    private Integer applicationId;

    private String ip;

    private Integer type;

    private String msg;

    private String remark;

    private Date createTime;

    private String operation;

    private String subCountId;

    private String companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSubCountId() {
        return subCountId;
    }

    public void setSubCountId(String subCountId) {
        this.subCountId = subCountId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public ApplicationAuditInfo(String subCountId, Integer applicationId, String ip, Integer type, String msg, String operation, Date createTime,String companyId) {
        this.subCountId=subCountId;
        this.applicationId = applicationId;
        this.ip = ip;
        this.type = type;
        this.msg = msg;
        this.createTime = createTime;
        this.operation = operation;
        this.companyId=companyId;
    }

    public ApplicationAuditInfo() {
    }
}