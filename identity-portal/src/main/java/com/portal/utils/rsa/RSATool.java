package com.portal.utils.rsa;


import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSATool {

	public static final String PUB_KEY = "30819f300d06092a864886f70d010101050003818d003081890281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d3750203010001";

	public static final String PRI_KEY = "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d37502030100010281807fc4763a4c2bb7a3746c48944d248c0682b9802d23c1d30e099c56eeaa7404f6332a57008c0e84d72d1fb7a9cec104734b8723f57197030f9fddb69324553862ce5caae1816ae9193c6b85db409b64e03533bed982a45cbd3d663d6efc2bd4bba6079e053c120eb8f885fee600d62980811f77104b3fd0f1e65b012077920905024100c5ceccb8b1a26f3afe725b8f72480054b1d3645fd3c1d02dfe553cb24c85feda560ebd2b9a74f69240e1414e803bb061f33fbcda14d96e01bc279885aba6b2ff024100c4b5502f2856ef3f71f7b4b832f90cb16ca15910c51a78b5d5d6d670928c37b77c907661302b097d65cb831c5a27a01a49b9026a702d235b1b25515f3dc75d8b024077f46be3c91d4734d05f0b35574960a1fbe0c4eaf3e081dd9271a04843edb25a377be2ae1191c28178c4e134b8c0f9babca7b75fcceafa32252721f055e2d683024036dc729e945b9aa25140ca904cf714c92f08d8f44a6495a1f11fd7f2d18c75c5a0e211820d9313347bfd847595d75359c191edd08db4a915b1be78b15d638afd024067b5286b67b328a344d4ca229478a5cf14aac848aca7ea62c582a9d8da15e2a15b8f971e8b36a613b6051fc80b7123bac0f0e45ad14e3c7f956af39e93bc9749";

	private static final char[] bcdLookup = {'0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	private static final BASE64Encoder base64 = new BASE64Encoder();


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
	 * 生成公私钥秘钥对
	 */
	public static void genRSAKeyPair() {
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

			String pub = bytesToHexStr(rsaPublic.getEncoded());
			String pri = bytesToHexStr(rsaPrivate.getEncoded());
			System.out.println("pubKey:" + pub);
			System.out.println("priKey:" + pri);
			System.out.println("1024-bit RSA key GENERATED.");
		} catch (Exception e) {
			System.out.println("Exception genRSAKeyPair:" + e);
		}
	}

	public static String generateSHA1withRSASigature(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac.generatePrivate(keySpec);

			Signature sigEng = Signature.getInstance("SHA1withRSA");
			sigEng.initSign(privateKey);
			sigEng.update(src.getBytes());
			byte[] signature = sigEng.sign();
			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	private static String encryptWithPriKey(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key privateKey = fac.generatePrivate(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			byte[] bytes = src.getBytes();
			byte[] encodedByteArray = new byte[]{};
			for (int i = 0; i < bytes.length; i += 102) {
				byte[] subarray = org.apache.commons.lang3.ArrayUtils.subarray(bytes, i, i + 102);
				byte[] doFinal = cipher.doFinal(subarray);
				encodedByteArray = org.apache.commons.lang3.ArrayUtils.addAll(encodedByteArray, doFinal);
			}
			return new String(encodedByteArray);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param sign      签名后数据
	 * @param src       待签名数据
	 * @param pubKeyStr 公钥
	 * @return
	 */
	public static boolean verifySHA1withRSASigature(String sign, String src,
													String pubKeyStr) {
		try {

			Signature sigEng = Signature.getInstance("SHA1withRSA");

			byte[] pubbyte = hexStrToBytes(pubKeyStr.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPublicKey pubKey = (RSAPublicKey) fac.generatePublic(keySpec);

			sigEng.initVerify(pubKey);
			sigEng.update(src.getBytes());

			byte[] sign1 = hexStrToBytes(sign);
			return sigEng.verify(sign1);

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	public static String encryptLongTextWithPriKey(String src, String priKey) {
		final int ENCRYPT_LENGTH = 117;
		if (src.length() <= ENCRYPT_LENGTH) {
			return encryptWithPriKey(src, priKey);
		}

		StringBuffer sb = new StringBuffer();
		int idx = 0;
		while (idx < src.length()) {
			int end = idx + ENCRYPT_LENGTH > src.length() ? src.length() : idx + ENCRYPT_LENGTH;
			String sub = src.substring(idx, end);
			String encSub = encryptWithPriKey(sub, priKey);
			sb.append(encSub);
			idx += ENCRYPT_LENGTH;
		}

		return sb.toString();
	}

	public static String encryptWithPriKeyWithBase64(String src, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key privateKey = fac.generatePrivate(keySpec);
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] signature = cipher.doFinal(src.getBytes());

			return base64.encode(signature).replaceAll("[^a-zA-Z0-9+/=]", "");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encryptWithPubKey(String src, String pubKey) {
		try {
			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);

			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key publicKey = fac.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] signature = cipher.doFinal(src.getBytes());

			return bytesToHexStr(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptWithPriKey(String enc, String priKey) {
		try {
			byte[] pribyte = hexStrToBytes(priKey.trim());

			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pribyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			RSAPrivateKey privateKey = (RSAPrivateKey) fac
					.generatePrivate(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			byte[] forumcookie = hexStrToBytes(enc);

			byte[] plainText = cipher.doFinal(forumcookie);

			return new String(plainText);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptWithPubKey(String enc, String pubKey) {
		try {
			byte[] pubbyte = hexStrToBytes(pubKey.trim());

			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubbyte);
			KeyFactory fac = KeyFactory.getInstance("RSA");
			Key publicKey = fac.generatePublic(keySpec);

			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			byte[] forumcookie = hexStrToBytes(enc);

			byte[] plainText = cipher.doFinal(forumcookie);

			return new String(plainText);

		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
			return null;
		}
	}


	private static final String pub = "30819f300d06092a864886f70d010101050003818d003081890281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d3750203010001";

	private static final String pri = "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d37502030100010281807fc4763a4c2bb7a3746c48944d248c0682b9802d23c1d30e099c56eeaa7404f6332a57008c0e84d72d1fb7a9cec104734b8723f57197030f9fddb69324553862ce5caae1816ae9193c6b85db409b64e03533bed982a45cbd3d663d6efc2bd4bba6079e053c120eb8f885fee600d62980811f77104b3fd0f1e65b012077920905024100c5ceccb8b1a26f3afe725b8f72480054b1d3645fd3c1d02dfe553cb24c85feda560ebd2b9a74f69240e1414e803bb061f33fbcda14d96e01bc279885aba6b2ff024100c4b5502f2856ef3f71f7b4b832f90cb16ca15910c51a78b5d5d6d670928c37b77c907661302b097d65cb831c5a27a01a49b9026a702d235b1b25515f3dc75d8b024077f46be3c91d4734d05f0b35574960a1fbe0c4eaf3e081dd9271a04843edb25a377be2ae1191c28178c4e134b8c0f9babca7b75fcceafa32252721f055e2d683024036dc729e945b9aa25140ca904cf714c92f08d8f44a6495a1f11fd7f2d18c75c5a0e211820d9313347bfd847595d75359c191edd08db4a915b1be78b15d638afd024067b5286b67b328a344d4ca229478a5cf14aac848aca7ea62c582a9d8da15e2a15b8f971e8b36a613b6051fc80b7123bac0f0e45ad14e3c7f956af39e93bc9749";

	private static final String data = "to encrept";

	public static void main(String[] args) {

		//私钥签名，公钥验签
		String s = generateSHA1withRSASigature("tengxun", pri);
		System.err.println(s);
		boolean check = verifySHA1withRSASigature(s, "tengxun", pub);
		System.err.println(check);

		//公钥加密，私钥解密
		/*String pubs = encryptWithPubKey("tengxung",pri);
		System.err.println(pubs);
		pubs = decryptWithPriKey(pubs, pri);
		System.err.println(pubs);*/

	}
}
