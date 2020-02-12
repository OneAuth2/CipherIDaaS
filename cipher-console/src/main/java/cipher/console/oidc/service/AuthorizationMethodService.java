package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AuthorizationMethodDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthorizationMethodService {

    /**
     * 查询所有的授权方式
     * @return 授权方式的列表
     * */
    public List<AuthorizationMethodDomain> queryAllAuthorizationMethod();

    /**
     * 更新授权方式的状态
     * @param status 授权方式的状态
     * @param method 授权方式的名称
     * */
    public void updateAuthorizationMethod(@Param(value = "status")int status,
                                          @Param(value = "method")String method);

    /**
     * 更新授权方式的状态
     * @param accountNumber 主账号
     * @return 授权关系的列表
     * */
    public List<AuthorizationMethodDomain> queryUserAuthorizationMethod(String accountNumber);
}
