package cipher.console.oidc.domain.web;

import java.io.Serializable;

public class TengXunConfigInfoDomain implements Serializable{
    private String cropSecret;
    private String cropId;
    private String deptMngId;

    public String getDeptMngId() {
        return deptMngId;
    }

    public void setDeptMngId(String deptMngId) {
        this.deptMngId = deptMngId;
    }

    public String getCropSecret() {
        return cropSecret;
    }

    public void setCropSecret(String cropSecret) {
        this.cropSecret = cropSecret;
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }
}
