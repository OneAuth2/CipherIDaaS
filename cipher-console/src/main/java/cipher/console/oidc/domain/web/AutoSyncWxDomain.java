package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-15
 */

/**
 * @Author qiaoxi
 * @Date 2019-10-1514:33
 **/
public class AutoSyncWxDomain {
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
        return "AutoSyncWxDomain{" +
                "id='" + id + '\'' +
                ", companyUUid='" + companyUUid + '\'' +
                ", syncConfig='" + syncConfig + '\'' +
                '}';
    }
}
