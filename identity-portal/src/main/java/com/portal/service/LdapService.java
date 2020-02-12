package com.portal.service;


import com.portal.commons.GlobalReturnCode;

/**
 * @Author: zt
 * @Date: 2019-04-08 17:03
 */
public interface LdapService {


    /**
     * 修改AD账号的密码
     *
     * @param uuid 账号
     * @param pwd           密码
     * @return
     */
    public GlobalReturnCode.MsgCodeEnum modifyPwd(String uuid, String pwd);

    /**
     * 修改用户的userAccountContro密码策略属性
     * 设置用户的密码永久有效
     * @param accountNumber
     * @return
     */
    public GlobalReturnCode.MsgCodeEnum modifyUserAccountContro(String accountNumber);


}
