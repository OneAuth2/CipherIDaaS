package cipher.console.oidc.domain.web;

public class NewAppSonAccountDomain {
    private String uuid;
    private String applicationId;
    private String accountNumber;
    private String gender;
    private String mail;
    private String mailPrefix;
    private String job;
    private String nickname;
    private String phoneNumber;
    private String jobNo;
    private String userName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMailPrefix() {
        return mailPrefix;
    }

    public void setMailPrefix(String mailPrefix) {
        this.mailPrefix = mailPrefix;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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

    @Override
    public String toString() {
        return "NewAppSonAccountDomain{" +
                "uuid='" + uuid + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", mail='" + mail + '\'' +
                ", mailPrefix='" + mailPrefix + '\'' +
                ", job='" + job + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", jobNo='" + jobNo + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
