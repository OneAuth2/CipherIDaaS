package com.portal.saml.utils;

import com.portal.domain.ApplicationInfoDomain;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
