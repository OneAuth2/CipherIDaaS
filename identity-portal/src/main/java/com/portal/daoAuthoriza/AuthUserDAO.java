package com.portal.daoAuthoriza;


import com.portal.domain.UserInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/8/27 9:50
 */
public interface AuthUserDAO {


    /**
     * 根据用户名查询账号的状态信息，是否过期？是否被停用？
     * @param userName 用户名
     * @return userInfoDomain
     * @throws Exception not mapped
     */
    public UserInfoDomain queryUserAuthInfo(String userName);

}
