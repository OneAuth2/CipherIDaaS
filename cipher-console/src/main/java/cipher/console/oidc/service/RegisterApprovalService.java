package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;

import java.util.Map;

public interface RegisterApprovalService {
    Map<String, Object> getApprovalList(String companyUuid, DataGridModel pageModel);

    Map<String, Object> getRecordsList(String companyUuid, DataGridModel pageModel);

    Map<String, Object> getApprovalResult(String companyUuid, String uuid,Integer status);
}
