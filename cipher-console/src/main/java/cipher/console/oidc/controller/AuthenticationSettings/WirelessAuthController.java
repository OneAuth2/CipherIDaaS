package cipher.console.oidc.controller.AuthenticationSettings;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.AccountRegistration;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.UserBehaviorInfoService;
import cipher.console.oidc.service.authsettings.WirelessAuthService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cozi
 * @date 2019-04-28
 */

@Controller
@RequestMapping(value = "/cipher/setting")
public class WirelessAuthController {


    @Autowired
    private WirelessAuthService wirelessAuthService;

    @Autowired
    private SessionService sessionService;
    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     *无线认证设置
     * @param request
     * @param identityAuthentication 身份认证
     * @param accountRegistration 账号注册
     * @param accountBinding 账号绑定
     * @return
     */
    @RequestMapping(value = "/wirelessAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> wirelessAuthSettings(HttpServletRequest request, HttpSession session,
                                                   IdentityAuthentication identityAuthentication,
                                                   AccountRegistration accountRegistration,
                                                   AccountBinding accountBinding){
        Map<String,Object> map = new HashMap<>();
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),"更新无线认证设置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&identityAuthentication!=null&&accountRegistration!=null&&accountBinding!=null){
            map = wirelessAuthService.compileWirelessAuth(companyUuid, identityAuthentication, accountRegistration, accountBinding);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getWireAuthMsg(1));
    }

    /**
     *无线认证设置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/wirelessAuthEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> wirelessAuthEcho(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            map = wirelessAuthService.echoWirelessAuth(companyUuid);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getWireAuthMsg(5));
    }

}
