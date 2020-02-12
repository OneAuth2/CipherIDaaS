package cipher.console.oidc.service.impl;

import cipher.console.oidc.service.TotpService;
import cipher.console.oidc.totp.GoogleAuthenticator;
import cipher.console.oidc.totp.GoogleAuthenticatorConfig;
import cipher.console.oidc.totp.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author: zt
 * @Date: 2018/12/18 11:35
 */
@Service
public class TotpServiceImpl implements TotpService {


    @Override
    public int buildTotp(String secret) {
        GoogleAuthenticatorConfig config = new GoogleAuthenticatorConfig.GoogleAuthenticatorConfigBuilder().build();
        GoogleAuthenticatorKey googleAuthenticatorKey =
                new GoogleAuthenticatorKey
                        .Builder(secret)
                        .setConfig(config)
                        .setVerificationCode(0)
                        .setScratchCodes(new ArrayList<Integer>())
                        .build();
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        return googleAuthenticator.getTotpPassword(googleAuthenticatorKey.getKey());
    }
}
