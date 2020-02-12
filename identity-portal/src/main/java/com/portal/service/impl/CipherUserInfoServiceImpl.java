package com.portal.service.impl;


import com.portal.daoAuthoriza.CipherUserInfoDAO;
import com.portal.domain.UserInfoDomain;
import com.portal.service.CipherUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2018/11/30 10:06
 */

@Service
public class CipherUserInfoServiceImpl implements CipherUserInfoService {

    @Autowired
    private CipherUserInfoDAO cipherUserInfoDAO;

    @Override
    public UserInfoDomain queryUserByName(String userName) {
        return cipherUserInfoDAO.queryUserByName(userName);
    }

    @Override
    public void modifyUserAccountControl(String accountNumber, int control,String companyId) {
        cipherUserInfoDAO.modifyUserAccountControl(accountNumber, control,companyId);
    }

    @Override
    public UserInfoDomain insertUserInfo(UserInfoDomain userInfoDomain) {
        return cipherUserInfoDAO.insertUserInfo(userInfoDomain);
    }


}
