package com.portal.utils;


import com.portal.auth.response.GetQrCodeCallBackResp;
import com.portal.model.DabbyUserInfoModel;
import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SignatureUtils {


    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureUtils.class);
    private static final Charset DefaultCharset = StandardCharsets.UTF_8;

    public enum SupportedSignatureAlgorithm {
        BLAKE2BwithNONE,
        BLAKE2BwithRSA,
        NONEwithRSA
    }


    private static byte[] sign(String algorithm, PrivateKey privk, byte [] bytes) throws Exception {
        Signature sig = Signature.getInstance(algorithm); sig.initSign(privk);
        sig.update(bytes);
        return sig.sign();
    }

    private static byte[] sign(SupportedSignatureAlgorithm alg, PrivateKey privk, byte[] bytes)throws Exception {

        String algn;

        switch (alg) {

            case BLAKE2BwithNONE:
                return hashBlake2b(bytes);
            case BLAKE2BwithRSA:
                bytes = hashBlake2b(bytes);
                algn = SupportedSignatureAlgorithm.NONEwithRSA.name(); break;
            default:
                algn = alg.name();
        }
        return sign(algn, privk, bytes);

    }

    private static byte[] hashBlake2b(byte[] bytes) {
        Blake2bDigest b2bd = new Blake2bDigest(512); b2bd.update(bytes, 0, bytes.length);
        byte[] ret = new byte[1024];
        int len = b2bd.doFinal(ret, 0);
        return Arrays.copyOfRange(ret, 0, len);
    }

    private static byte[] map2SortedMapBytes(Map<?, ?> map) {
        Map<?, ?> sm = new TreeMap<>(map);
        StringBuilder sb = new StringBuilder();
        boolean firstEntry = true;
        for (Map.Entry<?, ?> entry : sm.entrySet()) {
            if (!firstEntry) {
                sb.append("&");
            }else {
                firstEntry = false;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString().getBytes(DefaultCharset);
    }

    private static byte[] sign(SupportedSignatureAlgorithm alg, PrivateKey privk, Map<?, ?> map) throws Exception{
        return sign(alg, privk, map2SortedMapBytes(map));
    }

    public static String getSig(Map<?, ?> map) {

        try {
            byte[] sigByte = SignatureUtils.sign(SupportedSignatureAlgorithm.BLAKE2BwithNONE, null, map);
            return Base64.toBase64String(sigByte);
        }catch (Exception e) {
            LOGGER.error("black2b getsign exception:", e);
        }
        return null;
    }

    public static String getSig(String content) {
        try {
            byte[] sigByte = SignatureUtils.sign(SupportedSignatureAlgorithm.BLAKE2BwithNONE, null, content.getBytes(DefaultCharset));
            return Base64.toBase64String(sigByte);
        } catch (Exception e) {
            LOGGER.error("black2b getsign exception:", e);
        }
        return null;
    }

    public static boolean compareSign(Map<?, ?> map, String sigText) {
        return sigText.equals(getSig(map));
    }


    public static boolean compareSign(DabbyUserInfoModel userInfoModel, String signText){
        Map<String, Object> sigMap = new HashMap<>();
        sigMap.put("cert_token", userInfoModel.getCert_token());
        sigMap.put("full_name", userInfoModel.getFull_name());
        sigMap.put("id_num", userInfoModel.getId_num());
        sigMap.put("cert_res", userInfoModel.getCert_res());
        sigMap.put("cert_mode", userInfoModel.getCert_mode());
        sigMap.put("client_id", "695216d3ff65b20c");
        sigMap.put("client_secret", "e296af43-e8e1-4405-84cd-246e7efdd032");
        return compareSign(sigMap,signText);
    }


    public static boolean compareCipherSign(GetQrCodeCallBackResp getQrCodeCallBackResp, String signText){
        Map<String, Object> sigMap = new HashMap<>();
        sigMap.put("certToken", getQrCodeCallBackResp.getCertToken());
        sigMap.put("userName", getQrCodeCallBackResp.getUserName());
        sigMap.put("phoneNumber", getQrCodeCallBackResp.getPhoneNumber());
        sigMap.put("mail", getQrCodeCallBackResp.getMail());
        sigMap.put("certRes", getQrCodeCallBackResp.getCertRes());
        sigMap.put("certString", getQrCodeCallBackResp.getCertString());
        sigMap.put("sig", getQrCodeCallBackResp.getSig());
        return compareSign(sigMap,signText);
    }




    //测试用
    public static void main(String[] args) throws Throwable {
        Map<String, Object> sigMap = new HashMap<>();

        sigMap.put("cert_token", "69f615bb-54ca-4832-9501-59603e7b977d");
        sigMap.put("full_name", "董思招");
        sigMap.put("id_num", "330326199604226017");
        sigMap.put("cert_res", 4112);
        sigMap.put("cert_mode", 66);
        sigMap.put("client_id", "695216d3ff65b20c");
        sigMap.put("client_secret", "e296af43-e8e1-4405-84cd-246e7efdd032");
        String sigText = "gI/6cVUx7k889raFE9E+lc2HSft2W0Mxl9LN62DLrqvJvcqX4FpPWJXoWEbLV7FIwEJTTznFSqWSzJibbG/eKw==";
        System.err.println(compareSign(sigMap, sigText));//true 验签通过， false 验签失败
    }







}
