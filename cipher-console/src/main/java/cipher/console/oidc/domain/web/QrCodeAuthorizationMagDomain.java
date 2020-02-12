package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class QrCodeAuthorizationMagDomain {
    private Integer id;
    private Integer accountAnthorizedCode;

    private String accountNumber;

    private Integer qrCodeCount;

    private Date qrCodeBindingDate;

    private Date createTime;

    private Date modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountAnthorizedCode() {
        return accountAnthorizedCode;
    }

    public void setAccountAnthorizedCode(Integer accountAnthorizedCode) {
        this.accountAnthorizedCode = accountAnthorizedCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public Integer getQrCodeCount() {
        return qrCodeCount;
    }

    public void setQrCodeCount(Integer qrCodeCount) {
        this.qrCodeCount = qrCodeCount;
    }

    public Date getQrCodeBindingDate() {
        return qrCodeBindingDate;
    }

    public void setQrCodeBindingDate(Date qrCodeBindingDate) {
        this.qrCodeBindingDate = qrCodeBindingDate;
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