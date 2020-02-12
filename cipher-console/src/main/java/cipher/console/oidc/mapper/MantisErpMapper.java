package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ErpConfigInfo;

public interface MantisErpMapper {
    ErpConfigInfo selectTheConfig(String companyId);

    void  updateTheConfig(ErpConfigInfo erpConfigInfo);
}
