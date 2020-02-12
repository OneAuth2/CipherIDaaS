package cipher.console.oidc.domain;

import java.io.Serializable;

public class BaseParamDomain implements Serializable {

    private String companyId;
    private String uuid;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
