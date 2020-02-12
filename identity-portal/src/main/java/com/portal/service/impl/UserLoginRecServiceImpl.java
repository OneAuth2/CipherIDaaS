package com.portal.service.impl;



import com.portal.daoAuthoriza.UserLoginRecDAO;
import com.portal.domain.UserLoginRecInfo;
import com.portal.service.UserLoginRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserLoginRecServiceImpl implements UserLoginRecService {

    @Autowired
    private UserLoginRecDAO userLoginRecDAO;


    @Override
    public UserLoginRecInfo selectUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo) {
        return userLoginRecDAO.selectUserLoginRecInfo(userLoginRecInfo);
    }

    @Override
    public void insertUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo) {
        userLoginRecDAO.insertUserLoginRecInfo(userLoginRecInfo);
    }

    @Override
    public void updateUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo) {
        userLoginRecDAO.updateUserLoginRecInfo(userLoginRecInfo);
    }

    @Override
    public UserLoginRecInfo selectUserLoginRecInfoByUUid(String uuid) {
        return userLoginRecDAO.selectUserLoginRecInfoByUUid(uuid);
    }

    @Override
    public boolean insertUserLoginRecInfoWithUUid(UserLoginRecInfo userLoginRec) {
        try {
            userLoginRecDAO.insertUserLoginRecInfoWithUUid(userLoginRec);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateUserLoginRecInfoUUid(String userId) {
        try {
            userLoginRecDAO.updateUserLoginRecInfoUUid(userId);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
