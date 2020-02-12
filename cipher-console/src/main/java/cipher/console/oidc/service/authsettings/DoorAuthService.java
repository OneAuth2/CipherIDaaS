package cipher.console.oidc.service.authsettings;

import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.AccountRegistration;
import cipher.console.oidc.domain.authsettingsdomain.ForgetPassword;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;

import java.util.Map;

public interface DoorAuthService {
    Map<String,Object> compileDoorAuth(String companyUuid,IdentityAuthentication identityAuthentication, AccountRegistration accountRegistration,
                        AccountBinding accountBinding, ForgetPassword forgetPassword);
    Map<String,Object> echoDoorAuth(String companyUuid);
}
