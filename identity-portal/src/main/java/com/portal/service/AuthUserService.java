package com.portal.service;


import com.portal.domain.UserInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/8/27 9:23
 */
public interface AuthUserService {

    public UserInfoDomain queryUserByName(String userName);

}
