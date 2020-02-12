package com.portal.service.impl;



import com.portal.daoAuthoriza.PlatUserDAO;
import com.portal.service.PlatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2019/1/17 10:07
 */
@Service
public class PlatUserServiceImpl implements PlatUserService {

    @Autowired
    private PlatUserDAO platUserDAO;

    @Override
    public Integer queryPlatUserIdByPhoneNumber(String phoneNumber) {
        return platUserDAO.queryPlatUserIdByPhone(phoneNumber);
    }


}
