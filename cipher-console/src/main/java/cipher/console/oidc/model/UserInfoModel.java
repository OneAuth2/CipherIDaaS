package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

import java.util.List;

/**
 * modify by cozi
 * 2019-05-09 10:03
 * 添加认证解绑
 */
public class UserInfoModel extends BaseDomain{

    private String accountNumber;
    private String nickname;
    private String phoneNumber;
    private String mail;
    private String gender;
    private String start;
    private String end;
    private int groupId;
    private String job;
    private String jobNo;
    private Integer isAdmin;
    private String groupNames;
    private String birthday;
    private List<ApplicationModel> applist;

    private String startTime;
    private String source;
    private String userName;

    private String department;
    private String securityGroup;
    private String password;
    private String uuid;
    private String companyId;
    private String idNum;

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    private Integer unbindSf;
    private Integer unbindDd;
    private Integer unbindDb;
    private Integer unbindWx;

    public Integer getUnbindWx() {
        return unbindWx;
    }

    public void setUnbindWx(Integer unbindWx) {
        this.unbindWx = unbindWx;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSecurityGroup() {
        return securityGroup;
    }

    public void setSecurityGroup(String securityGroup) {
        this.securityGroup = securityGroup;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 失效时间
     */
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public UserInfoModel() {
    }

    public UserInfoModel(String accountNumber, int groupId) {
        this.accountNumber = accountNumber;
        this.groupId = groupId;
    }

    public List<ApplicationModel> getApplist() {
        return applist;
    }

    public void setApplist(List<ApplicationModel> applist) {
        this.applist = applist;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getUnbindSf() {
        return unbindSf;
    }

    public void setUnbindSf(Integer unbindSf) {
        this.unbindSf = unbindSf;
    }

    public Integer getUnbindDd() {
        return unbindDd;
    }

    public void setUnbindDd(Integer unbindDd) {
        this.unbindDd = unbindDd;
    }

    public Integer getUnbindDb() {
        return unbindDb;
    }

    public void setUnbindDb(Integer unbindDb) {
        this.unbindDb = unbindDb;
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "accountNumber='" + accountNumber + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", gender='" + gender + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", groupId=" + groupId +
                ", job='" + job + '\'' +
                ", jobNo='" + jobNo + '\'' +
                ", isAdmin=" + isAdmin +
                ", groupNames='" + groupNames + '\'' +
                ", birthday='" + birthday + '\'' +
                ", applist=" + applist +
                ", startTime='" + startTime + '\'' +
                ", source='" + source + '\'' +
                ", userName='" + userName + '\'' +
                ", department='" + department + '\'' +
                ", securityGroup='" + securityGroup + '\'' +
                ", password='" + password + '\'' +
                ", unbindSf=" + unbindSf +
                ", unbindDd=" + unbindDd +
                ", unbindDb=" + unbindDb +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
