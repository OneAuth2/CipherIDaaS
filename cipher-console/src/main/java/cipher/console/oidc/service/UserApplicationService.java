package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.UserApplicationMapInfo;

public interface UserApplicationService {

    int deleteUserAuth(Integer applicationId);
    UserApplicationMapInfo selectUserApplicationInfo(UserApplicationMapInfo record);

    int insertSelective(UserApplicationMapInfo record);
}
