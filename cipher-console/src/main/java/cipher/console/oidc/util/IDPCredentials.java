package cipher.console.oidc.util;

import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import org.apache.commons.io.IOUtils;
import org.apache.http.util.EncodingUtils;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * 本DEMO 作为IDP 接入时所使用的用来保存 凭据 的类
 */
public class IDPCredentials {
    private static Logger logger = LoggerFactory.getLogger(IDPCredentials.class);
    private static Credential credential;

    private static Credential resolveCredential(ApplicationInfoDomain applicationInfoDomain) {
            return SamlKeyStoreProvider.getCredential(applicationInfoDomain);
    }

    public static Credential getCredential(ApplicationInfoDomain applicationInfoDomain) {
        if(credential == null){
            credential = resolveCredential(applicationInfoDomain);
        }
        return credential;
    }
    public static Credential readCredential(ApplicationInfoDomain applicationInfoDomain) throws IOException {
        return SamlKeyStoreProvider.getCredential(applicationInfoDomain);
    }

}
