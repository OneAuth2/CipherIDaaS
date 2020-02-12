package com.portal.service;


import com.portal.domain.PasswordSetting;

/**
 * @Author: zt
 * @Date: 2018/10/16 17:30
 */
public interface InitPasswordService {

    /**
     * 获取全局初始密码
     * @return
     */
    public String getInitPwd();


    /**
     * 获取对应公司的密码配置
     * */
    PasswordSetting getPasswordSetting(String companyId);

    /**
     * 判断密码是否可接受
     * */
    boolean isAcceptable(String password, PasswordSetting passwordSetting);

}
