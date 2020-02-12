package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;

import java.util.List;
import java.util.Map;

public interface DynamicStateService {

    Map<String,Object> getDynamicStateList(String queryName, Integer issueStatus, String companyId, DataGridModel pageModel);

    Map<String,Object> issueSeedKey(String userId,String companyId);

    Map<String,Object> resetSeedkey(String userId,String companyId);

    List<String> getSelectedUserId(String companyId);

    Map<String,Object> batchIssueSeedkey(String userIds,String companyId);
}
