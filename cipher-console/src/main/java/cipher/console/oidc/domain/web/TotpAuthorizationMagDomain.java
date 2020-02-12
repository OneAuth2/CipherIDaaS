package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class TotpAuthorizationMagDomain {

    private Integer id;

    private Integer accountAuthorizedCode;

    private String accountNumber;

    private Integer totpSecretCount;

    private String totpKey;

    private Date totpBindingDate;

    private Date createTime;

    private Date modifyTime;

    private String sord;

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountAuthorizedCode() {
        return accountAuthorizedCode;
    }

    public void setAccountAuthorizedCode(Integer accountAuthorizedCode) {
        this.accountAuthorizedCode = accountAuthorizedCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public Integer getTotpSecretCount() {
        return totpSecretCount;
    }

    public void setTotpSecretCount(Integer totpSecretCount) {
        this.totpSecretCount = totpSecretCount;
    }

    public String getTotpKey() {
        return totpKey;
    }

    public void setTotpKey(String totpKey) {
        this.totpKey = totpKey == null ? null : totpKey.trim();
    }

    public Date getTotpBindingDate() {
        return totpBindingDate;
    }

    public void setTotpBindingDate(Date totpBindingDate) {
        this.totpBindingDate = totpBindingDate;
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