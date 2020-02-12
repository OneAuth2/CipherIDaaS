package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.authsettingsdomain.IdentitySettingInfo;

public interface DoorAuthMapper {
    int companyUuidCount(String companyUuid);
    void insertDoorAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    void updateDoorAuth(IdentitySettingInfo identitySettingInfo) throws Exception;
    IdentitySettingInfo selectDoorAuthBycompanyUuid(String companyUuid);
}
