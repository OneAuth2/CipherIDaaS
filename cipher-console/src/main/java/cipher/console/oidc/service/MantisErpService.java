package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.ErpConfigInfo;

public interface MantisErpService {

    ErpConfigInfo selectTheConfig(String companyId);

    void  updateTheConfig(ErpConfigInfo erpConfigInfo);

}
