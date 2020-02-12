package cipher.console.oidc.domain.web;

import java.util.Date;

public class CompanyInfoDomain {

    private Integer id;
    private Integer platUserId;
    private String companyName;
    private Integer maxsize;
    private Integer isStop;
    private String accountNumber;
    private String companyAppId;
    private String companySecretKey;
    private Integer singleCount;
    private Integer leftCount;
    private String outOfDate;
    private String createTime;
    private String modifyTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getMaxsize() {
        return maxsize;
    }

    public void setMaxsize(Integer maxsize) {
        this.maxsize = maxsize;
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCompanyAppId() {
        return companyAppId;
    }

    public void setCompanyAppId(String companyAppId) {
        this.companyAppId = companyAppId;
    }

    public String getCompanySecretKey() {
        return companySecretKey;
    }

    public void setCompanySecretKey(String companySecretKey) {
        this.companySecretKey = companySecretKey;
    }

    public Integer getSingleCount() {
        return singleCount;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(String outOfDate) {
        this.outOfDate = outOfDate;
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


    public Integer getPlatUserId() {
        return platUserId;
    }

    public void setPlatUserId(Integer platUserId) {
        this.platUserId = platUserId;
    }
}
