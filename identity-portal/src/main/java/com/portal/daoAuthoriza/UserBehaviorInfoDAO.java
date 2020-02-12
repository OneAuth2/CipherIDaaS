package com.portal.daoAuthoriza;



import com.portal.domain.UserBehaviorInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserBehaviorInfoDAO {

    void insertSelective(UserBehaviorInfo record) throws Exception;

    UserBehaviorInfo selectUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo);

    List<String> getIpList(@Param("userName") String userName);

    String selectUserInfoByUserId(String UserId);

}