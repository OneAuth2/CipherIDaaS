package cipher.console.oidc.domain.web;

public class PortalLogInfo {
    private Integer portalId;
    private String portalName;

    public Integer getPortalId() {
        return portalId;
    }

    public void setPortalId(Integer portalId) {
        this.portalId = portalId;
    }

    public String getPortalName() {
        return portalName;
    }

    public void setPortalName(String portalName) {
        this.portalName = portalName;
    }

    @Override
    public String toString() {
        return "PortalLogInfo{" +
                "portalId=" + portalId +
                ", portalName='" + portalName + '\'' +
                '}';
    }
}
