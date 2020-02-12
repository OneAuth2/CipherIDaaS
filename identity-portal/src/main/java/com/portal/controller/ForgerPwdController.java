package com.portal.controller;

import com.cipher.china.channel.factory.AuthInfoFactory;
import com.cipher.china.channel.factory.AuthMachine;
import com.cipher.china.channel.pojo.AuthInfo;
import com.cipher.china.channel.pojo.AuthStrategy;
import com.portal.commons.CacheKey;
import com.portal.commons.Constants;
import com.portal.commons.ConstantsCMP;
import com.portal.domain.*;
import com.portal.enums.ResultCode;
import com.portal.listener.CookieListener;
import com.portal.publistener.UserBehaviorPublistener;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.CookieUtils;
import com.portal.utils.IpUtil;
import com.portal.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.EXPIRES;
import static com.portal.commons.Constants.USERNAME;
import static com.portal.enums.ResultCode.*;

/**
 * @Author: TK
 * @Date: 2019/4/28 14:10
 */
@Controller
@RequestMapping(value = "/cipher/forget")
@EnableAutoConfiguration
public class ForgerPwdController extends BaseController implements CookieListener{

    private static final Logger logger = LoggerFactory.getLogger(ForgerPwdController.class);

    @Autowired
    private PortalAuthChannelService portalAuthChannelService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();

    @Autowired
    private IdentityModesService identityModesService;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private UserLoginRecService userLoginRecService;

    @Autowired
    private ISessionService sessionService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;

    /**
     * 获取公司忘记密码的流程
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @PostMapping(value = "/forgetPwdFlow")
    @ResponseBody
    public Map<String, Object> getForgetPwdFlow(String companyUUid) {
        Map<String, Object> map = new HashMap<>();

        //入参校验
        if (companyUUid.isEmpty()) {
            logger.warn(" enter ForgetPwdFlow.getForgetPwdFlow  Error As The companyUUid =[{}]", new Object[]{companyUUid});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        ForgetPassword forgetPassword = identityModesService.getForgetPasswordFlow(companyUUid);

        map.put("forgetPwdFlow", forgetPassword);

        return sendBaseNormalMap(map);

    }


    @PostMapping(value = "/phoneCodeChecked")
    @ResponseBody
    public Map<String, Object> getPhoneCheck(@RequestParam(value = "companyUUid") String companyId,
                                             @RequestParam(value = "phoneNumber") String phoneNumber,
                                             @RequestParam(value = "code") String code) {

        //入参校验
        if (isEmpty(companyId,phoneNumber,code)){
            logger.info("There Is Empty Info As companyId=[{}],phoneNumber=[{}],code=[{}]",
                    companyId,phoneNumber,code);

            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        //认证成功 获取认证的主账号
        UserInfoDomain userInfo= userInfoService.getCompanyUserInfoByPhoneNumber(phoneNumber,companyId);

        //判断用户信息是否为空
        if (userInfo ==null){
            logger.info("No User Found To Reset Password..");

            return sendBaseErrorMap(USER_IS_NOT_EXIST);
        }

        //创建手机认证方式并且去认证中心认证
        AuthStrategy authStrategy = new AuthStrategy();
        authStrategy.setPhoneRandomCode(Constants.ON);
        AuthInfo authInfo = AuthInfoFactory.createSmsCodeAuth(phoneNumber, code);
        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyId);
        //获取认证结果 并判断结果是否是否认证成功 不成功直接返回
        if (!authMachine.isSuccess()) {
            logger.info("Auth Failed..");
            return sendBaseErrorMap(LOGIN_AUTH_FAILED);
        }

        //判断redis是否有该对象
        AuthFlow authFlow= (AuthFlow) redisClient.get(CacheKey.getCacheKeyUserAuth(userInfo.getUuid()));
        if (authFlow == null){
            authFlow=new AuthFlow();
        }

        //认证成功 把认证成功结果放入缓存中
        authFlow.setPhoneNumber(AuthFlow.SUCCESS);
        redisClient.put(CacheKey.getCacheKeyUserAuth(userInfo.getUuid()),authFlow , EXPIRES);
        Map<String,Object> map = new HashMap<>();
        return setMapParam(map,Constants.USER_ID,userInfo.getUuid()).sendBaseNormalMap(map,SUCCESS);
    }
    /**
     * 邮箱验证
     * @param companyId
     * @param mail
     * @param code
     * @return
     */
    @PostMapping(value = "/emailChecked")
    @ResponseBody
    public Map<String, Object> emailChecked(@RequestParam(value = "companyUUid") String companyId,
                                            @RequestParam(value = "mail") String mail,
                                            @RequestParam(value = "code") String code) {

        if (StringUtils.isBlank(companyId)  || StringUtils.isBlank(mail) || StringUtils.isBlank(code)) {
            logger.warn("There Is Empty Info Exist As ", new Object[]{companyId,  mail, code});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        //认证成功获取认证的主账号
        UserInfoDomain userInfo= userInfoService.getCompanyUserInfoByMail(mail,companyId);

        //判断根据邮箱搜索是否能查出主账号 如果为空直接返回认证失败
        if (userInfo == null){
            logger.info("User Info Is Not Exist..");

            return sendBaseErrorMap(ResultCode.USER_IS_NOT_EXIST);
        }

        //打开mail验证的开关
        AuthStrategy authStrategy = new AuthStrategy();
        authStrategy.setMailRandomCode(Constants.ON);
        //设置要验证的信息
        AuthInfo authInfo = AuthInfoFactory.createMailCodeAuth(mail, code);
        //获取验证结果
        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyId);
        if (!authMachine.isSuccess()) {
            logger.info("Auth Failed As Message = [{}]",authMachine.getTheFailedResult().getResultMessage());

            return sendBaseErrorMap(LOGIN_AUTH_FAILED);
        }

        //判断redis是否有该对象
        AuthFlow authFlow= (AuthFlow) redisClient.get(CacheKey.getCacheKeyUserAuth(userInfo.getUuid()));
        if (authFlow == null){
            authFlow=new AuthFlow();
        }

        //设置邮箱验证已通过
        authFlow.setMail(0);
        redisClient.put(CacheKey.getCacheKeyUserAuth(userInfo.getUuid()),authFlow , EXPIRES);
        Map<String,Object> map = new HashMap<>();
        return setMapParam(map,Constants.USER_ID,userInfo.getUuid()).sendBaseNormalMap(map,SUCCESS);

    }

    /**
     * 设置新密码
     *
     * @param companyId
     * @param password
     * @return
     */
    @PostMapping(value = "/reset")
    @ResponseBody
    public Map<String, Object> setPasswordChecked(HttpServletRequest request, HttpServletResponse response,
                                                  @RequestParam(value = "companyUUid") String companyId,
                                                  @RequestParam(value = "userId") String userId,
                                                  @RequestParam(value = "password") String password) {
        //入参校验
        if (isEmpty(companyId,userId,password)){
            logger.info("There Is Empty Info Exist As companyUUid=[{}],userId=[{}],password=[{}]",
                    new Object[]{companyId,userId,password});

            return sendBaseErrorMap(PARAMETER_FAILURE);
        }


        //将字符串转换为实体类获取忘记密码流程
        ForgetPassword forgetPassword = identityModesService.getForgetPasswordFlow(companyId);

        //如果忘记密码流程为空则直接返回错误
        if (forgetPassword == null) {
            logger.info("There Is No Found Any Config Of Password Reset..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE);
        }

        boolean needMailCheck = forgetPassword.getIsOpenMailForget()==Constants.AUTH_ON?true:false;

        boolean needPhoneCheck = forgetPassword.getIsOpenNumForget()==Constants.AUTH_ON?true:false;

        //boolean needCipherAuthCheck = forgetPassword.getTwoAuthForgetSf() == Constants.AUTH_ON?true:false;

        boolean needBayMaxAuthCheck = forgetPassword.getTwoAuthForgetDb() == Constants.AUTH_ON?true:false;

        boolean needDingTalkAuthCheck = forgetPassword.getTwoAuthForgetDd() == Constants.AUTH_ON?true:false;

        AuthFlow authFlow  =(AuthFlow) redisClient.get(CacheKey.getCacheKeyUserAuth(userId));

        //新建要验证的实体类
        UserInfoDomain userInfoDomain=userInfoService.getUserInfoByUUid(userId);

        boolean isMailChecked = authFlow == null?false:authFlow.getMail()==Constants.AUTH_ON?true:false;

        boolean isPhoneChecked = authFlow == null?false:authFlow.getPhoneNumber()==Constants.AUTH_ON?true:false;

        boolean isCipherChecked = authFlow == null?false:StringUtils.isNotEmpty(authFlow.getSaiFuBindLocalUuid());

        boolean isBayMaxChecked = authFlow == null?false:StringUtils.isNotEmpty(authFlow.getDaBaiBindLocalUuid());

        boolean isDingTalkAuthChecked = authFlow == null?false:StringUtils.isNotEmpty(authFlow.getDingBindLocalUuid());

        //需要进行邮箱检查而未进行邮箱校验
        if (needMailCheck && !isMailChecked){
            logger.info("Not Complete The Mail Auth Flow..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE_AUTH);
        }

        //需要进行手机检查而未进行手机检查
        if (needPhoneCheck && !isPhoneChecked){
            logger.info("Not Complete The Phone Auth Flow..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE_AUTH);
        }

       /* //需要进行赛赋认证而未进行赛赋认证
        if (needCipherAuthCheck && !isCipherChecked){
            logger.info("Not Complete The Cipher Auth Flow..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE_AUTH);
        }
*/
        //需要进行大白认证而未进行大白认证
        if (needBayMaxAuthCheck && !isBayMaxChecked){
            logger.info("Not Complete The BayMax Auth Flow..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE_AUTH);
        }

        //需要进行钉钉认证而未进行钉钉认证
        if (needDingTalkAuthCheck && !isDingTalkAuthChecked){
            logger.info("Not Complete The DingTalk Auth Flow..");

            return sendBaseErrorMap(PASSWORD_RESET_NOT_COMPLETE_AUTH);
        }

        if (!resetPasswordService.resetPassword(companyId,userId,password)){
            logger.info("Update Password Failed..");

            return sendBaseErrorMap(PASSWORD_RESET_FAILED_PASSWORD_NOT_ALLOWED);
        }

        //查看用户的密码策略是否是AD如果是修改AD密码
        if(!resetPasswordService.resetAdPwd(userId,password,companyId)){
            logger.info("Can't Reset This Password = [{}]",password);
            return sendBaseErrorMap(PASSWORD_RESET_FAILED);
        }


        if (userLoginRecService.selectUserLoginRecInfoByUUid(userId) != null){
            userLoginRecService.updateUserLoginRecInfoUUid(userId);
        }else {
            userLoginRecService.insertUserLoginRecInfoWithUUid(new UserLoginRecInfo(userId));
        }

        //TODO:增加session,cookie
        sessionService.updateSessionExpireTime(IpUtil.getIp(),companyId,userId);
        try {
            UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(userId,null, ConstantsCMP.UserBehaviorConstant.ACCOUNT_MAINTAIN,IpUtil.getIp(),"设置新密码",companyId);
            userBehaviorPublistener.publish(userBehaviorInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        SessionUtils.setSession(request,response, Constants.COMPANY_UUID, companyId);
        SessionUtils.setSession(request, response,USERNAME, userId);
        CookieUtils.writePortalCookie(response, userId,this::setCookies);

        return sendBaseNormalMap();
    }

    @Override
    public void setCookies(String key, String value) {
        redisClient.put(key,value);
    }

//    @PostMapping(value = "/reset")
//    @ResponseBody
//    public Map<String,Object> forgetReset(@RequestParam(value = "companyUUid") String companyId,
//                                          @RequestParam(value = "userId") String userId,
//                                          @RequestParam(value = "password") String password){
//
//        //参数为空校验
//        if (isEmpty(companyId,userId,password)){
//            logger.info("There Is Empty Info Exist As CompanyUUid=[{}],userId=[{}],password=[{}]",
//                    new Object[]{companyId,userId,password});
//            return sendBaseErrorMap(PARAMETER_FAILURE);
//        }
//
//        UserInfoDomain userInfo = userInfoService.getUserInfoByUUid(userId);
//        //用户不存在
//        if (userInfo == null){
//            logger.info("Not Found Any User To Change Password As userId = [{}]",userId);
//
//            return sendBaseErrorMap(USER_IS_NOT_EXIST);
//        }
//
//
//
//
//    }


}
