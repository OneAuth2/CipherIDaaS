package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.CompanyInfoDomain;

public interface CompanyInfoMapper {

    CompanyInfoDomain getCompanyInfoById(String companyId);

    void updateLeftCount(CompanyInfoDomain companyInfoDomain);
}
