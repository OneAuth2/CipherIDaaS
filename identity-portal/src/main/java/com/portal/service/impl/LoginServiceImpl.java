package com.portal.service.impl;



import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.IdentityAuthentication;
import com.portal.domain.UserLoginRecInfo;

import com.portal.domain.LoginInfo;
import com.portal.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 登录功能的实现层
 * create by shizhao at 2019/5/27
 *
 * @author shizhao
 * @since 2019/5/17
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public LoginInfo getLoginInfo(UserLoginRecInfo userLoginRecInfo,
                                  IdentityAuthentication identityAuthentication,
               IdentityAuthTypeDomain identityAuthTypeDomain, String uuid) {
            //获取是否首次登陆
            int firstLogin = (null == userLoginRecInfo || StringUtils.isEmpty(userLoginRecInfo.getFirstFaceLoginTime())) ?
                0 : 1;

        //获取是否更新密码
        int updatePwd = identityAuthentication.getFirstLogin() == 0 ?
                0 : 1;

        //获取是否进行信息手机号采集
        int infoCollection = identityAuthentication.getInfoCollection();

        //获取是否进行信息邮箱采集
        int infoCollectionMail = identityAuthentication.getInfoCollectionMail();

        //获取用户的UUID
        String userId = uuid;


        int secondLogin = identityAuthTypeDomain.getSecondAuth();


        int switches=identityAuthTypeDomain.getSwitches();


        return new LoginInfo(firstLogin, updatePwd, secondLogin, infoCollection, infoCollectionMail,switches,uuid);
    }
}
