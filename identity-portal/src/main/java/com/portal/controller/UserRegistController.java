package com.portal.controller;


import com.portal.domain.AccountRegistration;
import com.portal.domain.IdentitySettingInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.IdentityModesService;
import com.portal.service.PortalAuthChannelService;
import com.portal.service.UserInfoService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.portal.enums.ResultCode.PARAMETER_FAILURE;
import static com.portal.enums.ResultCode.PORTAL_SYSTEM_ERROR;

/**
 * @Author: TK
 * @Date: 2019/4/28 14:04
 */

@RequestMapping("/cipher/regist")
@Controller
public class UserRegistController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindingController.class);

    @Autowired
    private IdentityModesService identityModesService;


    /**
     * 获取公司账号绑定的流程
     *
     * @param companyUUid 公司UUid
     * @return
     */
    @RequestMapping(value = "/registFlow")
    @ResponseBody
    public Map<String, Object> getRegistFlow(String companyUUid) {
        Map<String, Object> map = new HashMap<>();

        //入参校验
        if (companyUUid.isEmpty()) {

            LOGGER.warn(" enter UserRegistController.getRegistFlow  Error As The companyUUid =[{}]", new Object[]{companyUUid});
            sendBaseErrorMap(PARAMETER_FAILURE);
        }

        try {

            //获取身份认证设置
            IdentitySettingInfoDomain identitySettingInfoDomain = identityModesService.getIdentityModesByCompanyUUid(companyUUid);

            //判断对象为空
            if (identitySettingInfoDomain == null) {
                return sendBaseErrorMap(ResultCode.REGIST_ERROR);
            }

            //将字符串转换为实体类获取注册流程
            AccountRegistration accountRegistration = null;
            if (!identitySettingInfoDomain.getRegistFlow().isEmpty()) {
                JSONObject jsonObject = JSONObject.fromObject(identitySettingInfoDomain.getRegistFlow());
                accountRegistration = (AccountRegistration) JSONObject.toBean(jsonObject, AccountRegistration.class);

            }

            //添加到map中返回
            map.put("registFlow", accountRegistration);
        } catch (Exception e) {

            //打印错误日志
            LOGGER.error("Enter UserRegistController.getRegistFlow But failed companyUUid[{}]",
                    new Object[]{companyUUid});
            LOGGER.error(e.getMessage(), e);

            sendBaseErrorMap(PORTAL_SYSTEM_ERROR);

        }

        return sendBaseNormalMap(map);
    }

//    /**
//     * 注册手机验证码校验
//     *
//     * @param companyUUid
//     * @param phoneNumber
//     * @param code
//     * @return
//     */
//    @RequestMapping(value = "/registPhoneChecked")
//    @ResponseBody
//    public Map registPhoneChecked(String companyUUid, String phoneNumber, String code) {
//
//        //入参校验
//        if (StringUtils.isBlank(companyUUid) || StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(code)) {
//
//            LOGGER.warn(" enter UserRegistController.registPhoneChecked  Error As The companyUUid =[{}] phoneNumber=[{}] code=[{}] code=[{}] ", new Object[]{companyUUid, phoneNumber, code});
//
//            return sendBaseErrorMap(PARAMETER_FAILURE);
//        }
//
//
//        //创建手机认证方式并且去认证中心认证
//        AuthStrategy authStrategy = new AuthStrategy();
//        authStrategy.setPhoneRandomCode(Constants.ON);
//        AuthInfo authInfo = AuthInfoFactory.createSmsCodeAuth(phoneNumber, code);
//        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyUUid);
//
//        //
//
//        //认证成功添加到redis中
//        redisClient.put(CacheKey.getCacheKeyPhoneAuthCode(phoneNumber), Constants.AUTH_SUCCESS, EXPIRES);
//        return sendBaseNormalMap(SUCCESS);
//
//    }
//
//
//    @RequestMapping(value = "/registMailChecked")
//    @ResponseBody
//    public Map<String, Object> registMailChecked(String companyUUid, String mail, String code) {
//        //入参校验
//        if (StringUtils.isBlank(companyUUid) || StringUtils.isBlank(mail) || StringUtils.isBlank(code)) {
//
//            LOGGER.warn(" enter UserRegistController.registMailChecked  Error As The companyUUid =[{}] mail=[{}] code=[{}] code=[{}] ", new Object[]{companyUUid, mail, code});
//            return sendBaseErrorMap(PARAMETER_FAILURE);
//        }
//
//        //查找本地账号是否存在
//        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByMail(mail);
//
//        //查找注册表中是否存在该人的信息
//        RegisterApprovalDomain registerApprovalDomain = new RegisterApprovalDomain();
//        registerApprovalDomain.setUserEmail(mail);
//        RegisterApprovalDomain registerApprovalDomain1 = userInfoService.getRegistUser(registerApprovalDomain);
//
//        //registerApprovalDomain1 和userInfoDomain不为空 该用户邮箱存在
//        if (userInfoDomain !=null || registerApprovalDomain1 != null){
//            return sendBaseErrorMap(ResultCode.REGIST_EXIST_LOCAL);
//        }
//
//        //创建手机认证方式并且去认证中心认证
//        AuthStrategy authStrategy = new AuthStrategy();
//        authStrategy.setMailRandomCode(Constants.ON);
//        AuthInfo authInfo = AuthInfoFactory.createMailCodeAuth(mail, code);
//        AuthMachine authMachine = portalAuthChannelService.auth(authStrategy, authInfo, companyUUid);
//
//        //
//
//        //认证成功加入redis中
//        redisClient.put(CacheKey.getCacheKeyMailAuthCode(mail), Constants.AUTH_SUCCESS, EXPIRES);
//
//        return sendBaseNormalMap(SUCCESS);
//    }


}
