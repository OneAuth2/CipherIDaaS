package com.portal.service.impl;


import com.alibaba.dubbo.config.annotation.Service;


import com.portal.daoAuthoriza.UserDAO;
import com.portal.model.UserAgentModel;
import com.portal.service.UserAgentInfoService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserAgentInfoServiceImpl implements UserAgentInfoService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public void insertUserAgentInfo(UserAgentModel userAgentModel) {
        userDAO.insertUserAgentInfo(userAgentModel);
    }
}
