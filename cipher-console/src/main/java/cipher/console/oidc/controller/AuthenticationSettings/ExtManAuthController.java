package cipher.console.oidc.controller.AuthenticationSettings;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.authsettings.ExtManAuthService;
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
public class ExtManAuthController {


    @Autowired
    private ExtManAuthService extManAuthService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     * 管理后台认证设置
     * @param identityAuthentication
     * @param accountBinding
     * @return
     */
    @RequestMapping(value = "/extManAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> ExtManAuthSettings(HttpServletRequest request, HttpSession session,
                                                 IdentityAuthentication identityAuthentication,
                                                 AccountBinding accountBinding){
        Map<String,Object> map = new HashMap<>();
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新管理后台认证设置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&identityAuthentication!=null&&accountBinding!=null){
            map = extManAuthService.compileExtManAuth(companyUuid, identityAuthentication, accountBinding);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getExtmanAuthMsg(1));
    }

    /**
     *管理后台认证设置回显
     *
     * @return
     */
    @RequestMapping(value = "/extManAuthEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> ExtManAuthEcho(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            map = extManAuthService.echoExtManAuth(companyUuid);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getExtmanAuthMsg(5));
    }
}
