package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.ApplicationAuditExcle;
import cipher.console.oidc.domain.web.ApplicationAuditInfo;
import cipher.console.oidc.domain.web.ApplicationInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */

public interface ApplicationAuditInfoService {

    public Map<String,Object> getApplicationAuditPageList(ApplicationAuditInfo form, DataGridModel pageModel);

    public Map<String,Object> getApplicationPageList(ApplicationInfo form, DataGridModel pageModel);

    List<ApplicationInfo> getApplicationList(ApplicationInfo form,DataGridModel pageModel);

    List<ApplicationAuditExcle> exportExcle(ApplicationAuditInfo form);

    List<ApplicationInfo> getApplyList(String applicationId,String companyId);


    ApplicationInfo getApplyInfo(ApplicationInfo form);

    void insertApplicationAuditInfo(ApplicationAuditInfo form);

    Map<String,Object> getChartData(List<String> list,String companyId);
}
