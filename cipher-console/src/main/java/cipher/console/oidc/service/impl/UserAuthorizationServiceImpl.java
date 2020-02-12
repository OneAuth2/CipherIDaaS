package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.UserAuthorizationMapDomain;
import cipher.console.oidc.mapper.UserAuthorizationMapper;
import cipher.console.oidc.service.UserAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    @Autowired
    UserAuthorizationMapper authorizationMapper;

    @Override
    public List<UserAuthorizationMapDomain> getUserAuthorizationMethods() {
        return authorizationMapper.getUserAuthorizationMethods();
    }

    @Override
    public int deleteByAccountNumber(String accountNumber) {
        return authorizationMapper.deleteByAccountNumber(accountNumber);
    }

    @Override
    public int insertInto(UserAuthorizationMapDomain userAuthorizationMapDomain) {
        return authorizationMapper.insertInto(userAuthorizationMapDomain);
    }

}
