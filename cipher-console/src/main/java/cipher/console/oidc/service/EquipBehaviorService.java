package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.EquipBehaviorExcle;
import cipher.console.oidc.domain.web.EquipBehaviorDomain;
import cipher.console.oidc.domain.web.EquipBehaviorInfo;

import java.util.List;
import java.util.Map;

public interface EquipBehaviorService {
    Map<String, Object> getEquipPageList(EquipBehaviorDomain equipBehaviorDomain, DataGridModel pageModel);

    Map<String, Object> getEquipLogList(EquipBehaviorInfo equipBehaviorInfo, DataGridModel pageModel);

    List<EquipBehaviorExcle> getEquipBehaviorExcle(EquipBehaviorInfo equipBehaviorInf);
}
