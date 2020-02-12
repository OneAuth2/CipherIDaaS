package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.enums.AccountStatusEnum;
import org.apache.commons.lang.StringUtils;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.util.Date;

/**
 * Ad同步到自己用户体系的缓冲表
 *
 * @Author: zt
 * @Date: 2018/10/24 19:44
 */
public class AdUserBufferDomain extends BaseDomain {

    private Integer id;

    private String accountNumber;

    private String userName;

    /**
     * 用户除去根域名所在的节点信息
     */
//    private Name dn;

    private String dn;

    private String nickname;

    private String mail;

    private String phoneNumber;

    /**
     * 1-启用，2-停用，3-锁定
     */
    private Integer accountStatus;


    /**
     * 职位
     */
    private String job;

    /**
     * 工号
     */
    private String jobNo;

    private String manager;

    /**
     * 0-字段更改，1-新增
     */
    private Integer isAdd;

    private String objectGUID;

    private String source;

    private Date createTime;

    private Date modifyTime;

    /**
     * 不为空，代表是字段更改，为空代表是新增用户
     */
    private String checkNumber;


    /**
     * Ad域中用户的状态
     */
    private int userAccountControl;


    /**
     * Ad中修改时间
     */
    private String whenChanged;

    /*
     * 公司id
     * */
    private String companyId;


    @Override
    public boolean equals(Object obj) {
        AdUserBufferDomain adUserBufferDomain = (AdUserBufferDomain) obj;
        if (objectGUID == null) {
            if (adUserBufferDomain.objectGUID != null)
                return false;
        } else if (!objectGUID.equals(adUserBufferDomain.objectGUID)){
             return false;
       }
        return true;
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








    /**********dn 为Name类型时************/
//    public String getDn() {
//        return dn.toString();
//    }

//    public void setDn(Name dn) {
//        this.dn = dn;
//    }
//
//    public void setDn(String dn) {
//        try {
//            this.dn = new LdapName(dn);
//        } catch (InvalidNameException e) {
//            e.printStackTrace();
//        }
//    }

    /**********dn 为Name类型时************/

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        //用户账号默认为启用
        if (accountStatus == null) {
            return AccountStatusEnum.ENABLE.getCode();
        }

        //AD域中514代表账号禁用
        if (accountStatus == 514) {
            return AccountStatusEnum.DISABLE.getCode();
        }

        return AccountStatusEnum.ENABLE.getCode();
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setAccountStatus(String s) {
        if ("启用".equals(s)) {
            this.accountStatus = AccountStatusEnum.ENABLE.getCode();
        }

        if ("停用".equals(s)) {
            this.accountStatus = AccountStatusEnum.DISABLE.getCode();
        }

        if ("锁定".equals(s)) {
            this.accountStatus = AccountStatusEnum.LOCK.getCode();
        }
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Integer getIsAdd() {
        //默认是新增的用户
        if (isAdd == null) {
            return 1;
        }
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public int getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(int userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getWhenChanged() {
        return whenChanged;
    }

    public void setWhenChanged(String whenChanged) {
        this.whenChanged = whenChanged;
    }

    @Override
    public String toString() {
        return "AdUserBufferDomain{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", dn='" + dn + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mail='" + mail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", accountStatus=" + accountStatus +
                ", job='" + job + '\'' +
                ", manager='" + manager + '\'' +
                ", isAdd=" + isAdd +
                ", objectGUID='" + objectGUID + '\'' +
                ", source='" + source + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", checkNumber='" + checkNumber + '\'' +
                ", userAccountControl=" + userAccountControl +
                ", whenChanged='" + whenChanged + '\'' +
                '}';
    }
}
