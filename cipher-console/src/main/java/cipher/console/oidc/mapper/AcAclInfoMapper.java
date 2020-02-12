package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AcAclInfo;

import java.util.List;

public interface AcAclInfoMapper {

    List<AcAclInfo> selectAclInfo(AcAclInfo acAclInfo);

    void insertAclInfo(AcAclInfo acAclInfo);

    AcAclInfo selectAclInfoByMac(String mac);

    void updateAclInfoByMac(AcAclInfo acAclInfo);

    void deleteAclInfoByMac(String mac);

    Integer selectCount(AcAclInfo acAclInfo);

    void updateAclInfo(AcAclInfo acAclInfo);

}
