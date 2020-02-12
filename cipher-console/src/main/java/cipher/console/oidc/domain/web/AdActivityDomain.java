package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.enums.AccountStatusEnum;

import java.util.Date;

/**
 * Ad同步到自己用户体系的缓冲表
 *
 * @Author: zt
 * @Date: 2018/10/24 19:44
 */
public class AdActivityDomain extends BaseDomain {

    private Integer id;
    private String username;
    private String accountNumber;
    private String  checkNumber;
    private String nickname;

    private String mail;

    private String phoneNumber;

    /**
     * 1-启用，2-停用，3-锁定
     */
    private Integer accountStatus;


    private String job;

    private String manager;

    /**
     * 0-字段更改，1-新增
     */
    private Integer isAdd;

    private String objectGUID;

    private Date createTime;

    private Date modifyTime;
    private String sidx;
    private String sord;

    /**
     * 标识AD来源
     */
    private String source;


    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
        this.accountNumber = accountNumber;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }


    public void setAccountStatus(String accountStatus) {
        if("启用".equals(accountStatus)){
            this.accountStatus= AccountStatusEnum.ENABLE.getCode();
        }
        if("停用".equals(accountStatus)){
            this.accountStatus= AccountStatusEnum.DISABLE.getCode();
        }
        if("锁定".equals(accountStatus)){
            this.accountStatus= AccountStatusEnum.LOCK.getCode();
        }
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Integer isAdd) {
        this.isAdd = isAdd;
    }

    public String getObjectGUID() {
        return objectGUID;
    }

    public void setObjectGUID(String objectGUID) {
        this.objectGUID = objectGUID;
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

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        if (checkNumber==""){
            this.checkNumber="";
        }
        this.checkNumber = checkNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "AdActivityDomain{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", checkNumber='" + checkNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountStatus=" + accountStatus +
                ", job='" + job + '\'' +
                ", manager='" + manager + '\'' +
                ", isAdd=" + isAdd +
                ", objectGUID='" + objectGUID + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                '}';
    }
}
