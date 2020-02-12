package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.VpnConfigurationDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipmentManageMapper {
    List<VpnConfigurationDomain> selectVpnConfigList(VpnConfigurationDomain vpnConfigurationDomain);

    int obtainVpnConfigCount(String companyId);

    void updateVpnConfig(VpnConfigurationDomain vpnConfigurationDomain)throws Exception;

    void insertVpnConfig(VpnConfigurationDomain vpnConfigurationDomain)throws Exception;

    VpnConfigurationDomain vpnConfigByUuid(@Param(value = "uuid") String uuid,
                                           @Param(value = "companyId") String companyId);
    String selectDevicenameByUuid(String uuid);

    void vpnConfigDel(@Param(value = "uuid") String uuid,
                      @Param(value = "companyId") String companyId);
}
