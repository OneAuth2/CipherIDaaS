package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class UserCompanyMapDomain extends BaseDomain {



    private Integer id;
    private String queryName;
    private String invitCode;
    private Integer platFormUserId;
    private String companyId;
    private String createTime;
    private String modifyTime;
    private String userName;
    private String phoneNumber;
    private String mail;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvitCode() {
        return invitCode;
    }

    public void setInvitCode(String invitCode) {
        this.invitCode = invitCode;
    }

    public Integer getPlatFormUserId() {
        return platFormUserId;
    }

    public void setPlatFormUserId(Integer platFormUserId) {
        this.platFormUserId = platFormUserId;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
