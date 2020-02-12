package cipher.console.oidc.domain.authsettingsdomain;

import java.util.Date;

public class AuthStrategyDomain {
    private int id;
    private String uuid;
    private String companyUuid;
    private String strategyName;
    private int priority;
    private int authOrigin;
    private int secondAuth;
    private String auth_type;
    private String effectiveRange;
    private Date create_time;
    private Date modify_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAuthOrigin() {
        return authOrigin;
    }

    public void setAuthOrigin(int authOrigin) {
        this.authOrigin = authOrigin;
    }

    public int getSecondAuth() {
        return secondAuth;
    }

    public void setSecondAuth(int secondAuth) {
        this.secondAuth = secondAuth;
    }

    public String getAuth_type() {
        return auth_type;
    }

    public void setAuth_type(String auth_type) {
        this.auth_type = auth_type;
    }

    public String getEffectiveRange() {
        return effectiveRange;
    }

    public void setEffectiveRange(String effectiveRange) {
        this.effectiveRange = effectiveRange;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    @Override
    public String toString() {
        return "AuthStrategyDomain{" +
                "strategyName='" + strategyName + '\'' +
                ", priority=" + priority +
                ", authOrigin=" + authOrigin +
                ", secondAuth=" + secondAuth +
                ", auth_type='" + auth_type + '\'' +
                ", effectiveRange='" + effectiveRange + '\'' +
                '}';
    }
}
