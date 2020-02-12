package com.portal.utils.rsa;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAToolNew {

	public static final String PUB_KEY = "30820275020100300d06092a864886f70d01010105000482025f3082025b0201000281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d37502030100010281807fc4763a4c2bb7a3746c48944d248c0682b9802d23c1d30e099c56eeaa7404f6332a57008c0e84d72d1fb7a9cec104734b8723f57197030f9fddb69324553862ce5caae1816ae9193c6b85db409b64e03533bed982a45cbd3d663d6efc2bd4bba6079e053c120eb8f885fee600d62980811f77104b3fd0f1e65b012077920905024100c5ceccb8b1a26f3afe725b8f72480054b1d3645fd3c1d02dfe553cb24c85feda560ebd2b9a74f69240e1414e803bb061f33fbcda14d96e01bc279885aba6b2ff024100c4b5502f2856ef3f71f7b4b832f90cb16ca15910c51a78b5d5d6d670928c37b77c907661302b097d65cb831c5a27a01a49b9026a702d235b1b25515f3dc75d8b024077f46be3c91d4734d05f0b35574960a1fbe0c4eaf3e081dd9271a04843edb25a377be2ae1191c28178c4e134b8c0f9babca7b75fcceafa32252721f055e2d683024036dc729e945b9aa25140ca904cf714c92f08d8f44a6495a1f11fd7f2d18c75c5a0e211820d9313347bfd847595d75359c191edd08db4a915b1be78b15d638afd024067b5286b67b328a344d4ca229478a5cf14aac848aca7ea62c582a9d8da15e2a15b8f971e8b36a613b6051fc80b7123bac0f0e45ad14e3c7f956af39e93bc9749";

	public static final String PRI_KEY = "30819f300d06092a864886f70d010101050003818d003081890281810097fe6de936b2bebfe7fbdc12be7f4868581139b658d573d7426b55eba0152fdbe75fc954469276bc2dd018353a1f4045aa636997df026a2ca1fa20e0370ec1d398afc0af58d9631b735276a75ab6549b5cc8ba3fa63f5b84718327f486c41ced042639aee19ae5a3a533a02ad172bac6a5843a629021d4f32b11a2cca9c2d3750203010001";

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



	private static final String data = "403d66d4828b11e9b5af00163e00cc6a";


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

	public static void main(String[] args) throws UnsupportedEncodingException {
		Long time=System.currentTimeMillis();
		System.err.println(time);
		//公钥签名，私钥验签
		String s = generateSHA1withRSASigature(data + time, PUB_KEY);
		URLEncoder.encode(s,"UTF-8");
		System.err.println(URLEncoder.encode(s,"UTF-8"));
	}
}
