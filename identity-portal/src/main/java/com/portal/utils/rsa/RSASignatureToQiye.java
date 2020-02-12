package com.portal.utils.rsa;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 产生用户公钥私钥以及签名方法
 * 
 * 
 */
public class RSASignatureToQiye {

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static final String ENCODING_UTF_8="UTF-8";
	
	private static String pri = null;
	private static String pub = null;

	public static void main(String... args){
		createKeyPair();
	}


	/**
	 * 产生私钥公钥对
	 */
	public static void createKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try {
			System.out.println("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.setSeed(System.currentTimeMillis());

			rsaKeyGen.initialize(1024, random);

			rsaKeyPair = rsaKeyGen.genKeyPair();
			PublicKey rsaPublic = rsaKeyPair.getPublic();
			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();

			pub = bytesToHexStr(rsaPublic.getEncoded());
			pri = bytesToHexStr(rsaPrivate.getEncoded());
			System.out.println("pubKey:" + pub);
			System.out.println("priKey:" + pri);
			System.out.println("1024-bit RSA key GENERATED.");
		} catch (Exception e) {
			System.out.println("Exception genRSAKeyPair:" + e);
		}
	}

	private static String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	private static byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	/**
	 * 签名方法
	 * 
	 * @param priKey 用户的私钥
	 * @param src 要进行签名的字符串
	 * @return 签名
	 */
	public static String generateSigature(String priKey, String src) {
		try {
			Signature sigEng = Signature.getInstance("SHA1withRSA");
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);

			KeyFactory fac = KeyFactory.getInstance("RSA");

			RSAPrivateKey privateKey = (RSAPrivateKey) fac
					.generatePrivate(keySpec);
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes(ENCODING_UTF_8));

			byte[] signature = sigEng.sign();
			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}