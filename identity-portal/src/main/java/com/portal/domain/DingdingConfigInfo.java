package com.portal.domain;


import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/5/8 15:30
 */
public class DingdingConfigInfo implements Serializable {


    public static final int PHONE_NUMBER=0;

    public static final int MAIL=1;

    public static final  int PHONE_AND_MAIL=2;

    public static final int PHONE_OR_MAIL=3;



    private String   id ;
    private String  companyUUid ;
    private String scanAppId;
    private String scanAppSecret;
    private String corpId;
    private String corpSecret;
    private String appKey;
    private String appSecret;
    private int matchRule;

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

    public String getScanAppId() {
        return scanAppId;
    }

    public void setScanAppId(String scanAppId) {
        this.scanAppId = scanAppId;
    }

    public String getScanAppSecret() {
        return scanAppSecret;
    }

    public void setScanAppSecret(String scanAppSecret) {
        this.scanAppSecret = scanAppSecret;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public int getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(int matchRule) {
        this.matchRule = matchRule;
    }
}
