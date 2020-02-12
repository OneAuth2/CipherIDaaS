package cipher.console.oidc.controller.testController;

import cipher.console.oidc.common.subapp.SecretManager;
import cipher.console.oidc.domain.subapp.TencentCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.TencentDepDomain;
import cipher.console.oidc.service.subapp.TencentEmailService;

import cipher.console.oidc.util.Base64Utils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

import java.security.*;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import static javax.crypto.Cipher.PRIVATE_KEY;


/**
 * @Author: zt
 * @Date: 2018/11/29 9:59
 */

@Controller
@RequestMapping(value = "/tengxun")
public class Test2Controller {

    @Autowired
    private TencentEmailService tencentEmailService;

    @RequestMapping(value = "/token")
    @ResponseBody
    public String getToken() {
        return tencentEmailService.getAccessToken(SecretManager.TENCENT_ID, SecretManager.TENCENT_ID);
    }

    @RequestMapping(value = "/create")
    @ResponseBody
    public Map<String, Object> createSbAccount(HttpServletRequest request, HttpServletResponse response) {
        TencentCreateSubReqDomain tencent = new TencentCreateSubReqDomain();
        tencent.setUserid("zhoutao@cipherchina.com");
        tencent.setName("周涛");
        List<Long> depList = new ArrayList<>();
        depList.add(6769111398567385885L);
        tencent.setDepartment(depList);
        tencent.setPassword("Zt123456");
        String accessToken = tencentEmailService.getAccessToken(SecretManager.TENCENT_ID, SecretManager.TENCENT_DEPT_MNG);
        return tencentEmailService.createSubAccount(accessToken, tencent);
    }

    @RequestMapping(value = "/dep")
    @ResponseBody
    public TencentDepDomain queryTencentDep(HttpServletRequest request) {
        String accessToken = tencentEmailService.getAccessToken(SecretManager.TENCENT_ID, SecretManager.TENCENT_DEPT_MNG);
        return tencentEmailService.qeryDept(accessToken, null);
    }
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;//设置长度
    private static final String PUBLIC_KEY_NAME = "publicKey";
    private static final String PRIVATE_KEY_NAME = "privateKey";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(keySize);
            KeyPair pair = keyGen.genKeyPair();
            return pair;

    }

    public PrivateKey generatePrivateKey(String privateKey) throws Exception{

        privateKey = privateKey.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        privateKey = privateKey.replace("-----END RSA PRIVATE KEY-----", "");
        privateKey = privateKey.replaceAll("\\s+", "");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getBytes());
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public static X509CertificateHolder generateCertificate(KeyPair keyPair, String issuer, int expireInDays) throws GeneralSecurityException, IOException {
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 86400000L * (long)expireInDays);
        X500Name issueDn = new X500Name(issuer);
        X500Name subjectDn = new X500Name(issuer);
        BigInteger serail = BigInteger.probablePrime(32, new Random());
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

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




    public static void main(String... args) throws KeyStoreException {
        try {
            KeyPair keyPair=generateKeyPair(2048);


            X509CertificateHolder x509CertificateHolder=  generateCertificate( keyPair, "CN=scriptx, OU=wps, O=wps, L=BJ, ST=BJ, C=CN", 365);
           // System.out.println(Base64Utils.encode(keyPair.getPrivate().getEncoded()));
            X509Certificate x509Certificate=  new JcaX509CertificateConverter().setProvider( "BC" )
                    .getCertificate( x509CertificateHolder);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
