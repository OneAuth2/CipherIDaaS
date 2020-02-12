package com.portal.domain;


import java.io.Serializable;

public class WangyiConfigInfoDomain implements Serializable{

    private String product;
    private String domain;
    private String secret;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
