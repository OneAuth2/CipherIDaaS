package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.Constants;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.PasswordSettingDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.model.TotpAuthorizationModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cipher.console.oidc.common.ReturnUtils.successResponse;


/**
 * 安全管理
 * create by shizhao
 *
 * @author shizhao
 * @since 2018/5/30
 */
@Controller
@RequestMapping(value = "/cipher/auth")
@EnableAutoConfiguration
public class SafeManagementController {

    private static final Logger logger = LoggerFactory.getLogger(SafeManagementController.class.getSimpleName());

    @Autowired
    private AuthorizationMethodService authorizationMethodService;

    @Autowired
    private PasswordSettingService passwordSettingService;

    @Autowired
    private PasswordTypeInfoService passwordTypeInfoService;

    @Autowired
    private TotpAuthorizationService totpAuthorizationService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceInfoService deviceInfoService;




    /**
     * 设备管理
     *
     * @param accountNumber 用户主账号
     * @param limitCount    限制的设备数量
     * @param openCode      是否开启 0-关闭 1-开启
     * @return 设置信息
     */
    @RequestMapping(value = "management/device/submit")
    @ResponseBody
    public Map<String, Object> managementDeviceInfo(HttpServletRequest request,
                                                    @RequestParam(value = "accountNumber", required = false, defaultValue = "") String accountNumber,
                                                    @RequestParam(value = "openCode", required = false, defaultValue = "0") Integer openCode,
                                                    @RequestParam(value = "limitCount", required = false, defaultValue = "0") Integer limitCount) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to management/device/info on managementDeviceInfo");
        }
        System.out.println(accountNumber + openCode + limitCount);
        UserInfoDomain userInfoDomain = userService.getUserByAccountNumber(accountNumber);
        Map<String, Object> map = new HashMap<>();
        if (userInfoDomain == null) {
            map.put("msg", "此用户不存在");
            map.put("code", 0);
            return map;
        }
        int count = deviceInfoService.accountBundledCount(accountNumber);
        if (userInfoDomain.getLimitOpen().intValue() == 1 && userInfoDomain.getLimitOpen().intValue() != openCode) {//原本设置了设备限制，现在把设置关了
            map.put("msg", "您将关闭设备绑定限制，此操作将解除您曾经绑定过的设备信息");
            map.put("code", 1);
        } else if (userInfoDomain.getLimitOpen().intValue() == 1 && userInfoDomain.getLimitOpen().intValue() == openCode) {//设备限制开启是
            if (count > limitCount) {//已绑定的设备大鱼设定值
                map.put("msg", "当前已绑定设备数大于设定值，请解绑后操作");
                map.put("code", 2);
            } else {//已绑定的设备小于等于设定值
                userService.updateUserBundledInfo(new UserInfoDomain(accountNumber, limitCount, openCode));
                map.put("msg", "设置成功");
                map.put("code", 3);
            }
        } else if (userInfoDomain.getLimitOpen().intValue() == 0 && openCode == 1) {
            if (limitCount >= 0) {
                userService.updateUserBundledInfo(new UserInfoDomain(accountNumber, limitCount, openCode));
                map.put("msg", "设置成功");
                map.put("code", 3);
            } else {
                map.put("msg", "无效的操作，您设置的数值不能小于0");
                map.put("code", 4);
            }
        } else {
            map.put("msg", "该用户已经关闭设备控制");
            map.put("code", 5);
        }
        return map;
    }

    @RequestMapping(value = "management/device/clear")
    @ResponseBody
    public Map<String, Object> clearDeviceBundleInfo(@RequestParam(value = "accountNumber", required = false, defaultValue = "") String accountNumber) {
        Map<String, Object> map = new HashMap<>();
        if (deviceInfoService.clearBundleInfo(accountNumber)) {
            userService.updateUserBundledInfo(new UserInfoDomain(accountNumber, 0, 0));
            map.put("result", "success");
        } else {
            map.put("result", "error");
        }
        return map;
    }

    /**
     * 身份管理
     *
     * @return 用户认证方式全部信息
     */
    @RequestMapping(value = "/management/identity/info")
    @ResponseBody
    public Map<String, Object> managementIdentityInfo() {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /management/identity/info on managementIdentityInfo");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("methods", authorizationMethodService.queryAllAuthorizationMethod());
        return map;
    }



    /**
     * 密码管理信息的获取
     *modify by 田扛
     * modify time 2019年3月11日16:14:45
     * @return 密码管理的全部信息
     * (数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "/management/password/info")
    @ResponseBody
    public Map<String, Object> managementPasswordInfo(HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /management/password/info on managementPasswordInfo");
        }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> msg=new HashMap<>();
        try{
            msg.put("setting", passwordSettingService.queryPasswordSetting(companyId));
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","服务器失败");
            e.printStackTrace();
            return  map;
        }
        map.put("code",0);
        map.put("msg",msg);
        return map;
    }

    /**
     * 保存密码策略
     * create by 田扛
     * create time 2019年3月11日16:32:37
     * @param passwordSettingDomain
     * @return
     * (数据隔离修改)
     */
    @RequestMapping(value = "/management/password/save")
    @ResponseBody
    public Map<String,Object> savePassword(PasswordSettingDomain passwordSettingDomain,
                                           HttpServletRequest request, HttpSession session){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        passwordSettingDomain.setCompanyId(companyId);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "更新口令管理");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return passwordSettingService.savePassword(passwordSettingDomain);
    }


    /**
     * 身份认证信息系修改提交
     *
     * @param init      初始密码
     * @param length    密码最小长度
     * @param number    包含数字
     * @param lower     包含小写字母
     * @param capitals  包含大写字母
     * @param cPassword 包含特殊字符
     * @param username  不包含用户名
     * @return 修改结果
     */
    @RequestMapping(value = "/management/password/submit")
    @ResponseBody
    public Map<String, Object> managementIdentitySubmit(HttpServletRequest requeset,HttpSession session,
                                                        @RequestParam(value = "init", required = false) String init,
                                                        @RequestParam(value = "length", required = false) Integer length,
                                                        @RequestParam(value = "number", required = false) Integer number,
                                                        @RequestParam(value = "lower", required = false) Integer lower,
                                                        @RequestParam(value = "capitals", required = false) Integer capitals,
                                                        @RequestParam(value = "char", required = false) Integer cPassword,
                                                        @RequestParam(value = "username", required = false) Integer username) {

        if (logger.isDebugEnabled()) {
            logger.debug("turn to /management/password/submit on managementIdentitySubmit init=[{}],length=[{}],number=[{}],lower=[{}],capitals=[{}],cPassword=[{}],username=[{}]",
                    new Object[]{init, length, number, lower, capitals, cPassword, username});
        }
        if (init != null && length != null) {
            passwordSettingService.updatePasswordSetting(length, init);
        }
        if (number != null) {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USEFUL, Constants.PASSWORD_NUMBER);
        } else {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USELESS, Constants.PASSWORD_NUMBER);
        }
        if (lower != null) {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USEFUL, Constants.PASSWORD_LOWER);
        } else {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USELESS, Constants.PASSWORD_LOWER);
        }
        if (capitals != null) {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USEFUL, Constants.PASSWORD_CAPITAL);
        } else {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USELESS, Constants.PASSWORD_CAPITAL);
        }
        if (cPassword != null) {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USEFUL, Constants.PASSWORD_CHAR);
        } else {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USELESS, Constants.PASSWORD_CHAR);
        }
        if (username != null) {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USEFUL, Constants.PASSWORD_USERNAME);
        } else {
            passwordTypeInfoService.updatePasswordTypeInfo(Constants.PASSWORD_USELESS, Constants.PASSWORD_USERNAME);
        }
        try {
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "更新密码策略");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return successResponse("index", 1);
    }

    /**
     * Totp管理信息获取
     *
     * @param name      姓名
     * @param pageModel 分页信息
     */
    @RequestMapping(value = "/management/totp/info")
    @ResponseBody
    public Map<String, Object> managementTotpInfo(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                  @RequestParam(value = "sord", required = false, defaultValue = "desc") String sord,
                                                  @RequestParam(value = "sidx", required = false, defaultValue = "desc") String sidx,
                                                  DataGridModel pageModel) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /management/totp/info on managementTotpInfo name=[{}],pageModel=[{}]",
                    new Object[]{name, pageModel.toString()});
        }
        TotpAuthorizationModel model = new TotpAuthorizationModel();
        model.setName(name);
        model.setSidx(sidx);
        model.setSord(sord);
        return totpAuthorizationService.queryAllTotpTable(model, pageModel);
    }

    /**
     * 二维码扫描信息管理
     *
     * @param accountNumber 主账号
     * @return 二维码管理信息
     */
    @RequestMapping(value = "/management/totp/scratch")
    @ResponseBody
    public Map<String, Object> managementScratchCode(@RequestParam(value = "accountNumber") String accountNumber) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /management/totp/scratch on managementScratchCode accountNumber=[{}]",
                    new Object[]{accountNumber});
        }
        accountNumber = accountNumber.replace("/", "");
        Map<String, Object> map = new HashMap<>();
        map.put("scratchCode", totpAuthorizationService.queryScratchCodeByAccountNumber(accountNumber));
        return map;
    }

}
