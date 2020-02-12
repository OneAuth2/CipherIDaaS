package com.portal.domain;

import java.io.Serializable;

public class TengXunConfigInfoDomain implements Serializable{
    private String cropSecret;
    private String cropId;

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
