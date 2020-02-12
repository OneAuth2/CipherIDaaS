package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/9/29
 *
 * @author liuying
 * @since 2019/9/29 11:40
 */
public class WeiXinConfig implements Serializable {

    private String corpId;
    private String callbackUrl;
    private String corpSecret;
    private String companyUUid;
    private int matchRule;
    private String agentId;

    public int getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(int matchRule) {
        this.matchRule = matchRule;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getCompanyUUid() {
        return companyUUid;
    }

    public void setCompanyUUid(String companyUUid) {
        this.companyUUid = companyUUid;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
