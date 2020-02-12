package com.portal.service;

/**
 * @Author: zt
 * @Date: 2019/1/17 10:53
 */
public interface PlatCipherUserMapService {

    public String queryCipherUserByPlatUserId(Integer platUserId);

    public Integer queryPlatUserIdByCipherUser(String accountNumber);

}
