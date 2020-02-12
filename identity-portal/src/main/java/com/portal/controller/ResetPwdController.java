package com.portal.controller;


import com.portal.domain.PasswordSetting;
import com.portal.domain.UserInfoDomain;
import com.portal.domain.UserLoginRecInfo;
import com.portal.enums.ResultCode;
import com.portal.service.InitPasswordService;
import com.portal.service.ResetPasswordService;
import com.portal.service.UserInfoService;
import com.portal.service.UserLoginRecService;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.portal.enums.ResultCode.*;

@Controller
@RequestMapping(value = "/cipher")
public class ResetPwdController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ResetPwdController.class);


    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private InitPasswordService initPwdService;

    @Autowired
    private UserLoginRecService userLoginRecService;




    /*
     * 检验账号密码方式
     * */

    @RequestMapping(value = "/resetpwd/checkpwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkpwd(HttpServletRequest request,
                                        @RequestParam(value = "userId") String uuid,
                                        @RequestParam(value = "companyUUid") String companyUuid,
                                        @RequestParam(value = "pwd", required = false) String pwd,
                                        @RequestParam(value = "newpwd") String newpwd) {

        //入参校验
        if (isEmpty(uuid,companyUuid,pwd,newpwd)) {
            logger.warn(" enter ResetPwdController.checkpwd  Error As The companyUUid =[{}] uuid=[{}] pwd=[{}] newpwd=[{}] ",
                    new Object[]{companyUuid, uuid, pwd,newpwd});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        return resetPasswordService.checkUserPwd(uuid, newpwd, pwd,companyUuid);
    }
    /*
     * 修改密码
     *
     * */

    @RequestMapping(value = "/resetpwd/reset", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> repwd(HttpServletRequest request,
                                     @RequestParam(value = "userId") String uuid,
                                     @RequestParam(value = "companyUUid") String companyUuid,
                                     @RequestParam(value = "pwd", required = false) String pwd,
                                     @RequestParam(value = "newpwd") String newpwd) {
        //入参校验
        if (isEmpty(uuid,companyUuid,pwd,newpwd)) {
            logger.warn(" enter ResetPwdController.repwd  Error As The companyUUid =[{}] uuid=[{}] pwd=[{}] newpwd=[{}] ",
                    new Object[]{companyUuid, uuid, pwd,newpwd});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        return resetPasswordService.updateUserPwd(uuid, newpwd, pwd,companyUuid);
    }


    /**
     * 修改密码
     *
     * */
    @PostMapping(value = "/user/passwordSet")
    @ResponseBody
    public Map<String, Object> setPassword(HttpServletRequest request,
                                           @RequestParam(value = "userId")String userId,
                                           @RequestParam(value = "companyUUid")String companyId,
                                           @RequestParam(value = "password")String newPwd) {

        return setPassword(userId, companyId, newPwd);
    }


    /**
     * 修改密码
     *
     * */
    @PostMapping(value = "/reset/password")
    @ResponseBody
    public Map<String, Object> repwd(HttpServletRequest request,
                                     @RequestParam(value = "userId")String userId,
                                     @RequestParam(value = "companyUUid")String companyId,
                                     @RequestParam(value = "newPwd")String newPwd) {
        return setPassword(userId, companyId, newPwd);
    }



    private Map<String, Object> setPassword(String userId, String companyId, String newPwd){
        if (isEmpty(userId,companyId,newPwd)){

            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        //查询用户信息
        UserInfoDomain userInfo = userInfoService.getUserInfoByUUid(userId);
        //用户不存在
        if (userInfo == null){
            logger.info("User Is Not Exist As UserId = [{}]",userId);

            return sendBaseErrorMap(USER_IS_NOT_EXIST);
        }

        PasswordSetting passwordSetting = initPwdService.getPasswordSetting(companyId);
        //密码策略为空
        if (passwordSetting == null){
            logger.info("Didn't Get Right Config Of Password Setting As CompanyId = [{}]",companyId);

            return sendBaseErrorMap(PASSWORD_CONFIG_NOT_COMPLETE);
        }

        //密码是否为可接受的密码
        if (!initPwdService.isAcceptable(newPwd,passwordSetting)){
            logger.info("Can't Accept This Password = [{}]",newPwd);

            return sendBaseErrorMap(PASSWORD_CAN_NOT_BE_ACCEPT);
        }

        //查看用户的密码策略是否是AD如果是修改AD密码
       if(!resetPasswordService.resetAdPwd(userId,newPwd,companyId)){
           logger.info("Can't Reset This Password = [{}]",newPwd);
           return sendBaseErrorMap(PASSWORD_RESET_FAILED);
       }

        try {
            userInfo.setPassword(AES.encryptToBase64(newPwd, AesKey.AES_KEY));
        }catch (Exception e){
            logger.info("Encrypt Catch An Exception ..");

            return sendBaseErrorMap(PASSWORD_RESET_FAILED);
        }

        if (!userInfoService.updateUserPwdByUuid(userInfo)){
            logger.info("Encrypt Catch An Exception ..");

            return sendBaseErrorMap(PASSWORD_RESET_FAILED);
        }

        if (userLoginRecService.selectUserLoginRecInfoByUUid(userId) != null){
            userLoginRecService.updateUserLoginRecInfoUUid(userId);
        }else {
            userLoginRecService.insertUserLoginRecInfoWithUUid(new UserLoginRecInfo(userId));
        }

        return sendBaseNormalMap();
    }






}
