package com.portal.service;

/**
 * @Author: zt
 * @Date: 2019/1/17 10:05
 */
public interface PlatUserService {

    /**
     * 根据平台用户的手机号查询用户id
     * @param phoneNumber
     */
    public Integer queryPlatUserIdByPhoneNumber(String phoneNumber);

}
