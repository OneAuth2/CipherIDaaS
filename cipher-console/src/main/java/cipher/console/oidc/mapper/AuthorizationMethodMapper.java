package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AuthorizationMethodDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorizationMethodMapper {
    /**
     * 获取当前所用授权方式情况
     * */
    List<AuthorizationMethodDomain> queryAllAuthorizationMethod();

    void updateAuthorizationMethod(@Param(value = "status")int status,
                                   @Param(value = "method")String method);

    public List<AuthorizationMethodDomain> queryUserAuthorizationMethod(String accountNumber);

}
