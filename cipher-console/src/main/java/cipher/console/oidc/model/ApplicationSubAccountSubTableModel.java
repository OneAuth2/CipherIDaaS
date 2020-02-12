package cipher.console.oidc.model;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class ApplicationSubAccountSubTableModel extends BaseDomain {
    private int id;

    /**
     *
     *应用唯一Id
     */
    private String applicationId;
    /**
     *
     *应用名称
     */
    private String applicationName;

    /**
     * 子账号
     *
     */
    private String subAccount;

    /**
     * 最后访问时间
     *
     */
    private Date modifyTime;

    /**
     * 授权时间
     *
     */
    private Date applicationAuthorizedDate;


    public String getAppName() {
        return applicationName;
    }

    public void setAppName(String appName) {
        this.applicationName = appName;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getVisitDate() {
        return modifyTime;
    }

    public void setVisitDate(Date visitDate) {
        this.modifyTime = visitDate;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getAuthorizedDate() {
        return applicationAuthorizedDate;
    }

    public void setAuthorizedDate(Date authorizedDate) {
        this.applicationAuthorizedDate = authorizedDate;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getApplicationAuthorizedDate() {
        return applicationAuthorizedDate;
    }

    public void setApplicationAuthorizedDate(Date applicationAuthorizedDate) {
        this.applicationAuthorizedDate = applicationAuthorizedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
