package cipher.console.oidc.service.authsettings;

import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;

import java.util.Map;

public interface ExtManAuthService {
    Map<String,Object> compileExtManAuth(String companyUuid, IdentityAuthentication identityAuthentication, AccountBinding accountBinding);
    Map<String,Object> echoExtManAuth(String companyUuid);
}
