package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ExceptionLoginDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;

import java.util.List;

/**
 * @Author: yw
 *  异常登录设置
 */

public interface ExceptionLoginMapper {

    ExceptionLoginDomain queryExceptionLoginObj(String companyId);

    void updateExceptionLogin(ExceptionLoginDomain exceptionLoginDomain);

    int insertExceptionLogin(ExceptionLoginDomain exceptionLoginDomain);

}
