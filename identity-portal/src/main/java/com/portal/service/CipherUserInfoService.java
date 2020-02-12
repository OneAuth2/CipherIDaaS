package com.portal.service;


import com.portal.domain.UserInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/11/30 10:04
 */
public interface CipherUserInfoService {

    /**
     * 根据用户名查找用户的AD域来源
     *
     * @param userName
     * @return
     */
    public UserInfoDomain queryUserByName(String userName);


    /**
     * 修改本地的Ad用户策略
     *
     * @param accountNumber
     * @param control
     */
    public void modifyUserAccountControl(String accountNumber, int control,String companyId);


    /*
    * 添加用户信息
    *
    * */

    public UserInfoDomain insertUserInfo(UserInfoDomain userInfoDomain);

}
