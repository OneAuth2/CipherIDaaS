package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AuthorizationMethodDomain;
import cipher.console.oidc.mapper.AuthorizationMethodMapper;
import cipher.console.oidc.service.AuthorizationMethodService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationMethodImpl implements AuthorizationMethodService {

    @Autowired
    AuthorizationMethodMapper authorizationMethodMapper;

    @Override
    public List<AuthorizationMethodDomain> queryAllAuthorizationMethod() {
        return authorizationMethodMapper.queryAllAuthorizationMethod();
    }

    @Override
    public void updateAuthorizationMethod(int status, String method) {
        authorizationMethodMapper.updateAuthorizationMethod(status,method);
    }

    @Override
    public List<AuthorizationMethodDomain> queryUserAuthorizationMethod(String accountNumber) {
        return authorizationMethodMapper.queryUserAuthorizationMethod(accountNumber);
    }
}
