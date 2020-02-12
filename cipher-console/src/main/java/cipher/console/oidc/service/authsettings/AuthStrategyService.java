package cipher.console.oidc.service.authsettings;

import cipher.console.oidc.domain.authsettingsdomain.AuthStrategyDomain;
import cipher.console.oidc.domain.authsettingsdomain.SecondAuthStrategyDomain;

import java.util.Map;

public interface AuthStrategyService {
    Map<String,Object> compileAuthStrategy(String uuid,String companyUuid,
                                           AuthStrategyDomain authStrategyDomain,
                                           SecondAuthStrategyDomain secondAuthStrategyDomain);
    Map<String,Object> echoAuthStrategy(String uuid);
}
