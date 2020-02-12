package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.EquipBehaviorInfo;

public interface EquipBehaviorInfoMapper {

    String selectUserInfoByUserId(String UserId);

    void insertEquipBehavior(EquipBehaviorInfo equipBehaviorInfo) throws Exception;

}