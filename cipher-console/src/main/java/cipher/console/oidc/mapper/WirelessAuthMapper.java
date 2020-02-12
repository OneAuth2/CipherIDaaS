package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.authsettingsdomain.IdentitySettingInfo;

public interface WirelessAuthMapper {
    int companyUuidCount(String companyUuid);
    void insertWirelessAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    void updateWirelessAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    IdentitySettingInfo selectWirelessAuthBycompanyUuid(String companyUuid);
}
