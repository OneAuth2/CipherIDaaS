package cipher.console.oidc.controller.AuthenticationSettings;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.AccountRegistration;
import cipher.console.oidc.domain.authsettingsdomain.ForgetPassword;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.authsettings.DoorAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 门户认证设置
 * @author cozi
 * @date 2019-04-28
 */
@Controller
@RequestMapping(value = "/cipher/setting")
public class DoorAuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorAuthController.class);

    @Autowired
    private DoorAuthService doorAuthService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     *
     * @param identityAuthentication 身份认证
     * @param accountRegistration 账号注册
     * @param accountBinding 账号绑定
     * @param forgetPassword 忘记密码
     * @return
     */
    @RequestMapping(value = "/doorAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doorAuthSettings(HttpServletRequest request, HttpSession session,
                                               IdentityAuthentication identityAuthentication,
                                               AccountRegistration accountRegistration,
                                               AccountBinding accountBinding,
                                               ForgetPassword forgetPassword) {
        if(forgetPassword.getIsOpenMailForget()==1&&forgetPassword.getIsOpenNumForget()==1){
            return NewReturnUtils.failureResponse(ReturnMsg.FORGETFAILED);
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新门户认证设置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        Map<String,Object> map = new HashMap<>();
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if((!StringUtils.isEmpty(companyUuid))&&identityAuthentication!=null&&accountRegistration!=null&&accountBinding!=null&&forgetPassword!=null){
                map = doorAuthService.compileDoorAuth(companyUuid,identityAuthentication,accountRegistration,accountBinding,forgetPassword);
                return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getDoorAuthMsg(1));
    }

    /**
     *门户认证设置回显
     *
     * @return
     */
    @RequestMapping(value = "/doorAuthEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doorAuthEcho(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());

        if(!StringUtils.isEmpty(companyUuid)){
            return doorAuthService.echoDoorAuth(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getDoorAuthMsg(5));
    }

}
