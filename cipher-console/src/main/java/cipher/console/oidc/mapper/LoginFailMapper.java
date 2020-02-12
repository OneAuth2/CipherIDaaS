package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.LoginFailInfo;
import cipher.console.oidc.domain.web.LoginFailedConfigEntity;

/**
 * Created by 95744 on 2018/9/1.
 */
public interface LoginFailMapper {

    public LoginFailInfo getLoginFailInfo(LoginFailInfo loginFailInfo);

    public int updateLoginFailInfo(LoginFailInfo loginFailInfo);

    public int insertLoginFailInfo(LoginFailInfo loginFailInfo);

    public LoginFailedConfigEntity queryLoginFailedUser();
}
