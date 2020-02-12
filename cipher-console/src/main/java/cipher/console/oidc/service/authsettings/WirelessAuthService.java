package cipher.console.oidc.service.authsettings;

import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.AccountRegistration;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;

import java.util.Map;

public interface WirelessAuthService {
    Map<String,Object> compileWirelessAuth(String companyUuid, IdentityAuthentication identityAuthentication, AccountRegistration accountRegistration,
                                           AccountBinding accountBinding);
    Map<String,Object> echoWirelessAuth(String companyUuid);
}
