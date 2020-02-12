package com.portal.service;


import com.portal.domain.LoginFailedConfigEntity;
import com.portal.domain.UserLoginFailedInfo;

public interface LoginFailedUserService {



    /**
     * 获取系统登录失败配置信息
     *
     */
    LoginFailedConfigEntity getLoginFailedConfig();

    /**
     * 获取用户登录失败信息
     *
     */
    UserLoginFailedInfo getUserLoginFailedInfo(String account);


    /**
     * 更新用户登录失败信息
     *
     */
    void updateUserLoginFailedInfo(UserLoginFailedInfo userLoginFailedInfo, String account);

    /**
     * 判断用户是否被锁定 true-锁定，false-未锁定
     */
    boolean isUserFreezed(String account);

    /**
     * 更新登录失败次数数据
     *
     */
    void loginFailedValidate(String account,String ip);


    /**
     * 清除登录失败信息
     * */
    void clearUserLoginFailedInfo(String account);



}
