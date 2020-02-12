package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;

import java.util.Map;

public interface UserCompanyMapService {
     Map<String,Object> getCompanyId(String accountNumber);

    Map<String, Object> getUserInfo( String queryName,String userName,
                                    DataGridModel pageModel);


}
