package com.portal.daoAuthoriza;


import com.portal.domain.UserLoginRecInfo;

public interface UserLoginRecDAO {

    public UserLoginRecInfo selectUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    public void insertUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    public void updateUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    UserLoginRecInfo selectUserLoginRecInfoByUUid(String uuid);

    void insertUserLoginRecInfoWithUUid(UserLoginRecInfo userLoginRec);

    void updateUserLoginRecInfoUUid(String userId);
}
