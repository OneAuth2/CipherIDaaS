package cipher.console.oidc.domain.web;

public class AutoSyncDingDomain {
    private String id;
    private String companyUUid;
    private String syncConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyUUid() {
        return companyUUid;
    }

    public void setCompanyUUid(String companyUUid) {
        this.companyUUid = companyUUid;
    }

    public String getSyncConfig() {
        return syncConfig;
    }

    public void setSyncConfig(String syncConfig) {
        this.syncConfig = syncConfig;
    }

    @Override
    public String toString() {
        return "AutoSyncDingDomain{" +
                "id='" + id + '\'' +
                ", companyUUid='" + companyUUid + '\'' +
                ", syncConfig='" + syncConfig + '\'' +
                '}';
    }
}
