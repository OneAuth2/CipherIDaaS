package com.portal.service.impl;



import com.portal.daoAuthoriza.UserBehaviorInfoDAO;
import com.portal.domain.UserBehaviorInfo;
import com.portal.service.UserBehaviorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/6/14.
 */

@Service
public class UserBehaviorServiceImpl implements UserBehaviorService {

    private static final Logger logger = LoggerFactory.getLogger(UserBehaviorServiceImpl.class);

    @Autowired
    private UserBehaviorInfoDAO userBehaviorInfoDAO;

    @Override
    public void insertUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo){
        try {
            userBehaviorInfoDAO.insertSelective(userBehaviorInfo);
        } catch (Exception e) {
           logger.error("Enter UserBehaviorServiceImpl.insertUserBehaviorInfo() but failed..==");
           logger.error(e.getMessage(), e);
        }
    }

    @Override
    public UserBehaviorInfo selectUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo) {
        return userBehaviorInfoDAO.selectUserBehaviorInfo(userBehaviorInfo);
    }

    @Override
    public List<String> getIpList(String userName) {
        return userBehaviorInfoDAO.getIpList(userName);
    }
}
