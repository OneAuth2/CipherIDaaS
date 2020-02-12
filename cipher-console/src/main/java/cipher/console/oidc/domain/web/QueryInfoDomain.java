package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class QueryInfoDomain extends BaseDomain {

    private String queryName;

    private Integer applicationId;

    private  int isSynchron;


    public Integer getIsSynchron() {
        return isSynchron;
    }

    public void setIsSynchron(Integer isSynchron) {
        this.isSynchron = isSynchron;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }
}
