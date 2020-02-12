package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.util.DateUtil;

import java.util.Date;


public class PasswordAuthorizationMagDomain extends BaseDomain {
    private static final int AUTHORIZED_CODE= 2;

    private Integer id;

    private Integer accountAnthorizedCode;

    private String accountNumber;

    private Integer passwordMinCount;

    private String password;

    private Date passwordBindingDate;

    private Date createTime;

    private Date modifyTime;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PasswordAuthorizationMagDomain() {
    }

    public PasswordAuthorizationMagDomain(String userId, String password) {
        accountAnthorizedCode = AUTHORIZED_CODE;
        passwordBindingDate = DateUtil.getSystemDate();
        createTime = DateUtil.getSystemDate();
        modifyTime = DateUtil.getSystemDate();
        this.userId = userId;
        this.password = password;
    }

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
        this.accountNumber = accountNumber;
    }

    public Integer getPasswordMinCount() {
        return passwordMinCount;
    }

    public void setPasswordMinCount(Integer passwordMinCount) {
        this.passwordMinCount = passwordMinCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getPasswordBindingDate() {
        return passwordBindingDate;
    }

    public void setPasswordBindingDate(Date passwordBindingDate) {
        this.passwordBindingDate = passwordBindingDate;
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
}
