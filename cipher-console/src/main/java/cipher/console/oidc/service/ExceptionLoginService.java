package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.ExceptionLoginDomain;

public interface ExceptionLoginService {

    ExceptionLoginDomain queryExceptionLoginObj(String companyId);

    void updateExceptionLogin(ExceptionLoginDomain exceptionLoginDomain);

    int insertExceptionLogin(ExceptionLoginDomain exceptionLoginDomain);
}
