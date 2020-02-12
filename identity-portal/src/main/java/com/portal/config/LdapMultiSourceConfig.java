package com.portal.config;



import com.portal.domain.AdInfoDomain;

import com.unboundid.ldap.sdk.LDAPConnection;

import com.unboundid.ldap.sdk.migrate.ldapjdk.LDAPException;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


import javax.net.ssl.SSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ldap多数据源配置
 *
 * @Author: zt
 * @Date: 2018/11/28 14:09
 */
public class LdapMultiSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapMultiSourceConfig.class);

    /**
     * ldap连接池
     */
    private static volatile Map<String, LdapTemplate> ldapConnectionPool = new ConcurrentHashMap<>();

    /**
     * ldaps连接池
     */
    private static volatile Map<String, LDAPConnection> ldapsConnectionPool = new ConcurrentHashMap<>();

    private static LdapContextSource getContextSource(String ip, int port, String userName, String pwd) {

        if (port == 389) {
            port = 3268;
        }
        if (port == 636) {
            port = 3269;
        }

        String ldapUrl = "ldap://" + ip + ":" + port;
        //从用户名中切分出来，dc=cipherchina,dc=com.dc=cn
        String s = userName.substring(userName.lastIndexOf("@") + 1, userName.length());
        String[] dcs = s.split("\\.");
        StringBuilder baseDc = new StringBuilder();
        for (int i = 0; i < dcs.length; i++) {
            if (i == dcs.length - 1) {
                baseDc.append("dc=").append(dcs[i]);
            } else {
                baseDc.append("dc=").append(dcs[i]).append(",");
            }
        }

        LOGGER.info("ldapUrl:" + ldapUrl);
        LOGGER.info("userName:" + userName);
        LOGGER.info("pwd:" + pwd);
        LOGGER.info("baseDc:" + baseDc);

        LdapContextSource contextSource = new LdapContextSource();
        Map<String, Object> config = new HashMap<>();

        contextSource.setUrl(ldapUrl);
        contextSource.setBase(baseDc.toString());
        contextSource.setUserDn(userName);
        contextSource.setPassword(pwd);
        contextSource.setReferral("follow");
        contextSource.setPooled(false);
        //  解决乱码的关键一句
        config.put("java.naming.ldap.attributes.binary", "objectGUID");
        contextSource.setBaseEnvironmentProperties(config);
        contextSource.afterPropertiesSet();
        return contextSource;
    }


    /**
     * 获取ldaps连接
     *
     * @param adInfoDomain
     * @return
     * @throws GeneralSecurityException
     * @throws LDAPException
     */
    public static LDAPConnection getLdapsConnection(AdInfoDomain adInfoDomain) throws GeneralSecurityException, LDAPException {

        //该用户不是AD用户，或者AD配置缺失
        if (adInfoDomain == null) {
            return null;
        }

        LDAPConnection ldapConnection = null;

        ldapConnection = ldapsConnectionPool.get(adInfoDomain.getIp());

        if (ldapConnection == null || !ldapConnection.isConnected()) {
            LOGGER.info("Get new ldaps connection for :" + adInfoDomain.getIp());
            SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
            SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
            try {
                ldapConnection = new LDAPConnection(sslSocketFactory, adInfoDomain.getIp(), 636,
                        adInfoDomain.getUserName(), adInfoDomain.getPassword());
            } catch (com.unboundid.ldap.sdk.LDAPException e) {
                e.printStackTrace();
            }
            ldapsConnectionPool.put(adInfoDomain.getIp(), ldapConnection);
        }

        return ldapConnection;

    }


    public static LdapTemplate getLdapTemplate(AdInfoDomain adInfoDomain) {
        if (adInfoDomain == null) {
            return null;
        }
        LdapTemplate ldapTemplate = null;

        ldapTemplate = ldapConnectionPool.get(adInfoDomain.getIp());

        if (ldapTemplate == null) {
            LOGGER.info("Get new ldap connection for :" + adInfoDomain.getIp());
            ldapTemplate = new LdapTemplate(getContextSource(adInfoDomain.getIp(), adInfoDomain.getPort(), adInfoDomain.getUserName(), adInfoDomain.getPassword()));
            ldapConnectionPool.put(adInfoDomain.getIp(), ldapTemplate);
        }
        return ldapTemplate;
    }


}
