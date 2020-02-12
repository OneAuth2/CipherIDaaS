package com.portal.daoAuthoriza;

import com.portal.domain.EquipBehaviorInfo;

public interface EquipBehaviorInfoDAO {

    String selectUserInfoByUserId(String UserId);

    void insertEquipBehavior(EquipBehaviorInfo equipBehaviorInfo) throws Exception;

}