package com.portal.service;


import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.IdentityAuthentication;
import com.portal.domain.LoginInfo;
import com.portal.domain.UserLoginRecInfo;

import javax.validation.constraints.NotNull;

/**
 * 登录操作的service
 * create by shizhao at 2019/5/17
 *
 * @author shizhao
 * @since  2019/5/17
 * */
public interface LoginService {

    LoginInfo getLoginInfo(@NotNull UserLoginRecInfo userLoginRecInfo,
                           @NotNull IdentityAuthentication identityAuthentication,
                           @NotNull IdentityAuthTypeDomain identityAuthTypeDomain,
                           String uuid);

}
