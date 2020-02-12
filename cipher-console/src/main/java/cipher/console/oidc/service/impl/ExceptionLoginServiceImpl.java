package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.ExceptionLoginDomain;
import cipher.console.oidc.mapper.ExceptionLoginMapper;
import cipher.console.oidc.service.ExceptionLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExceptionLoginServiceImpl implements ExceptionLoginService {

    @Autowired
    private ExceptionLoginMapper exceptionLoginMapper;

    @Override
    public ExceptionLoginDomain queryExceptionLoginObj(String companyId) {
        return exceptionLoginMapper.queryExceptionLoginObj(companyId);
    }

    @Override
    public void updateExceptionLogin(ExceptionLoginDomain exceptionLoginDomain) {
        exceptionLoginMapper.updateExceptionLogin(exceptionLoginDomain);
    }

    @Override
    public int insertExceptionLogin(ExceptionLoginDomain exceptionLoginDomain) {
        return exceptionLoginMapper.insertExceptionLogin(exceptionLoginDomain);
    }
}
