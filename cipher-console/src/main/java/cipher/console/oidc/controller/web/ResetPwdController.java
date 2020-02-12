package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.GlobalAuthType;
import cipher.console.oidc.common.GlobalReturnCode;
import cipher.console.oidc.common.PasswordTypeCode;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.PasswordAuthorizationMapper;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.aes.AES;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping(value = "/cipher/resetPwd")
public class ResetPwdController {

    private static final Logger logger = LoggerFactory.getLogger(ResetPwdController.class.getSimpleName());

    @Autowired
    private ResetPwdService resetPwdService;

    @Autowired
    private PasswordAuthorizationMapper passwordAuthorizationMapper;

    @Autowired
    private PasswordAuthorizationService passwordAuthorizationService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;


    /*
     * 获取用户修改密码的策略
     * 1：是AD 0:本地
     * (数据隔离调整)
     * */
    @CheckToken
    @RequestMapping(value = "/getCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCode(HttpServletRequest request,
                                       @RequestParam(value = "accountNumber", required = false) String accountNumber,
                                       @RequestParam(value = "groupId", required = false) String groupId,
                                       @RequestParam(value = "uuid", required = false) String uuid) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to ResetPwdController.getCode groupId=[{}] uuid=[{}]", new Object[]{groupId, uuid});
        }
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        try {
            GlobalAuthType  globalAuthType=null;
            if(StringUtils.isEmpty(groupId)){
                globalAuthType=resetPwdService.getGlobalAuthType(uuid,0,companyId);
            }else{
                globalAuthType=resetPwdService.getGlobalAuthType(uuid,Integer.valueOf(groupId),companyId);
            }
            if (globalAuthType.getCode() == GlobalAuthType.ADAUTH.getCode()) {
                map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
                map.put("is_ad_update", ConstantsCMP.IsAdon.AD_YES);
                map.put("return_msg", "操作成功");
            } else {
                map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
                map.put("return_msg", "操作成功");
                map.put("is_ad_update", ConstantsCMP.IsAdon.AD_NO);
            }


        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }


    /*
     * 组织结构用户重置密码接口
     * (数据隔离调整)
     * */

    @RequestMapping(value = "/resetAdPwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updatePwd(HttpServletResponse response, HttpSession session,
                                         @RequestParam(value = "accountNumber", required = false) String accountNumber,
                                         @RequestParam(value = "uuid") String uuid,
                                         @RequestParam(value = "pwd") String pwd,
                                         @RequestParam(value = "logFlag") int logFlag, HttpServletRequest request) {
        logger.debug("enter ResetPwdController.updatePwd");
        Map<String, Object> map = new HashMap<>();
        UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        AdminBehaviorInfo adminBehaviorInfo =null;
       // String companyId="123456";
        PasswordTypeCode passwordTypeCode = passwordAuthorizationService.updatePassword(uuid, pwd,companyId,logFlag);
        if (passwordTypeCode.getCode() == 1) {
            GlobalReturnCode.MsgCodeEnum msgCodeEnum = resetPwdService.modifyPwd(uuid, pwd);
            if (msgCodeEnum.getCode() != 0) {
                map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
                map.put("return_msg", msgCodeEnum.getMsg());
                return map;
            }
            pwd = AES.encryptToBase64(pwd, ConstantsCMP.AES_KEY);
            PasswordAuthorizationMagDomain passwordAuthorizationMagDomain = passwordAuthorizationMapper.selectPasswordAuthorizationMagDomain(uuid);
            if (null == passwordAuthorizationMagDomain) {
                passwordAuthorizationMagDomain = new PasswordAuthorizationMagDomain();
                passwordAuthorizationMagDomain.setUserId(uuid);
                passwordAuthorizationMagDomain.setAccountNumber(accountNumber);
                passwordAuthorizationMagDomain.setPassword(pwd);
                passwordAuthorizationMapper.insertAccountPassword(passwordAuthorizationMagDomain);
            } else {
                passwordAuthorizationMapper.updatePassword(uuid, pwd);
            }
            adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(), "重置:" + userInfoDomain.getUserName() + "成功");
            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
            map.put("return_msg", "修改密码成功");
        } else {
            adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(), "重置:" + userInfoDomain.getUserName() + "失败");
            map.put("return_code", passwordTypeCode.getCode());
            map.put("return_msg", passwordTypeCode.getType());

        }
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo, session);
        return map;

    }
}
