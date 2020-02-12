package cipher.console.oidc.service.impl;


import cipher.console.oidc.domain.bto.AppInfoBto;
import cipher.console.oidc.domain.bto.UserInfoBto;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;

import java.util.ArrayList;
import java.util.List;

public class ObjectFactoryImpl {

  public static List<UserInfoBto> objectConvertUserBtoList(List<NewUserInfo> userInfoDomainList) {
    List<UserInfoBto> userInfoBtoList = new ArrayList<UserInfoBto>();
    if (userInfoDomainList != null && !userInfoDomainList.isEmpty()) {
      for (NewUserInfo entity : userInfoDomainList) {
        UserInfoBto userInfoBto = new UserInfoBto();
        userInfoBto.setUuid(entity.getUuid());
        userInfoBto.setAccountNumber(entity.getAccountNumber());
        userInfoBto.setUserName(entity.getUserName());
        userInfoBto.setGroupName(entity.getGroupName());
        userInfoBto.setStatus(entity.getStatus());
        userInfoBto.setTeamName(entity.getTeamName());
        userInfoBtoList.add(userInfoBto);
      }
    }
    return userInfoBtoList;
  }



}
