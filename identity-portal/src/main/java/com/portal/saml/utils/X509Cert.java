package com.portal.saml.utils;

import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * TODO:
 * create by shizhao at 2019-11-26
 *
 * @author: shizhao
 * @since: 2019-11-26 10:38
 */
public class X509Cert {
    public static void genX509(String password,
                               int keysize, String algorithm) throws Exception {
        // 创建KeyStore
        KeyStore store = KeyStore.getInstance("PKCS12");
        store.load(null, null);
        // 生成一对公私钥，这部分如果自己已经有了PublicKey，可以直接在下面使用，不用生成
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(keysize);
        KeyPair keyPair = kpg.generateKeyPair();
        System.out.println(keyPair.getPublic().toString());
        System.out.println(keyPair.getPrivate().toString());
        // 这个字符串根据自己情况填
        String issuer = "C=CN,ST=BJ,L=BJ,O=cipherchina,OU=cipherchina,CN=cipherchina";
        String subject = issuer;
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new X500Principal(issuer));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 10 * 365
                * 24 * 60 * 60 * 1000));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 10 * 365 * 24
                * 60 * 60 * 1000));
        certGen.setSubjectDN(new X500Principal(subject));
        certGen.setPublicKey(keyPair.getPublic());// 此处可直接传入线程的PublicKey
        if (algorithm == null || algorithm.equals("")) {
            certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
        } else {
            certGen.setSignatureAlgorithm(algorithm);
        }
        X509Certificate cert = certGen.generateX509Certificate(keyPair
                .getPrivate());
        // 私钥有现成的也可直接传入
        store.setKeyEntry("alias", keyPair.getPrivate(),
                password.toCharArray(), new Certificate[] { cert });
        // 导出为 cer 证书 和 keystore文件
        try {
            FileOutputStream fos = new FileOutputStream("D://saml/public" + ".cer");
            FileOutputStream ksFOS = new FileOutputStream("D://saml/private" + ".keystore");
            fos.write(cert.getEncoded());
            fos.close();
            store.store(ksFOS, "123".toCharArray());
            ksFOS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void main(String[] args) throws Exception {
        genX509("123", 1024, "");

    }
}
