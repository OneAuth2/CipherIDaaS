package cipher.console.oidc.controller.AuthenticationSettings;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AuthStrategyDomain;
import cipher.console.oidc.domain.authsettingsdomain.SecondAuthStrategyDomain;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.authsettings.AuthStrategyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/setting")
public class AuthStrategyController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AuthStrategyService authStrategyService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     * 身份认证策略
     * @param uuid 数据唯一值
     * @param authStrategyDomain 身份认证策略类
     * @param secondAuthStrategyDomain 二次认证方式
     * @return
     */
    @RequestMapping(value = "/identAuth",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> authStrategySettings(HttpServletRequest request, HttpSession session,
                                                   @RequestParam(value = "uuid") String uuid,
                                                   AuthStrategyDomain authStrategyDomain,
                                                   SecondAuthStrategyDomain secondAuthStrategyDomain){
        Map<String,Object> map = new HashMap<>();
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),"更新身份认证策略");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&authStrategyDomain!=null&&secondAuthStrategyDomain!=null){
            map = authStrategyService.compileAuthStrategy(uuid,companyUuid, authStrategyDomain, secondAuthStrategyDomain);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getAuthStrategyMsg(1));

    }

    /**
     * 身份认证策略回显
     * @param
     * @return
     */
    @RequestMapping(value = "/identAuthEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> authStrategyEcho(@RequestParam(value = "uuid") String uuid){
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(uuid)){
            map = authStrategyService.echoAuthStrategy(uuid);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getAuthStrategyMsg(5));
    }
}
