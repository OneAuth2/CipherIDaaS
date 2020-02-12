package com.portal.controller;


import com.portal.auth.request.GetAuthAccessTokenReq;
import com.portal.auth.request.GetCipherQrCodeReq;
import com.portal.auth.request.GetDingAuthAccessTokenReq;
import com.portal.auth.response.GetAuthAccessTokenResp;
import com.portal.auth.response.GetCipherQrCodeResp;
import com.portal.commons.AuthMode;
import com.portal.commons.CacheKey;
import com.portal.commons.Constants;
import com.portal.ctid.request.GetAccessTokenReq;
import com.portal.ctid.request.GetQrCodeReq;
import com.portal.ctid.response.GetQrCodeResp;
import com.portal.domain.DingScanParam;
import com.portal.domain.NullCacheObject;
import com.portal.domain.OtpDynamicInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.DingScanParamService;
import com.portal.service.OtpDynamicInfoService;
import com.portal.service.UserInfoService;
import com.portal.totp.GoogleAuthenticator;
import com.portal.totp.GoogleAuthenticatorKey;
import com.portal.utils.QRCodeUtil;
import com.portal.utils.QuickMarkUtil;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import edu.hziee.cap.common.StringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.portal.commons.AuthMode.DING_MODE;
import static com.portal.commons.AuthMode.WEIXIN_MODE;
import static com.portal.enums.ResultCode.PARAMETER_FAILURE;
import static com.portal.enums.ResultCode.PORTAL_SYSTEM_ERROR;

/**
 * @Author: TK
 * @Date: 2019/4/28 14:17
 */

@Controller
@RequestMapping(value = "/cipher/login")
public class UserLoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();

    @Autowired
    private DingScanParamService dingScanParamService;

    @Autowired
    private OtpDynamicInfoService otpDynamicInfoService;

    @Autowired
    private UserInfoService userInfoService;


    private   static  final  String appId="";


    private   static  final  String redirectUrl="";


    /**
     * 获取钉钉二维码
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @RequestMapping(value = "/dingQrcode")
    @ResponseBody
    public Map<String, Object> getDingQrcode(String companyUUid) {
        //入参校验
        if (companyUUid.isEmpty()) {

            logger.warn(" enter UserLoginController.getDingQrcode  Error As The companyUUid =[{}]", new Object[]{companyUUid});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        Map<String, Object> map = new HashMap<>();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            //获取后台配置的钉钉信息
            DingScanParam dingScanParam = dingScanParamService.getDingScanParamByCompanyUuid(companyUUid);

            //对象为空
            if (dingScanParam == null) {
                return sendBaseErrorMap(ResultCode.REGIST_ERROR);
            }

            //生成二维码的token
            String token = new GetDingAuthAccessTokenReq(dingScanParam.getClientId(), dingScanParam.getSecretKey()).request().getAccessToken();

            //获取二维码的具体信息
            GetQrCodeResp qrCodeResp = new GetQrCodeReq(token, "ScanAuth", DING_MODE).request();
            long times = (qrCodeResp.getTokenInfo().getExpireTimeMs() - System.currentTimeMillis()) / 1000;

            //返回获取二维码的具体信息
            map.put("qrcode", qrCodeResp.getQrcodeImage());
            map.put("uuid", qrCodeResp.getTokenInfo().getCertToken());
            map.put("times", times);

        } catch (Exception e) {

            //打印错误日志
            logger.error("Enter UserLoginController.getDingQrcode But failed companyUUid[{}]",
                    new Object[]{companyUUid});
            logger.error(e.getMessage(), e);

            return sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }

        return sendBaseNormalMap(map);

    }

    /**
     * 获取赛赋二维码
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @RequestMapping(value = "/saiFuQrcode")
    @ResponseBody
    public Map<String, Object> getSaiFuQrcode(String companyUUid) {
        //入参校验
        if (companyUUid.isEmpty()) {

            logger.warn(" enter UserLoginController.getSaiFuQrcode  Error As The companyUUid =[{}]", new Object[]{companyUUid});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        Map<String, Object> map = new HashMap<>();
        try {
            //获取二维码的token信息
            String token = getAccessToken();

            //获取二维码的具体信息
            GetCipherQrCodeResp getCipherQrCodeResp = new GetCipherQrCodeReq(token, "scanAuth", AuthMode.QR_CODE).request();
            int times = getCipherQrCodeResp.getExpireSeconds();

            //返回map的信息
            map.put("qrcode", getCipherQrCodeResp.getQrcodeImage());
            map.put("uuid", getCipherQrCodeResp.getTokenInfo().getCertToken());
            map.put("times", times);
        } catch (Exception e) {

            //打印错误日志

            logger.error("Enter UserLoginController.getDingDingQrcode But failed companyUUid[{}]",
                    new Object[]{companyUUid});
            logger.error(e.getMessage(), e);

            sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }


        return sendBaseNormalMap(map);

    }

    /**
     * 获取大白二维码
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @RequestMapping(value = "/daBaiQrcode")
    @ResponseBody
    public Map<String, Object> getDabaiQrcode(String companyUUid, HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "mode", required = false) Integer mode) {
        Map<String, Object> map = new HashMap<>();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //生成二维码
        String token = new GetAccessTokenReq().request().getAccessToken();
        if (mode == null) {
            mode = 66;
        }
        GetQrCodeResp qrCodeResp = new GetQrCodeReq(token, "ScanAuth", mode).request();
        long times = (qrCodeResp.getTokenInfo().getExpireTimeMs() - System.currentTimeMillis()) / 1000;
        String content = qrCodeResp.getTokenInfo().getQrcodeContent();

        try {
            BufferedImage qrCode = QRCodeUtil.createImage(content, null, true);
            ImageIO.write(qrCode, "JPG", out);
            map.put("qrcode", "data:image/png;base64," + Base64.encodeBase64String(out.toByteArray()));
            map.put("uuid", qrCodeResp.getTokenInfo().getCertToken());
            map.put("times", times);
            return sendBaseNormalMap(map);
        } catch (Exception e) {
            logger.error("Enter DabbyController.createQrCode() but failed, mode=[{}] ..==", mode);

            return sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }

    }




    /**
     * 获取OTP动态码的二维码
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @RequestMapping(value = "/otpQrcode")
    @ResponseBody
    public Map<String, Object> getOtpQrcode(@RequestParam(value = "companyUUid") String companyUUid ,@RequestParam(value = "userId") String userId) {
        //入参校验
        if (companyUUid.isEmpty()||userId.isEmpty()) {
            logger.warn(" enter UserLoginController.getOtpQrcode  Error As The companyUUid =[{}] userId=[{}]", new Object[]{companyUUid,userId});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        Map<String, Object> map = new HashMap<>();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            UserInfoDomain userInfoDomain=userInfoService.getUserInfoByUUid(userId);
            if(null==userInfoDomain){
                return sendBaseErrorMap(ResultCode.USER_IS_NOT_EXIST);
            }

            GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
            GoogleAuthenticatorKey credentials = googleAuthenticator.createCredentials();
            String encryptPassword = AES.encryptToBase64(credentials.getKey(), AesKey.AES_KEY);
            redisClient.put(CacheKey.getCacheKeyUserOtpSecrect(userId),encryptPassword);

            //生成otp二维码的具体信息
            String dynamicPassword="http://www.cipherchina.com:8000/a?user="+userInfoDomain.getAccountNumber()+"&secret="+credentials.getKey();
            BufferedImage qrCode = QuickMarkUtil.createImage(dynamicPassword, null, true);
            ImageIO.write(qrCode, "JPG", out);
            map.put("qrcode", "data:image/png;base64," + Base64.encodeBase64String(out.toByteArray()));
            map.put("times", Constants.OTPEXPRIE);

        } catch (Exception e) {

            //打印错误日志
            logger.error("Enter UserLoginController.getOtpQrcode But failed companyUUid[{}]",
                    new Object[]{companyUUid});
            logger.error(e.getMessage(), e);

            return sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }

        return sendBaseNormalMap(map);
    }










    /**
     * 获取赛赋认证的accessToken
     */
    public String getAccessToken() {
        Object obj = redisClient.get(CacheKey.getCacheKeyCipherAuthAccessToken());
        if (obj == null) {
            GetAuthAccessTokenResp resp = new GetAuthAccessTokenReq().request();
            obj = resp.getAccessToken();
            String time = resp.getExpireSeconds();
            if (obj == null) {
                obj = new NullCacheObject();
            }
            redisClient.put(CacheKey.getCacheKeyCipherAuthAccessToken(), obj, Long.valueOf(time));
        }

        if (obj == null || obj instanceof NullCacheObject) {
            return null;
        }

        return (String) obj;
    }
}
