package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.CompanyInfoDomain;

public interface CompanyInfoService {

    CompanyInfoDomain getCompanyInfoById(String userName);

}
