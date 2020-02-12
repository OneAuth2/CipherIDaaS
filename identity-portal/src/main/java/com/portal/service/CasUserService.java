package com.portal.service;

import com.portal.domain.CasUserInfoDomain;
import com.portal.domain.UserInfoDomain;

/**
 * @Author: 安歌
 * @Date: 2019/7/25 14:37
 */
public interface CasUserService {

    /**
     * create by 安歌
     * create time 2019年7月25日14:45:00
     * 根据用户的主账号 获取用户的信息信息包括（姓名、工号、部门、岗位。邮箱。手机）
     * @param userInfoDomain
     * @return
     */
    public CasUserInfoDomain getCasUserByUuid(UserInfoDomain userInfoDomain);
}
