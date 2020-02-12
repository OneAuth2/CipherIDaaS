package com.portal.domain;

/**
 * TODO:
 * create by shizhao at 2019-12-11
 *
 * @author: shizhao
 * @since: 2019-12-11 10:40
 */
public class RSAPublicPrivateKeyEntity {

    private String publicKey;//公钥

    private String privateKey;//私钥

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
