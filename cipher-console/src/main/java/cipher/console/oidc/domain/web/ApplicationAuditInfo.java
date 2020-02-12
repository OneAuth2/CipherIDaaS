package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class ApplicationAuditInfo extends BaseDomain {
    private Integer id;

    private Integer applicationId;

    private String subcountId;

    private String ip;

    private Integer type;

    private String typeStr;

    private String msg;

    private String remark;

    private Integer recently;

    private Date createTime;

    private String startTime;

    private String operation;

    private String sord;

    private String companyId;

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }


    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Integer getRecently() {
        return recently;
    }

    public void setRecently(Integer recently) {
        this.recently = recently;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    private String endTime;
    private String userName;
    private String  accountNumber;
    private String  subAccount;

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
        this.ip = ip == null ? null : ip.trim();
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
        this.msg = msg == null ? null : msg.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getEndTime() {
        return endTime;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSubcountId() {
        return subcountId;
    }

    public void setSubcountId(String subcountId) {
        this.subcountId = subcountId;
    }

    public ApplicationAuditInfo() {
    }

    public ApplicationAuditInfo(Integer applicationId, String subcountId, String ip, Integer type,  String msg, String remark, Date createTime, String operation,String companyId) {
        this.applicationId = applicationId;
        this.subcountId = subcountId;
        this.ip = ip;
        this.type = type;
        this.msg = msg;
        this.remark = remark;
        this.createTime = createTime;
        this.operation = operation;
        this.companyId=companyId;
    }
}