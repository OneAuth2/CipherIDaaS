package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AcAclInfo;

import java.util.List;

public interface AcAclInfoService {

    List<AcAclInfo> selectAclInfo(AcAclInfo acAclInfo);

    void  insertAclInfo(AcAclInfo acAclInfo);

    boolean isExist(String mac);

    void updateAclInfoByMac(AcAclInfo acAclInfo);

    void deleteAclInfoByMac(String mac);

    Integer selectCount(AcAclInfo acAclInfo);

    void updateAclInfo(AcAclInfo acAclInfo);



}
