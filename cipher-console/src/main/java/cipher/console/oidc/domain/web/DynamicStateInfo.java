package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.DataGridModel;

public class DynamicStateInfo {
    private String queryName;
    private String companyId;
    private DataGridModel pageModel;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public DataGridModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(DataGridModel pageModel) {
        this.pageModel = pageModel;
    }

    @Override
    public String toString() {
        return "DynamicStateInfo{" +
                "queryName='" + queryName + '\'' +
                ", companyId='" + companyId + '\'' +
                ", pageModel=" + pageModel +
                '}';
    }
}
