package cipher.console.oidc.domain.web;

public class AutoSyncAdDomain {
    private Integer id;
    private String companyId;
    private String autoConfig;

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

    public String getAutoConfig() {
        return autoConfig;
    }

    public void setAutoConfig(String autoConfig) {
        this.autoConfig = autoConfig;
    }

    @Override
    public String toString() {
        return "AutoSyncAdDomain{" +
                "id=" + id +
                ", companyId='" + companyId + '\'' +
                ", autoConfig='" + autoConfig + '\'' +
                '}';
    }
}
