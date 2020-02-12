package com.portal.service;


import com.portal.domain.UserBehaviorInfo;

import java.util.List;

/**
 * Created by 95744 on 2018/6/14.
 */
public interface UserBehaviorService {

    public void insertUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo);

    UserBehaviorInfo selectUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo);

    List<String> getIpList(String userName);

}
