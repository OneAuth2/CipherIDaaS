package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.authsettingsdomain.IdentitySettingInfo;

public interface ExtManAuthMapper {
    int companyUuidCount(String companyUuid);
    void insertExtManAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    void updateExtManAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    IdentitySettingInfo selectExtManAuthBycompanyUuid(String companyUuid);
}
