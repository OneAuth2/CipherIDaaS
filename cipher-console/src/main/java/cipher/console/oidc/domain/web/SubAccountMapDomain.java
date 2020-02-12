package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

/**
 * 主账号和子账号的关联表
 */
public class SubAccountMapDomain extends BaseDomain {

    private Integer id;

    /**
     * 主账号
     */
    private String accountNumber;

    /**
     * 从账号id
     */
    private Integer subId;

    private Date createTime;

    private Date modifyTime;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
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



    @Override
    public String toString() {
        return "SubAccountMapDomain{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", subId=" + subId +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}