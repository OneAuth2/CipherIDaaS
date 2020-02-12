package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;

import java.util.List;
import java.util.Map;

public interface IdentityStrategyService {

    Map<String,Object> queryIdentityStrategyList(IdentityStrategyDomain identityStrategyDomain);
    //int  getAllIdentityStrategyList(IdentityStrategyDomain identityStrategyDomain);
    void saveIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    void deleteIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    void modifyIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    IdentityStrategyDomain queryIdentityStrategyById(IdentityStrategyDomain identityStrategyDomain);

    int queryCountByStrategyName(Integer id,String strategyName);

    int queryCountByPriority(Integer id,Integer priority);



}
