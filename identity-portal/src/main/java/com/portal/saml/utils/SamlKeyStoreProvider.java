package com.portal.saml.utils;

import com.portal.domain.ApplicationInfoDomain;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.KeyStoreCredentialResolver;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import static org.springframework.util.StringUtils.hasText;

/**
 * 用于解析证书与私钥的工具类
 */
public class SamlKeyStoreProvider {
    /**
     * 名字可以随意,作为证书的别名
     */
    private static String name = "certificate";

    private static final String privateKey="MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCZVCQPEBnAP/BC6y1GDSIy5B9jT79G/Yy5GNFEZA/tV6lP4dtVrgBUjsEggPjOVIG41BrZHTKOdaMr7sLC5F2MqQLOeOtn5Ws41Att5kRtNpjPgnuk9N6pxsrN+UvEDdUvOxz+UE/8VGBxp0eHdW7E3igZVKLNQFDgtbkp/nc8zbiNcHqPFHS06zsAsFsO5yi0nEvwAu1OZebqLHyM4xiN/qmoF4ZIwKx0gP+XTBsyyIL73LiH/+keKW6MKaLaMrE2tr27fR0UCbCYAw4rAjCphChgNksWZiPRJufPfxARgZChXhj/raWjEdlw+1XScxHL5Ad42vOkWZfLLlzodP49AgMBAAECggEBAI+U/otofWTNuzU/O888n0je3RHH7QwShYQT3GvZ9BxDGdcnXpjWb5K/d9TzUYsOEcg+P8IPGHfVa27JkHKnHX+KCYnGLFPHv+egke9hUbSgCutPem/Xqud7tBC8Iho/BeLLzXPNS4m0jfgAOPh9nLQYgZ5u5mGkl9hF9FaB57D0TXnIAIRj7crv6iDOvcrPAagaCQmn7g8Rw0M/k/kmb1Ohw19MKSfRHXKixrrlBqsMSrDiQLIO3KVlkIqFwxieE7lQYTxbCeIzLoCrK0Gyiej+mGuIzM0aPDlAcL1Pis/hp2prvYNog/qWeWbAduBUy2HfF6vf7aErlEIwtSMlyiECgYEA9AC8S9AGjW4s9EZ1v/nbXTv0q0W+YZ3S+xASUQgl4pDNQ7f9oTdIjBX0XK+iiuSqAMLTD7dRQaQNK2Rg9xdOsXFddUqe1XH+LqNs1ogjxVi4jbdtn6fq8eX3AvqMnFnNaWQ5RD7S2oO6gKkM9BuR8Kgy+tBeq4PrHSzDZALAMEUCgYEAoN4WzftZbdyDP0lz6/zJTTEIstMEj/+uHw0TqWk5IaQSWL1O/XMrZLFkNuSxgOvAIxpNrvQ/wHEDBZCmfySXXrWPTLe2ny0Xrk5MF70KbbhicmJmVTOpLbl2OQ+PzaAIlZiGZ0aOAF14udsQ03sBVV7MM+K7Uj74GkL2DEP2YZkCgYEAhF5VyAb9VwJbDtWekVvZfjXFYJjAVDKLPzoTxbPW4ZdOU5tf03KrP5u1aga59gFgnHMUjuUTBroZjt2woLsbLLXkTnoBuqdRjjmIFMXcKfYcgHzu0nsEwN/RZeNIHpW1EqSVUImIV5S4u416An9mZ5jHtzsM8JzrEnOzN/EqE8UCgYAYPT6sbOotvqNcHuJDtcRTSEcqQteWJw2CikLjLQK5yfIlwuirEfaA5qXWC6GfbmZ2I5l++ji884pkBUbBM69HSw47nhb7Zx0BW7TlgsuWeskCuUb4IZ+lTyO8N1cQwcNpXyEpYJ02+t6dCqA/bXJm/bDBRLG7E9fohRxz/q2aSQKBgQCsJ5BaVM/+8xIrUqSLcg/Y5ewPP+uAychUo2jTWjqg6rY37sQgrl7zcqwkM5kkzkE+R6r1724P9iB54nvzck06wxF/Jdap/oc2PStHbe735pioeSnSAW+pB1JMWDSOHL6eN2dSEjmnttwM50s9X8aL2XXpXEjh1je83EoNY5PGLA==";

    private static final String pubKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmVQkDxAZwD/wQustRg0iMuQfY0+/Rv2MuRjRRGQP7VepT+HbVa4AVI7BIID4zlSBuNQa2R0yjnWjK+7CwuRdjKkCznjrZ+VrONQLbeZEbTaYz4J7pPTeqcbKzflLxA3VLzsc/lBP/FRgcadHh3VuxN4oGVSizUBQ4LW5Kf53PM24jXB6jxR0tOs7ALBbDucotJxL8ALtTmXm6ix8jOMYjf6pqBeGSMCsdID/l0wbMsiC+9y4h//pHilujCmi2jKxNra9u30dFAmwmAMOKwIwqYQoYDZLFmYj0Sbnz38QEYGQoV4Y/62loxHZcPtV0nMRy+QHeNrzpFmXyy5c6HT+PQIDAQAB";
    private static char[] DEFAULT_KS_PASSWD = UUID.randomUUID().toString().toCharArray();

    public static PrivateKey generatePrivateKey(String priKey) throws Exception {

        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(priKey));
        KeyFactory keyf = KeyFactory.getInstance("RSA");
       PrivateKey privateKey1 = keyf.generatePrivate(priPKCS8);

       return privateKey1;
    }
    private static PublicKey getPubKey(String pubKey) {
        PublicKey publicKey = null;
        try {

            // 自己的公钥(测试)
             java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(pubKey));
            // RSA对称加密算法
            KeyFactory keyFactory;
            keyFactory = KeyFactory.getInstance("RSA");
            // 取公钥匙对象
            publicKey = keyFactory.generatePublic(bobPubKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static X509CertificateHolder generateCertificate(PrivateKey privateKey,PublicKey publicKey, String issuer, int expireInDays) throws GeneralSecurityException, IOException {
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 86400000L * (long)expireInDays);
        X500Name issueDn = new X500Name(issuer);
        X500Name subjectDn = new X500Name(issuer);
        BigInteger serail = BigInteger.probablePrime(32, new Random());
        try {
            SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance((new ASN1InputStream(publicKey.getEncoded())).readObject());
            X509v3CertificateBuilder builder = new X509v3CertificateBuilder(issueDn, serail, notBefore, notAfter, subjectDn, subjectPublicKeyInfo);
            ContentSigner sigGen = (new JcaContentSignerBuilder("SHA256withRSA")).build(privateKey);
            X509CertificateHolder holder = builder.build(sigGen);
            return holder;
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static KeyStore getKeyStore(ApplicationInfoDomain applicationInfoDomain) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(null, DEFAULT_KS_PASSWD);
            X509CertificateHolder x509CertificateHolder=  generateCertificate( generatePrivateKey(applicationInfoDomain.getPrivateKey()),getPubKey(applicationInfoDomain.getPublicKey()), "CN=scriptx, OU=wps, O=wps, L=BJ, ST=BJ, C=CN", 365);
            X509Certificate x509Certificate=  new JcaX509CertificateConverter().setProvider( "BC" )
                    .getCertificate( x509CertificateHolder);
            ks.setCertificateEntry(name, x509Certificate);
            if (hasText(applicationInfoDomain.getPrivateKey())) {
                PrivateKey    pkey = generatePrivateKey(applicationInfoDomain.getPrivateKey());

                ks.setKeyEntry(name, pkey, "".toCharArray(), new Certificate[]{x509Certificate});
            }

            return ks;
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyStoreCredentialResolver getCredentialsResolver(ApplicationInfoDomain applicationInfoDomain) {
        KeyStore ks = getKeyStore(applicationInfoDomain);
        Map<String, String> passwords = hasText(applicationInfoDomain.getPrivateKey()) ?
                Collections.singletonMap(name, "") :
                Collections.emptyMap();
        KeyStoreCredentialResolver resolver = new KeyStoreCredentialResolver(
                ks,
                passwords
        );
        return resolver;
    }

    public static Credential getCredential(ApplicationInfoDomain applicationInfoDomain) {
        try {
            KeyStoreCredentialResolver resolver = getCredentialsResolver(applicationInfoDomain);
            CriteriaSet cs = new CriteriaSet();
            EntityIdCriterion criteria = new EntityIdCriterion(name);
            cs.add(criteria);
            return resolver.resolveSingle(cs);
        } catch (ResolverException e) {
            throw new RuntimeException("Can't obtain SP private key", e);
        }
    }


    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        KeyPair pair = keyGen.genKeyPair();
        return pair;

    }

    public static void main(String... args) throws Exception {

//            KeyPair keyPair = generateKeyPair(2048);
//            System.out.println(EncodingUtils.encode(keyPair.getPrivate().getEncoded()));
//            System.out.println();
//            System.out.println(EncodingUtils.encode(keyPair.getPublic().getEncoded()));
        PublicKey publicKey = getPubKey(pubKey);
        PrivateKey prikey=generatePrivateKey(privateKey);
    }

}