package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.LoginFailInfo;

/**
 * Created by 95744 on 2018/8/27.
 */
public interface LoginFailService {


    public LoginFailInfo getLoginFailInfo(LoginFailInfo loginFailInfo);

    public int updateLoginFailInfo(LoginFailInfo loginFailInfo);

    public int insertLoginFailInfo(LoginFailInfo loginFailInfo);
}
