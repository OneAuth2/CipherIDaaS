package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: TK
 * @Date: 2018/11/15 17:13
 */
public class AppSonAccountDomain extends BaseDomain {
    private Integer id;
    private String applicationId;//应用id
    private String accountNumber;//对应主账号
    private String userName;//主账号的姓名
     private String sonAccountNumber;//对应从账号
    private String subId;
    private String sord;//排序
    private String sidx;//排序字段
    private String appName;
    private String phoneNumber;
    private String mail;
    private String password;
    private String gender;
    private String jobNo;
    private String job;
    private String groupName;
    private String nickName;
    private String checkNumber;
    private Integer genderStr;

    private String subRuleStr;

    private String userId;

    private PathInfo pathInfo;
    private String uuid;

    private String isSyschronTime;

    private  int isSynchron;

    public int getIsSynchron() {
        return isSynchron;
    }

    public void setIsSynchron(int isSynchron) {
        this.isSynchron = isSynchron;
    }

    public String getIsSyschronTime() {
        return isSyschronTime;
    }

    public void setIsSyschronTime(String isSyschronTime) {
        this.isSyschronTime = isSyschronTime;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PathInfo getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(PathInfo pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubRuleStr() {
        return subRuleStr;
    }

    public void setSubRuleStr(String subRuleStr) {
        this.subRuleStr = subRuleStr;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getGenderStr() {
        return genderStr;
    }

    public void setGenderStr(Integer genderStr) {
        this.genderStr = genderStr;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    @Override
    public String toString() {
        return "AppSonAccountDomain{" +
                "applicationId='" + applicationId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", sonAccountNumber='" + sonAccountNumber + '\'' +
                ", sord='" + sord + '\'' +
                ", sidx='" + sidx + '\'' +
                '}';
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSonAccountNumber() {
        return sonAccountNumber;
    }

    public void setSonAccountNumber(String sonAccountNumber) {
        this.sonAccountNumber = sonAccountNumber;
    }
}
