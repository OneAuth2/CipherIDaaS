package com.portal.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CsAppAESUtil {

    // 16进制字符
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static final String key = "cipher2019sftest";

    public static void main(String args[]) throws Exception {


      //  String sEncode = encode(s, key);
        String sEncode="67e234dade3960a1702b1151846f6532c262699707e571fa40f2cf059b55b648761ee81fd7018db9fbb33eedf40e68a3814adf38e963be57443141649f37cf181a975d8dad8165cac194fa8fec3d015b1b89f963ddbb5b60c6a4406295f02964b3a2b7ebb8bc86540927e471df825a7810d997cf6339ad0e6bddc87a17582cd37db9c43885bf59138b01c879cee7636abe3c9765a459aeadfe314853c398fc74fe7230c0455f8a036fd1118aca768096b7987d33c4ae847baa34f962c10534f4cc162988eb2eb27da98d9746cb71648e673994e1153367da04d7f213d02fca4f413357dab7361173516b806bb9115d20c106236af9f1205a7d6cd8df067ecc690b98c384d2218ba9394910317b713452e9ce80296cd208dfb435ce538f8968a3fa70dc94d135e5f54c7e2e5258a345323a53c2a8c4f3913d5e53b48e21fbef0d4ab8603fd823e715907529942119f0ed4bfe47e6f6a073a801ea66a0d2f732f7a589212a3c52cf2cf5666f48d4f5389f3f0a6d8ca68b25da54f8816f86137d5c5c0315f8492f52422b3f304d0644d5541cf2a61ec513a5482358aa7753967ba0fbdb76d9c58a215d8f182c58086a6a38a2e449c52d1b22916e68700041de4f3ca9d41fd044943ce0b804cad3f595555b49cf68035ed558deca8156c0b83ab0156f0d605f956b73d71e6cbf3f67a63b43a306d3548bb0ce7081cb2f212d32a61dda21c78e905a465ee1d90676660cae2f8aed9cdf4a80383e46c1f07aac89fe0cf6e7fa9885e11f842f26b9a6500d7bce0f8d8ada4ec9c38640b7928759a0094a7bf7f38f69f9682cb003ca1f3aaa98cfd91afebf3cfb49667627da7ce65b65d993d30d74e90022c873c10873f8a5c2b34792f6455b3aa71d07c7a1acda1fe73f863644566aaf4c643b1cd9bf5e4c18777dda08ea4af3c53eb109745adba8ca750f4c7e9f6120d48a715a08f7a5b7637f57528b3097c3d096ffa3c176c2cc001e58419ee81e3998e8c6bc5bf5dcac4243cc3b554ca883b63a253fa7b7674ad118dda0609215ba4822812558f5645009e81c0f7b810d354930e061d4acc7dd7c30492766cc94e050cf5c193d73c2495ea89b322b4fe01b136df39aa450ed0712b0f8fe981013b02383e344fa859c1d5019e9e83dde65dfaf5467360b61fd6cc65767f2c7cbad8dc2b957d3b2e06a7f88123120d98ff70b3c5913e03516e59635f3be7004b95c1cd79b01793711e7145d2dab4119b5ffb7384b4114b6b715d3a81aa04bd202dba61ac464c41c6a72cbb4cd4bf1dfef11492ceafdee2e5008b6e054";
        System.out.println(sEncode);
        System.out.println("==============================================");

        String sDecode = decode(sEncode, key);
        System.out.println(sDecode);
    }

    /**
     * 解密
     *
     * @param content   密文
     * @param key 加密密码
     * @return String
     * @throws Exception 异常
     */
    public static String decode(String content, String key) throws Exception {
        byte[] arr = string2ByteArr(content);
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] original = cipher.doFinal(arr);
        return new String(original,"utf-8");
    }

    /**
     * 加密
     *
     * @param content      原文
     * @param key 加密密码
     * @return String
     * @throws Exception 异常
     */
    public static String encode(String content, String key) throws Exception {
        byte[] raw = key.getBytes("GBK");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("GBK"));
        return toHexString3(encrypted);
    }

    /**
     * 字符数组转为字符串： byte[]-->hexString
     * @explain 使用位运算
     * @param bytes
     * @return
     */
    public static String toHexString3(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        // 利用位运算进行转换，可以看作方法二的变型
        for (byte b : bytes) {
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }

    /**
     * 将16进制字符串转换为字节数组
     * @param str 16进制字符串
     * @return byte[]
     */
    public static byte[] string2ByteArr(String str) {
        byte[] bytes;
        bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),16);
        }
        return bytes;
    }

}
