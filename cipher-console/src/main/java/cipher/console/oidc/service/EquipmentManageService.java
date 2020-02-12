package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.domain.web.VpnConfigurationDomain;

import java.util.List;
import java.util.Map;

public interface EquipmentManageService {
    List<VpnConfigurationDomain> getVpnConfigList(String companyId,VpnConfigurationDomain vpnConfigurationDomain);

    int getVpnConfigCount(String companyId);

    Map<String,Object> modifyVpnConfig(VpnConfigurationDomain vpnConfigurationDomain, UserInfoDomain userInfoDomain);

    Map<String,Object> vpnConfigByUuid(String uuid,String companyId);

    Map<String,Object> vpnConfigDel(String uuid,String companyId,UserInfoDomain userInfoDomain);
}
