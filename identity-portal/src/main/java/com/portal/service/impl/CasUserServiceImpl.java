package com.portal.service.impl;

import com.portal.domain.CasUserInfoDomain;
import com.portal.domain.UserInfoDomain;
import com.portal.service.CasUserService;
import com.portal.service.GroupService;
import com.portal.service.UserInfoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 安歌
 * @Date: 2019/7/25 14:43
 */
@Service
public class CasUserServiceImpl implements CasUserService {

    private static Logger logger= LoggerFactory.getLogger(CasUserServiceImpl.class);

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private GroupService groupService;

    @Override
    public CasUserInfoDomain getCasUserByUuid(UserInfoDomain userInfoDomain) {

        //入参校验
        if (userInfoDomain  == null || StringUtils.isEmpty(userInfoDomain.getUserId())){
            logger.error("enter CasUserServiceImpl.getCasUserByUuid error by userInfoDomain is null !",userInfoDomain);
            return null;
        }

        //获取用户信息
        UserInfoDomain localUserInfoDomain=userInfoService.getUserInfoAndGroupsByUUid(userInfoDomain.getUserId());
        //对结果进行判断是否为空
        if (localUserInfoDomain == null) {
            logger.error("enter CasUserServiceImpl.getCasUserByUuid error by localUserInfoDomain is null !",localUserInfoDomain);
            return null;
        }
        //本地用户信息转换成cas需要的用户信息
        CasUserInfoDomain casUserInfoDomain=getUserFromLocalUserInfoDomainToCasUserInfoDomain(localUserInfoDomain);
        return casUserInfoDomain;
    }

    /**
     * 构造返回数据
     * create by 安歌
     * create time 2019年7月25日16:56:23
     * @param localUserInfoDomain
     * @return
     */
    private CasUserInfoDomain getUserFromLocalUserInfoDomainToCasUserInfoDomain(UserInfoDomain localUserInfoDomain) {

        //入参校验
        if (localUserInfoDomain == null){
            return null;
        }
        //构造参数并且给参数赋值
        CasUserInfoDomain casUserInfoDomain=new CasUserInfoDomain();
        casUserInfoDomain.setUserName(localUserInfoDomain.getUserName());
        casUserInfoDomain.setPhone(localUserInfoDomain.getPhoneNumber());
        casUserInfoDomain.setMail(localUserInfoDomain.getMail());
        casUserInfoDomain.setGroups(localUserInfoDomain.getGroupName());
        casUserInfoDomain.setJob(localUserInfoDomain.getJob());
        casUserInfoDomain.setJonNo(localUserInfoDomain.getAccountNumber());
        //返回
        return casUserInfoDomain;
    }


}
