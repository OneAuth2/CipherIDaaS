package com.portal.service.impl;

import com.cipher.china.channel.AuthResult;
import com.cipher.china.channel.channels.*;
import com.cipher.china.channel.factory.AuthMachine;
import com.cipher.china.channel.pojo.AuthInfo;
import com.cipher.china.channel.pojo.AuthStrategy;
import com.cipher.china.channel.pojo.DingTalkScanAuthUser;
import com.cipher.china.channel.pojo.WeixinScanAuthUser;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.google.gson.Gson;
import com.portal.auth.response.GetWeixinTokenResp;
import com.portal.commons.AuthMode;
import com.portal.commons.CacheKey;
import com.portal.commons.LdapConstants;
import com.portal.daoAuthoriza.*;
import com.portal.domain.AdInfoDomain;
import com.portal.domain.DingTalkConfig;

import com.portal.domain.ErpInfoDomain;
import com.portal.domain.UserInfoDomain;
import com.portal.domain.*;
import com.portal.jms.JMSProducer;
import com.portal.jms.JMSType;
import com.portal.redis.RedisClient;
import com.portal.service.CipherUserInfoService;
import com.portal.service.LdapService;
import com.portal.service.LoginFailedUserService;
import com.portal.service.PortalAuthChannelService;
import com.portal.service.*;
import com.portal.totp.GoogleAuthenticator;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import com.portal.utils.httpclient.HttpClientUtils;
import com.portal.utils.httpclient.URLBuilder;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static com.cipher.china.channel.enums.AuthChannelEnum.*;
import static com.cipher.china.channel.enums.AuthResultCode.*;


@Service
public class PortalAuthChannelServiceImpl implements PortalAuthChannelService {

    private static final Logger logger = LoggerFactory.getLogger(PortalAuthChannelServiceImpl.class);


    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();


    @Autowired
    private AdInfoDAO adInfoDAO;

    @Autowired
    private GoldMantisErpDAO erpDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DingTalkConfigDAO dingTalkConfigDAO;

    @Autowired
    private LdapService ldapService;

    @Autowired
    private CipherUserInfoService cipherUserInfoService;

    @Autowired
    private LoginFailedUserService loginFailedUserService;

    @Autowired
    private SystemConfigInfoDAO systemConfigInfoDAO;




    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private GetResultWithSyncService getResultWithSyncService;

    @Autowired
    private UserLoginRecDAO userLoginRecDAO;

    @Autowired
    private OtpDynamicInfoService otpDynamicInfoService;


    @Autowired
    private WxInfoService wxInfoService;


    private class BayMaxListener implements BayMaxScanAuthChannel.BayMaxScanAuthChannelListener {

        @Override
        public String getBayMaxScanInfo(String uuid, String companyId) {
            Object object = redisClient.get(CacheKey.getUserLoginUuid(uuid));
            if (object == null) {
                return null;
            }
            return new Gson().toJson(object);
        }

        @Override
        public boolean isBayMaxAuthUserAvailable(String result, String companyId) {
            //TODO:暂不查询是否已经在公司登记
            return true;
        }
    }

    private class CipherScanLister implements CipherScanAuthChannel.CipherScanAuthChannelListener {
        @Override
        public String getCipherScanInfo(String uuid, String companyId) {
            Object object = redisClient.get(CacheKey.getCacheKeyCipherAuthQrcodeUuid(uuid));
            if (object == null) {
                return null;
            }
            return new Gson().toJson(object);
        }

        @Override
        public boolean isCipherAuthUserAvailable(String result, String companyId) {
            return true;
        }
    }

    private class DingTalkListener implements DingTalkScanAuthChannel.DingTalkScanAuthChannelListener {
        @Override
        public String getDingTalkScanInfo(String code, String companyId) {

            OapiSnsGetuserinfoBycodeResponse codeResponse = getUserInfoByCode(code, companyId);
            if (codeResponse == null) {
                return null;
            }

            OapiSnsGetuserinfoBycodeResponse.UserInfo userInfo = codeResponse.getUserInfo();
            if (userInfo == null) {
                return null;
            }


            if (StringUtils.isEmpty(userInfo.getUnionid())) {
                return null;
            }

            String token = getToken(companyId);

            if (StringUtils.isEmpty(token)) {
                return null;
            }


            String userId = getUserId(companyId, userInfo.getUnionid(), token);

            if (StringUtils.isEmpty(userId)) {
                return null;
            }

            OapiUserGetResponse response = getUserInfoByUserId(userId, token, companyId);

            if (response == null) {
                return null;
            }

            DingTalkScanAuthUser dingTalkScanAuthUser = new DingTalkScanAuthUser();
            dingTalkScanAuthUser.setErrcode(codeResponse.getErrcode());
            dingTalkScanAuthUser.setErrmsg(codeResponse.getErrmsg());
            DingTalkScanAuthUser.UserInfo dingUserInfo = new DingTalkScanAuthUser.UserInfo();
            dingUserInfo.setNick(userInfo.getNick());
            dingUserInfo.setUnionId(userInfo.getUnionid());
            dingUserInfo.setOpenId(userInfo.getOpenid());
            dingUserInfo.setMail(response.getEmail());
            dingUserInfo.setPhone(response.getMobile());
            dingUserInfo.setUserId(userId);
            dingTalkScanAuthUser.setUserInfo(dingUserInfo);

            return new Gson().toJson(dingTalkScanAuthUser);
        }

        @Override
        public boolean isDingTalkUserAvailable(String result, String companyId) {
            return true;
        }

        private String getDingTalkServerUrl(String companyId) {
            return "https://oapi.dingtalk.com/sns/getuserinfo_bycode";
        }

        private String getDingTalkAccessKey(String companyId) {
            //获取ScanAppID
            DingTalkConfig dingTalkConfig = dingTalkConfigDAO.getPortalDingConfig(companyId);
            if (dingTalkConfig == null) {
                return null;
            }
            return dingTalkConfig.getScanAppId();
        }

        private String getDingTalkAccessSecret(String companyId) {
            //获取ScanAppSecret
            DingTalkConfig dingTalkConfig = dingTalkConfigDAO.getPortalDingConfig(companyId);
            if (dingTalkConfig == null) {
                return null;
            }
            return dingTalkConfig.getScanAppSecret();
        }

        private String getDinTalkUnionIdInfoUrl(String companyId) {
            return "https://oapi.dingtalk.com/user/getUseridByUnionid";
        }

        private String getAccessTokenUrl(String companyId) {
            return "https://oapi.dingtalk.com/gettoken";
        }

        private String getAppKey(String companyId) {
            //获取APP KEY
            DingTalkConfig dingTalkConfig = dingTalkConfigDAO.getPortalDingConfig(companyId);
            if (dingTalkConfig == null) {
                return null;
            }
            return dingTalkConfig.getAppKey();
        }

        private String getAppSecret(String companyId) {
            //获取APP Secret
            DingTalkConfig dingTalkConfig = dingTalkConfigDAO.getPortalDingConfig(companyId);
            if (dingTalkConfig == null) {
                return null;
            }
            return dingTalkConfig.getAppSecret();
        }

        private String getUserInfoUrl(String companyId) {
            return "https://oapi.dingtalk.com/user/get";
        }
        //根据code获取用户信息openId
        private OapiSnsGetuserinfoBycodeResponse getUserInfoByCode(String code, String companyId) {
            DefaultDingTalkClient client = new DefaultDingTalkClient(getDingTalkServerUrl(companyId));
            OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
            req.setTmpAuthCode(code);
            OapiSnsGetuserinfoBycodeResponse response = null;
            try {

                response = client.execute(req, getDingTalkAccessKey(companyId), getDingTalkAccessSecret(companyId));
            } catch (ApiException e) {
                return null;
            }

            //响应结果失败
            if (response == null || !response.isSuccess()) {
                return null;
            }

            return response;

        }

        //获取access_token
        private String getToken(String companyId) {
            //进行Token获取请求
            DefaultDingTalkClient client = new DefaultDingTalkClient(getAccessTokenUrl(companyId));
            OapiGettokenRequest tokenRequest = new OapiGettokenRequest();
            tokenRequest.setAppkey(getAppKey(companyId));
            tokenRequest.setAppsecret(getAppSecret(companyId));
            tokenRequest.setHttpMethod("GET");
            OapiGettokenResponse tokenResponse = null;
            try {
                tokenResponse = client.execute(tokenRequest);
            } catch (ApiException e) {
                return null;
            }

            //请求AccessToken失败
            if (tokenResponse == null) {
                return null;
            }

            //请求失败
            if (!tokenResponse.isSuccess()) {
                return null;
            }

            return tokenResponse.getAccessToken();
        }

        //根据access_token获取钉钉user_id
        private String getUserId(String companyId, String unionId, String token) {
            DefaultDingTalkClient client = new DefaultDingTalkClient(getDinTalkUnionIdInfoUrl(companyId));
            OapiUserGetUseridByUnionidRequest unionIdRequest = new OapiUserGetUseridByUnionidRequest();
            unionIdRequest.setUnionid(unionId);
            unionIdRequest.setHttpMethod("GET");

            OapiUserGetUseridByUnionidResponse unionIdResponse = null;
            try {
                unionIdResponse = client.execute(unionIdRequest, token);
            } catch (ApiException e) {
                return null;
            }

            if (unionIdResponse == null) {
                return null;
            }

            if (!unionIdResponse.isSuccess()) {
                return null;
            }

            return unionIdResponse.getUserid();
        }

        //根据钉钉user_id和access_token获取用户信息
        private OapiUserGetResponse getUserInfoByUserId(String userId, String token, String companyId) {
            DingTalkClient client = new DefaultDingTalkClient(getUserInfoUrl(companyId));
            OapiUserGetRequest request = new OapiUserGetRequest();
            request.setUserid(userId);
            request.setHttpMethod("GET");
            OapiUserGetResponse response = null;
            try {
                response = client.execute(request, token);
            } catch (ApiException e) {
                e.printStackTrace();
            }

            if (response == null) {
                return null;
            }

            if (!response.isSuccess()) {
                return null;
            }

            return response;
        }
    }

    private class CipherDynamicListener implements CipherDynamicAuthChannel.CipherDynamicAuthChannelListener {

        @Override
        public Integer getCipherDynamicAuthId(String userName, String companyId) {
            UserInfoDomain userInfo = userDAO.selectUserInfoWithPlatId(userName);
            if (userInfo == null) {
                return null;
            }
            return userInfo.getPlatId();
        }

        @Override
        public String getCipherAuthUrl(String userName, String companyId) {
            return "http://101.132.145.69:6114";
        }

        @Override
        public String getCipherAuthClientId(String userName, String companyId) {
            return "HJFJMWPT4SAPUY3G";
        }

        @Override
        public String getCipherAuthClientSecret(String userName, String companyId) {
            return "HJFJMWPT4SAPUY3G";
        }

        @Override
        public String getCipherDynamicAuthAuthType(String userName, String companyId) {
            return "totpAuth";
        }

        @Override
        public int getCipherDynamicAuthMode(String userName, String companyId) {
            return AuthMode.TOTP_CODE;
        }

        @Override
        public boolean isCipherDynamicAuthAvailable(String userName, String companyId) {
            return true;
        }
    }

    private class GoldMantisListener implements GoldMantisErpAuthChannel.GoldMantisErpAuthChannelListener {
        @Override
        public String getBaseUrl(String companyId) {
            ErpInfoDomain erpInfoDomain = erpDAO.selectGoldMantisErpUrl(companyId);
            if (erpInfoDomain == null) {
                return null;
            }
            return erpInfoDomain.getSrc();
        }
    }

    private class LdapListener implements LdapAuthChannel.LdapAuthChannelListener {

        private UserInfoDomain userInfo;

        @Override
        public String getDomain(String userName, String companyId) {
            if (userInfo == null) {
                userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            }
            if (userInfo == null) {
                return null;
            }
            String adUserName = userInfo.getAdUserName();
            if (StringUtils.isEmpty(adUserName)) {
                return null;
            }
            String[] splits = adUserName.split("@");
            if (splits.length < 2) {
                return null;
            }
            return splits[1];
        }

        @Override
        public String getLdapAddress(String userName, String companyId) {
            UserInfoDomain userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            if (userInfo == null) {
                return null;
            }
            return userInfo.getAdIp();
        }

        @Override
        public int getLdapPort(String userName, String companyId) {
            AdInfoDomain adInfo = adInfoDAO.selectAdInfoByUserInfo(new UserInfoDomain(userName, companyId));
            //查询为空返回默认的端口
            if (adInfo == null) {
                return 389;
            }
            return adInfo.getPort();
        }

        @Override
        public String getAdUserName(String userName, String companyId) {
            UserInfoDomain userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            if (userInfo == null) {
                return null;
            }
            return userInfo.getUpn();
        }

        @Override
        public boolean insertAdPwd(String userName, String password, String companyId) {
            //将AD密码存入本地数据库
            if (userInfo == null) {
                userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            }
            if (null == userInfo) {
                return false;
            }
            if (StringUtils.isEmpty(password)) {
                return false;
            }
            try {
                UserInfoDomain newuser = new UserInfoDomain();
                password = AES.encryptToBase64(password, AesKey.AES_KEY);
                newuser.setPassword(password);
                newuser.setUserId(userInfo.getUuid());
                String pwd = userDAO.getPwd(userInfo.getUuid());
                if (StringUtils.isEmpty(pwd)) {
                    userDAO.insertPwd(newuser);
                } else {
                    userDAO.updateUserPwd(newuser);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        public int getAdUserPwdStrategy(String userName, String companyId) {
            userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            return userInfo.getUserAccountControl();
        }

        @Override
        public void setAdUserPwdStrategy(String userName, String companyId) {
            userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            ldapService.modifyUserAccountContro(userInfo.getUuid());
        }

        @Override
        public void modifyLocalUserAccountControl(String userName, String companyId) {
            userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            cipherUserInfoService.modifyUserAccountControl(userInfo.getUuid(), LdapConstants.AD_PWD_STRATEGY,companyId);

           /* UserLoginRecInfo userLoginRecInfo=new UserLoginRecInfo();
            userLoginRecInfo.setUserId(userInfo.getUuid());
            userLoginRecInfo.setAccountNumber(userInfo.getAccountNumber());
            userLoginRecDAO.insertUserLoginRecInfo(userLoginRecInfo);*/
        }

        @Override
        public boolean isUnableCheck(String userName, String companyId) {
            userInfo = userDAO.selectUserInfoWithAdInfo(companyId, userName);
            return isUserUnableCheck(userInfo);
        }
    }

    private class LocalListener implements LocalAuthChannel.LocalAuthChannelListener {

        private UserInfoDomain userInfo;

        @Override
        public String getRealPassword(String userName, String companyId) {
            if (userInfo == null) {
                userInfo = userDAO.selectUserInfoWithPassword(userName);
            }
            //查询到的用户为空
            if (userInfo == null) {
                return null;
            }
            //查询到的密码为空
            if (StringUtils.isEmpty(userInfo.getPassword())) {
                return null;
            }
            try {
                //解密返回结果
                userInfo.setPassword(AES.decryptFromBase64(userInfo.getPassword(), AesKey.AES_KEY));
                return userInfo.getPassword();
            } catch (Exception e) {
                //解密失败返回空
                return null;
            }
        }

        @Override
        public String getDefaultPassword(String companyId) {
            //TODO:需要针对公司做出一点改动
            return userDAO.getFirstPwd();
        }

        @Override
        public boolean isUserEmployed(String userName, String companyId) {
            if (userInfo == null) {
                userInfo = userDAO.selectUserInfoWithPassword(userName);
            }
            //用户不存在
            if (userInfo == null) {
                return false;
            }
            //用户所在的公司为空
            if (StringUtils.isEmpty(userInfo.getCompanyId())) {
                return false;
            }
            //用户所在的公司和当前公司不一致
            if (!userInfo.getCompanyId().equals(companyId)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean isUnableCheck(String userName, String companyId) {
            if (userInfo == null) {
                userInfo = userDAO.selectUserInfoWithPassword(userName);
            }
            return isUserUnableCheck(userInfo);
        }

    }

    private class MailListener implements MailCodeAuthChannel.MailCodeAuthChannelListener {
        @Override
        public String getMailCode(String mail) {
            return (String) redisClient.get(CacheKey.getyKataCodeCacheKe(mail));
        }

        @Override
        public boolean isMailAvailable(String mail, String companyId) {
            return true;
        }
    }

    private class SmsListener implements SmsCodeAuthChannel.SmsCodeAuthChannelListener {
        @Override
        public String getSmsCode(String phone) {
            return (String) redisClient.get(CacheKey.getMobilePhoneSmsStrCacheKey(phone));
        }

        @Override
        public boolean isMobilePhoneAvailable(String phone, String companyId) {
            return true;
        }

    }

    private class DingPushListener implements  DingPushAuthChannel.DingPushAuthChannelListener{


        @Override
        public AuthResult getDingPushAuthInfo(String userId, String ip) {
            Long timestamp= System.currentTimeMillis();

            //push认证到钉钉小程序
            SystemConfigInfo systemConfigInfo=systemConfigInfoDAO.getSystemConfigInfo();

            DingPushAuthInfoMessage dingPushAuthInfoMessage = new DingPushAuthInfoMessage(userId, ip,timestamp,systemConfigInfo.getDeviceName());

            jmsProducer.sendMessage(dingPushAuthInfoMessage, JMSType.VPN_DEVICE_SERVICE);

            return getResultWithSync(userId,timestamp);

        }


//        @Override
        public boolean isDingPushAvailable(String result, String companyId) {
            return true;
        }

        public AuthResult getResultWithSync(String uuid,Long timestamp ) {

            Boolean result = (Boolean) getResultWithSyncService.getResultWithDing(CacheKey.getDingPushAuthResultKey(uuid,timestamp), null,uuid);
            System.out.println("++++++++++++result++++++++++++++"+result);

            if (null==result) {
              //  redisClient.put(CacheKey.getDingPushReturnResultKey(uuid),DING_PUSH_TIME_OUT.getResultCode());
                return new AuthResult(DING_PUSH_TIME_OUT,uuid,DING_PUSH_AUTH_TIME_OUT);
            }

            if (result==true) {
                redisClient.put(CacheKey.getDingPushReturnResultKey(uuid),SUCCESS.getResultCode());
                return new AuthResult(SUCCESS,uuid,DING_PUSH_AUTH_CHANNEL);
            }

            redisClient.put(CacheKey.getDingPushReturnResultKey(uuid),DING_PUSH_CHECK_FAILED.getResultCode());
            return new AuthResult(DING_PUSH_CHECK_FAILED, uuid,UNKNOWN_CHANNEL);

        }


    }

    private class  OtpDynamicAuthChannelListener implements  OtpDynamicAuthChannel.OtpDynamicAuthChannelListener{

        @Override
        public String getDynamicPassword(String userId) {
            String otpDynamicKey=(String)redisClient.get(CacheKey.getCacheKeyUserOtpSecrect(userId));
            if(StringUtils.isEmpty(otpDynamicKey)){
                otpDynamicKey= otpDynamicInfoService.getOtpDynamicInfo(userId);
            }
            return otpDynamicKey;
        }


        @Override
        public Boolean checkTotpDynamicCode(String userId,String otpDynamicCode) {

            String seed = getDynamicPassword(userId);

            if (StringUtils.isEmpty(seed)) {
                return false;
            }
            String key = AES.decryptFromBase64(seed, AesKey.AES_KEY);
            GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

            //时间戳不存在，则校验当前时间下的TOTP
            try {
                return  googleAuthenticator.authorize(key,Integer.parseInt(otpDynamicCode));
            }catch (Exception e){
                return    false;
            }

           /* //谷歌认证
            try {
                int totpPassword = new GoogleAuthenticator().getTotpPassword(key);
                String format = String.format("%06d", totpPassword);
                System.out.println("totpPassword----"+totpPassword);
                return new GoogleAuthenticator().authorize(key, Integer.parseInt(otpDynamicCode));
            } catch (Exception e) {
                return false;
            }*/
        }
    }

    private class WeixinScanAuthChannelListener implements WeixinScanAuthChannel.WeixinScanAuthChannelListener{
        String weixinScanAuthUser="";
        //获取微信扫码返回信息
        @Override
        public String getWeixinScanInfo(String code, String companyId) {
            String weixinScanAuthUser="'";
            try {
                WeiXinConfig weiXinConfig=wxInfoService.getWeiXinConfigInfo(companyId);
                if(null==weiXinConfig){
                    return null;
                }
                String accessToken=getAccessToken(weiXinConfig.getCorpId(),weiXinConfig.getCorpSecret());
                if(StringUtils.isEmpty(accessToken)){
                    return null;
                }

                String userTicket=getUserTicket(accessToken,code);
                if(StringUtils.isEmpty(userTicket)){
                    return null;
                }

                weixinScanAuthUser=getWechatWorkUserInfo(accessToken,userTicket);
                if(null==weixinScanAuthUser){
                    return null;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return weixinScanAuthUser;
        }


        @Override
        public boolean isWeixinUserAvailable(String result, String companyId) {
            return true;
        }

        @Override
        public String getAccessToken(String corpId, String corpSecret) throws IOException {
            URLBuilder builder = new URLBuilder("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
            builder.addParam("corpId", corpId).addParam("corpSecret", corpSecret);
            Map<String,Object> map= HttpClientUtils.doGet(builder.toString(),null);
            String res=(String) map.get("res");
            Gson gson=new Gson();
            GetWeixinTokenResp getWeixinTokenResp=gson.fromJson(res, GetWeixinTokenResp.class);
            if (getWeixinTokenResp.getErrcode() != 0) {
                logger.info("wexin scan getAccessToken failed errormsg"+getWeixinTokenResp.getErrmsg());
                return null;
            } else {
                return getWeixinTokenResp.getAccess_token();
            }

        }

        @Override
        public String getUserTicket(String accessToken, String code) {
            URLBuilder builder = new URLBuilder("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo");
            builder.addParam("access_token", accessToken).addParam("code", code);
            Map<String,Object> map=HttpClientUtils.doGet(builder.toString(),null);
            String res=(String) map.get("res");
            Gson gson=new Gson();
            GetWxUserIdResp getWxUserIdResp=gson.fromJson(res, GetWxUserIdResp.class);
            if (getWxUserIdResp.getErrcode() != 0) {
                logger.info("wexin scan getUserTicket failed errormsg" + getWxUserIdResp.getErrmsg());
                return null;
            }else {
                return getWxUserIdResp.getUserId();
            }

        }

        @Override
        public String getWechatWorkUserInfo(String accessToken, String userTicket) throws IOException {
            URLBuilder builder = new URLBuilder("https://qyapi.weixin.qq.com/cgi-bin/user/get");
            builder.addParam("access_token", accessToken).addParam("userid", userTicket);
            Map<String,Object> map=HttpClientUtils.doGet(builder.toString(),null);
            String res=(String) map.get("res");
            Gson gson=new Gson();
            WeixinScanAuthUser weixinScanAuthUser=gson.fromJson(res,WeixinScanAuthUser.class);
            if (weixinScanAuthUser.getErrcode()!= 0) {
                logger.info("wexin scan getWechatWorkUserInfo failed errormsg" + weixinScanAuthUser.getErrmsg());
                return null;
            }else {
                return res;
            }

        }


    }

    public boolean isUserUnableCheck(UserInfoDomain userInfo) {
        //用户不存在
        if (userInfo == null) {
            return true;
        }
        //用户不可用
        if (!userInfo.isAvailable()) {
            return true;
        }
        //TODO 从缓存中读取次数
        if (loginFailedUserService.isUserFreezed(userInfo.getUuid())) {
            return true;
        }
        return false;
    }


    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId) {
        return auth(authStrategy, authInfo, companyId, null, null, null,null);
    }

    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId, BayMaxScanAuthChannel.BayMaxScanInfoListener listener) {
        return auth(authStrategy, authInfo, companyId, listener, null, null,null);
    }

    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId, CipherScanAuthChannel.CipherScanInfoListener listener) {
        return auth(authStrategy, authInfo, companyId, null, listener, null,null);
    }

    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId, DingTalkScanAuthChannel.DingTalkScanInfoListener listener) {
        return auth(authStrategy, authInfo, companyId, null, null, listener,null);
    }

    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId, WeixinScanAuthChannel.WeixinScanInfoListener listener) {
        return auth(authStrategy, authInfo, companyId, null, null, null,listener);
    }




    @Override
    public AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                            BayMaxScanAuthChannel.BayMaxScanInfoListener bayMaxListener,
                            CipherScanAuthChannel.CipherScanInfoListener cipherAuthListener,
                            DingTalkScanAuthChannel.DingTalkScanInfoListener dingTalkListener,
                            WeixinScanAuthChannel.WeixinScanInfoListener weixinlistener
    ) {
        final String userName = authInfo.getUserName();
        final String password = authInfo.getPassword();
        final String dynamicCode = authInfo.getDynamicCode();
        final String bayMaxAuthScanInfo = authInfo.getBayMaxAuthScanInfo();
        final String cipherAuthScanInfo = authInfo.getCipherAuthScanInfo();
        final String dingTalkAuthScanInfo = authInfo.getDingTalkAuthScanInfo();
        final String phone = authInfo.getPhoneNumber();
        final String mail = authInfo.getMail();
        final String smsCode = authInfo.getSmsCode();
        final String mailCode = authInfo.getMailCode();
        final String ip=authInfo.getIp();
        final String otpDynamicCode=authInfo.getOtpDymaicCode();
        final String weixinAuthScanInfo=authInfo.getWeixinScanInfo();

        BayMaxScanAuthChannel bayMaxScanAuthChannel =
                new BayMaxScanAuthChannel(authStrategy, bayMaxAuthScanInfo, companyId, new BayMaxListener(), bayMaxListener);

        //赛赋扫码认证渠道
        CipherScanAuthChannel cipherscanAuthChannel =
                new CipherScanAuthChannel(authStrategy, cipherAuthScanInfo, companyId, new CipherScanLister(), cipherAuthListener);


        //钉钉扫码认证渠道
        DingTalkScanAuthChannel dingTalkScanAuthChannel =
                new DingTalkScanAuthChannel(authStrategy, dingTalkAuthScanInfo, companyId, new DingTalkListener(), dingTalkListener);

        //赛赋TOTP认证渠道
        CipherDynamicAuthChannel cipherDynamicAuthChannel =
                new CipherDynamicAuthChannel(authStrategy, userName, dynamicCode, companyId, new CipherDynamicListener());

        //本地认证渠道
        LocalAuthChannel localAuthChannel =
                new LocalAuthChannel(authStrategy, userName, password, companyId, new LocalListener());

        //Ldap认证渠道
        LdapAuthChannel ldapAuthChannel =
                new LdapAuthChannel(authStrategy, userName, password, companyId, new LdapListener());


        //ERP认证渠道
        GoldMantisErpAuthChannel goldMantisErpAuthChannel =
                new GoldMantisErpAuthChannel(authStrategy, userName, password, companyId, new GoldMantisListener());

        //手机验证码认证渠道
        SmsCodeAuthChannel smsCodeAuthChannel =
                new SmsCodeAuthChannel(authStrategy, phone, smsCode, companyId, new SmsListener());

        //邮箱验证码认证渠道
        MailCodeAuthChannel mailCodeAuthChannel =
                new MailCodeAuthChannel(authStrategy, mail, mailCode, companyId, new MailListener());


        //钉钉push认证渠道
        DingPushAuthChannel dingPushAuthChannel=
                new DingPushAuthChannel(authStrategy, userName, ip, new DingPushListener());

        //OTP动态码认证渠道
        OtpDynamicAuthChannel otpDynamicAuthChannel=
                new OtpDynamicAuthChannel(authStrategy,  userName,otpDynamicCode,companyId, new OtpDynamicAuthChannelListener());

        //微信扫码认证渠道
        WeixinScanAuthChannel weixinScanAuthChannel=
                new WeixinScanAuthChannel(authStrategy, weixinAuthScanInfo, companyId, new WeixinScanAuthChannelListener(),weixinlistener);


        //构造认证机器
        AuthMachine authMachine = new AuthMachine(bayMaxScanAuthChannel, authInfo)
                .next(cipherscanAuthChannel)
                .next(dingTalkScanAuthChannel)
                .next(localAuthChannel)
                .next(ldapAuthChannel)
                .next(goldMantisErpAuthChannel)
                .next(cipherDynamicAuthChannel)
                .next(smsCodeAuthChannel)
                .next(mailCodeAuthChannel)
                .next(dingPushAuthChannel)
                .next(otpDynamicAuthChannel)
                .next(weixinScanAuthChannel);

        return authMachine;
    }
}
