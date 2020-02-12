package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.EquipBehaviorExcle;
import cipher.console.oidc.domain.web.EquipBehaviorDomain;
import cipher.console.oidc.domain.web.EquipBehaviorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquipBehaviorMapper {

    List<EquipBehaviorDomain> getVpnList(EquipBehaviorDomain equipBehaviorDomain);

    int selectVpnCount(EquipBehaviorDomain equipBehaviorDomain);

    int selectRecentlySeven(@Param(value = "vpnId") String vpnId, @Param(value = "companyId")String companyId);

    int selectRecentlyThirty(@Param(value = "vpnId")String vpnId,@Param(value = "companyId")String companyId);

    List<EquipBehaviorInfo> getEquipLogList(EquipBehaviorInfo equipBehaviorInfo);

    int selectEquipLogCount(EquipBehaviorInfo equipBehaviorInfo);

    List<EquipBehaviorExcle> selectEquipBehaviorExcle(EquipBehaviorInfo equipBehaviorInfo);
}
