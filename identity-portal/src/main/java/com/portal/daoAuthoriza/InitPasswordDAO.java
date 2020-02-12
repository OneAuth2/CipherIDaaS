package com.portal.daoAuthoriza;


import com.portal.domain.PasswordSetting;

/**
 * 获取初始密码
 * @Author: zt
 * @Date: 2018/10/16 17:22
 */
public interface InitPasswordDAO {

    /**
     * 获取全局初始密码
     * @return
     */
    public String getInitPwd();

    /**
     * 获取对应公司的密码配置
     * */
    PasswordSetting getPasswordSetting(String companyId);

}
