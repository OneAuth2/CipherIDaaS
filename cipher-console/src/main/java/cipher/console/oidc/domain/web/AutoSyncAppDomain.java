package cipher.console.oidc.domain.web;

public class AutoSyncAppDomain {
    private Integer id;
    private String companyId;
    private String applicationId;
    private String syncConfig;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getSyncConfig() {
        return syncConfig;
    }

    public void setSyncConfig(String syncConfig) {
        this.syncConfig = syncConfig;
    }

    @Override
    public String toString() {
        return "AutoSyncAppDomain{" +
                "id=" + id +
                ", companyId='" + companyId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", syncConfig='" + syncConfig + '\'' +
                '}';
    }
}
