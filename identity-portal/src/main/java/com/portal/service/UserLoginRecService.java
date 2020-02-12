package com.portal.service;


import com.portal.domain.UserLoginRecInfo;

public interface UserLoginRecService {

    public UserLoginRecInfo selectUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    public void insertUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    public void updateUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    UserLoginRecInfo selectUserLoginRecInfoByUUid(String uuid);

    boolean insertUserLoginRecInfoWithUUid(UserLoginRecInfo userLoginRec);

    boolean updateUserLoginRecInfoUUid(String userId);

}
