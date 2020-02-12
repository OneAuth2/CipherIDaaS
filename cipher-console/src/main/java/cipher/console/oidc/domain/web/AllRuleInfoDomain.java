package cipher.console.oidc.domain.web;

import java.util.List;

/**
 * @Author: TK
 * @Date: 2018/12/4 14:59
 */
public class AllRuleInfoDomain {
    private String applicationId;
    private String accountType;
    private String  ruleValue;
    private String  initPwd;
    private List<SubSynLocalDomain> subSynLocalDomainList;

    public List<SubSynLocalDomain> getSubSynLocalDomainList() {
        return subSynLocalDomainList;
    }

    public void setSubSynLocalDomainList(List<SubSynLocalDomain> subSynLocalDomainList) {
        this.subSynLocalDomainList = subSynLocalDomainList;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getInitPwd() {
        return initPwd;
    }

    public void setInitPwd(String initPwd) {
        this.initPwd = initPwd;
    }

    @Override
    public String toString() {
        return "AllRuleInfoDomain{" +
                "applicationId='" + applicationId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", ruleValue='" + ruleValue + '\'' +
                ", initPwd='" + initPwd + '\'' +
                ", subSynLocalDomainList=" + subSynLocalDomainList +
                '}';
    }
}
