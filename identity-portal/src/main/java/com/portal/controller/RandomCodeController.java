package com.portal.controller;

import com.cipher.china.channel.AuthResult;
import com.cipher.china.channel.factory.AuthInfoFactory;
import com.cipher.china.channel.factory.AuthMachine;
import com.cipher.china.channel.pojo.AuthInfo;
import com.cipher.china.channel.pojo.AuthStrategy;
import com.portal.commons.CacheKey;
import com.portal.commons.Constants;
import com.portal.domain.SmsInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.PortalAuthChannelService;
import com.portal.service.SmsService;
import com.portal.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.EXPIRES;
import static com.portal.enums.ResultCode.*;

/**
 * 随机验证码的获取和校验业务
 * create by shizhao at 2019/5/18
 *
 * @author shizhao
 * @since 2019/5/18
 */
@RequestMapping(value = "/cipher/user")
@Controller
public class RandomCodeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RandomCodeController.class);

    @Autowired
    private SmsService smsService;

    @Autowired
    private PortalAuthChannelService portalAuthChannelService;


    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();

    /**
     * 邮件验证码发送
     *
     * @param companyUUid 公司ID
     * @param mail        需要发送验证码的邮箱
     */
    @RequestMapping(value = "/sendMailCode")
    @ResponseBody
    public Map<String, Object> sendMailCode(String companyUUid, String mail) {
        Map<String, Object> map=new HashMap<>();
        //入参校验
        if (isEmpty(companyUUid, mail)) {
            logger.warn("There is Empty Info In companyUUid =[{}] mail=[{}]", new Object[]{companyUUid, mail});
            return sendBaseErrorMap(ResultCode.PARAMETER_FAILURE);
        }


        //判断邮箱是否发送成功
        if (!smsService.sendMailMsg(companyUUid, mail)) {
            return sendBaseErrorMap(MSG_SEND_ERROR);
        }

        SmsInfoDomain smsInfoDomain=smsService.getSmsInfoConfig(companyUUid);
        map.put("interval_time",smsInfoDomain.getIntervalTime()*60);

        return sendTheMap(map,SUCCESS.getCode(),SUCCESS.getMessage());
    }

    /**
     * 手机验证码发送
     *
     * @param companyUUid 公司ID
     * @param phoneNumber 需要发送验证码的手机号
     * @return
     */
    @RequestMapping(value = "/sendPhoneCode")
    @ResponseBody
    public Map<String, Object> sendPhoneCode(String companyUUid, String phoneNumber) {
        //入参校验
        if (isEmpty(companyUUid, phoneNumber)) {
            logger.warn("There is Empty Info In companyUUid =[{}] phone=[{}]", new Object[]{companyUUid, phoneNumber});
            return sendBaseErrorMap(ResultCode.PARAMETER_FAILURE);
        }


        //失败返回错误原因
        if (!smsService.sendPhoneMsg(companyUUid, phoneNumber)) {
            return sendBaseErrorMap(ResultCode.MSG_SEND_ERROR);
        }

        //成功返回
        return sendBaseNormalMap(ResultCode.SUCCESS);
    }

    /**
     * 邮箱验证
     *
     * @param companyId 公司ID
     * @param mail        需要校验的邮箱
     * @param code        邮箱验证码
     * @param userId      用户的唯一标志（仅当进行完整性检查时传入）
     * @return
     */
    @RequestMapping(value = "/mailCodeChecked")
    @ResponseBody
    public Map<String, Object> emailChecked(@RequestParam(value = "companyUUid") String companyId,
                                            @RequestParam(value = "mail") String mail,
                                            @RequestParam(value = "code") String code,
                                            @RequestParam(value = "userId", required = false) String userId) {
        if (StringUtils.isBlank(companyId) || StringUtils.isBlank(mail) || StringUtils.isBlank(code)) {
            logger.warn("There Is Empty Info As companyId =[{}] accountNumber=[{}] mail=[{}] code=[{}] ",
                    new Object[]{companyId, mail, code});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        //打开mail验证的开关
        AuthStrategy authStrategy = new AuthStrategy();
        authStrategy.setMailRandomCode(Constants.ON);

        //设置要验证的信息
        AuthInfo authInfo = AuthInfoFactory.createMailCodeAuth(mail, code);
        //获取验证结果
        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyId);

        AuthResult authResult = authMachine.getTheResult();
        if (!authMachine.isSuccess()) {
            logger.info("Mail Code Auth Failed");

            return sendTheMap(authResult.getResultCode(), authResult.getResultMessage());
        }

        if (StringUtils.isEmpty(userId)){
            //插入注册的认证key
            redisClient.put(CacheKey.getCacheKeyUserRegisterMail(mail),mail,EXPIRES);
            return sendBaseNormalMap(SUCCESS);
        }
        //插入注册的认证key
        redisClient.put(CacheKey.getCacheKeyUserRegisterMail(mail),mail,EXPIRES);


        //插入绑定的key
        redisClient.put(CacheKey.getCacheKeyUserBindingMail(userId),mail,EXPIRES);
        return sendBaseNormalMap(SUCCESS);
    }

    /**
     * 手机验证码发送
     *
     * @param companyId 公司ID
     * @param phoneNumber 需要发送验证码的手机号
     * @param code        手机验证码
     * @param userId      用户的唯一标志（仅当进行完整性检查时传入）
     * @return
     */
    @RequestMapping(value = "/phoneCodeChecked")
    @ResponseBody
    public Map<String, Object> getPhoneCheck(@RequestParam(value = "companyUUid") String companyId,
                                             @RequestParam(value = "phoneNumber") String phoneNumber,
                                             @RequestParam(value = "code") String code,
                                             @RequestParam(value = "userId", required = false) String userId) {

        //入参校验
        if (StringUtils.isBlank(companyId) || StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(code)) {
            logger.warn("There Is Empty Info As companyId =[{}] phoneNumber=[{}] code=[{}] code=[{}] ",
                    new Object[]{companyId, phoneNumber, code});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        //创建手机认证方式并且去认证中心认证
        AuthStrategy authStrategy = new AuthStrategy();
        authStrategy.setPhoneRandomCode(Constants.ON);
        AuthInfo authInfo = AuthInfoFactory.createSmsCodeAuth(phoneNumber, code);
        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyId);
        AuthResult authResult = authMachine.getTheResult();
        if (!authResult.isSuccess()) {
            logger.info("Phone Code Check Failed As The Code =[{}]", code);

            return sendTheMap(authResult.getResultCode(), authResult.getResultMessage());
        }

        if (StringUtils.isEmpty(userId)){
            //插入注册的key
            redisClient.put(CacheKey.getCacheKeyUserRegisterPhone(phoneNumber),phoneNumber,EXPIRES);
            return sendBaseNormalMap(SUCCESS);
        }

        //插入注册的key
        redisClient.put(CacheKey.getCacheKeyUserRegisterPhone(phoneNumber),phoneNumber,EXPIRES);

        //插入绑定的key
        redisClient.put(CacheKey.getCacheKeyUserBindingPhone(userId),phoneNumber,EXPIRES);


//        //插入忘记密码的key
//        AuthFlow authFlow= (AuthFlow) redisClient.get(CacheKey.getCacheKeyUserAuth(userId));
//        if (authFlow == null){
//            authFlow=new AuthFlow();
//        }
//        authFlow.setMail(AuthFlow.SUCCESS);
//        redisClient.put(CacheKey.getCacheKeyAuthCode(userId),authFlow , EXPIRES);
        return sendBaseNormalMap(SUCCESS);
    }

}
