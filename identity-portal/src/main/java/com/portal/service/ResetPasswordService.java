package com.portal.service;



import com.portal.commons.GlobalAuthType;
import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.SecondLoginInfo;

import java.util.Map;

public interface ResetPasswordService {


    Map<String,Object> updateUserPwd(String username, String newpwd, String pwd,String companyUuid);


    Map<String,Object> checkUserPwd(String username, String newpwd, String pwd,String companyUuid);

    Map<String, Object> setNewPassword(String companyUUid, String accountNumber, String pwd);

    GlobalAuthType getGlobalAuth(String companyUUid, String accountNumber);



    GlobalAuthType getGlobalAuthByIdentity(IdentityAuthTypeDomain identityAuthTypeDomain);

    GlobalAuthType getGlobalAuth(IdentityAuthTypeDomain identityAuthTypeDomain, String companyUUid,String userId);


    IdentityAuthTypeDomain getIdentityAuthType(String companyUUid, String accountNumber);

    SecondLoginInfo getSecondLoginInfo(String companyUUid, String userId);

    boolean resetPassword(String companyId,String userId,String password);


    boolean resetAdPwd(String uuid, String newpwd,String companyUuid);

}
