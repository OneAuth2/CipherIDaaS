package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.ExceptionLoginDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.ExceptionLoginService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

@Controller
@RequestMapping("/cipher/exceptionLogin")
public class ExceptionLoginController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionLoginController.class);

    @Autowired
    private ExceptionLoginService exceptionLoginService;
    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;


    /*
    * 异常登录信息
    * (数据隔离修改)
    * */
    @CheckToken
    @RequestMapping(value = "/preList",method = RequestMethod.POST )
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest request){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        ExceptionLoginDomain exceptionLoginDomain=exceptionLoginService.queryExceptionLoginObj(companyId);
        return successResponse("exceptionLogin",exceptionLoginDomain);
    }

    /*
     * 修改异常登录信息
     * (数据隔离修改)
     * */
    @CheckToken
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletResponse response, HttpServletRequest request, HttpSession session,ExceptionLoginDomain exceptionLoginDomain){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        exceptionLoginDomain.setCompanyId(companyId);
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新异常登录信息");
        adminBehaviorInfoService.insertSelective(record,session);
        if(StringUtils.isEmpty(String.valueOf(exceptionLoginDomain.getId()))){
            exceptionLoginService.insertExceptionLogin(exceptionLoginDomain);
            ResponseUtils.customSuccessResponse(response, "保存成功！");
        }else{
            exceptionLoginService.updateExceptionLogin(exceptionLoginDomain);
            ResponseUtils.customSuccessResponse(response, "修改成功！");
        }
    }



}
