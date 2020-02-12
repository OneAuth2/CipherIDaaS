package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AppSonAccountDomain;
import cipher.console.oidc.domain.web.SubAccountInfoDomain;

import java.util.List;
import java.util.Map;

public interface SubDownService {

    public Map<String,Object> getSubDownInfo(String appClientId, List<AppSonAccountDomain> userList);

    public Map<String, Object> doSaveSubAccount(SubAccountInfoDomain form) ;

}
