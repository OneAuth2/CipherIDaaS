package cipher.console.oidc.service;


import cipher.console.oidc.domain.web.UserInfoDomain;

public interface TokenService {

    String setLoginToken(UserInfoDomain user, String ip);

    String getLoginToken(String username, String ip);



}
