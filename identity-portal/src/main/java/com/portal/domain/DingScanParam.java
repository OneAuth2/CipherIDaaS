package com.portal.domain;


import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:17
 */
public class DingScanParam implements Serializable {

    private Integer id;
    private String companyUuid;  //公司id
    private String clientId;   //对应公司的clientid
    private String  secretKey;  //s对应公司的ecretKey

    public DingScanParam(Integer id, String companyUuid, String clientId, String secretKey) {
        this.id = id;
        this.companyUuid = companyUuid;
        this.clientId = clientId;
        this.secretKey = secretKey;
    }

    public DingScanParam() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
