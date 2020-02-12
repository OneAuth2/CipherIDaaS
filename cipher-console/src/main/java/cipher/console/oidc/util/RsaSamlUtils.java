package cipher.console.oidc.util;

import cipher.console.oidc.entity.RSAPublicPrivateKeyEntity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/**
 * TODO:
 * create by shizhao at 2019-12-11
 *
 * @author: shizhao
 * @since: 2019-12-11 10:36
 */
public class RsaSamlUtils {
    //生成keyPair
    public static KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        KeyPair pair = keyGen.genKeyPair();
        return pair;
    }

    //返回公私钥字符串
    public static RSAPublicPrivateKeyEntity getPrivateKey(RSAPublicPrivateKeyEntity rsaPublicPrivateKeyEntity) throws NoSuchAlgorithmException {
        int length=2048;
        KeyPair keyPair= generateKeyPair(length);
        rsaPublicPrivateKeyEntity.setPrivateKey(Base64Utils.encode(keyPair.getPrivate().getEncoded()));
        rsaPublicPrivateKeyEntity.setPublicKey(Base64Utils.encode(keyPair.getPublic().getEncoded()));
        return rsaPublicPrivateKeyEntity;
    }
}
