package cipher.console.oidc.controller.InformSettingsController;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.EmailInformDomain.CreatEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.EmailBasicDomain;
import cipher.console.oidc.domain.EmailInformDomain.RandomEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.SeedkeyEmailDomain;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.InformSettings.EmailInformService;
import cipher.console.oidc.service.SessionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/mail")
public class EmailInformController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private EmailInformService emailInformService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     * 邮件通知基本配置
     * @param emailBasicDomain 邮件通知基本配置实体
     */
    @RequestMapping(value = "/basicConfig",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> emailInformSettings(HttpServletRequest request, EmailBasicDomain emailBasicDomain, HttpSession session){

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新邮件通知基本配置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        if(emailBasicDomain!=null){
            String companyUuid = sessionService.getCompanyUUid(request.getSession());
            if(StringUtils.isNotEmpty(companyUuid)){
                emailBasicDomain.setCompanyUuid(companyUuid);
                return emailInformService.compileEmailInform(emailBasicDomain);
            }

        }
        return NewReturnUtils.failureResponse(ReturnMsg.getEmailMsg(1));
    }

    /**
     * 邮件通知基本配置回显
     */
    @RequestMapping(value = "/basicConfigEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> emailInformEcho(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            return emailInformService.echoEmailInform(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getEmailMsg(5));
    }

    /**
     * 邮件通知验证码配置测试
     * @param title 主题
     * @param writer 文案
     * @param emailAddr 邮箱地址
     */
    @RequestMapping(value = "/sendMsg",method =RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendMsg(HttpServletRequest request,
                                      @RequestParam("title") String title,
                                      @RequestParam("writer") String writer,
                                      @RequestParam("emailAddr") String emailAddr){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&StringUtils.isNotEmpty(title) &&StringUtils.isNotEmpty(writer)&&StringUtils.isNotEmpty(emailAddr)){
            return emailInformService.emailInformByCompanyUuid(companyUuid,title,writer,emailAddr);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.EMAILSENDFAILED);
    }

    /**
     * @param randomEmailDomain 邮件通知随机码配置实体
     */
    @RequestMapping(value = "/randomCode",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> randomEmailSettings(HttpServletRequest request, RandomEmailDomain randomEmailDomain,HttpSession session){
        if(randomEmailDomain!=null){
            String companyUuid = sessionService.getCompanyUUid(request.getSession());
            if(StringUtils.isNotEmpty(companyUuid)){
                randomEmailDomain.setCompanyUuid(companyUuid);
                return emailInformService.compileRandomEmail(randomEmailDomain);
            }
        }


        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新随机码邮件模版");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return NewReturnUtils.failureResponse(ReturnMsg.getRandEmailMsg(1));
    }

    /**
     * 邮件通知随机码配置回显
     */
    @RequestMapping(value = "/randomCodeEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> randomEmailEcho(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)) {
            return emailInformService.echoRandomEmail(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getRandEmailMsg(5));
    }

    /**
     *邮件通知开通账号配置
     * @param request
     * @param serviceState 服务状态
     * @param title 邮件主题
     * @param writer 邮件文案
     * @return
     */
    @RequestMapping(value = "/createAccount",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> creatEmailSettings(HttpServletRequest request,HttpSession session,
                                                 @RequestParam(value = "serviceState") Integer serviceState,
                                                 @RequestParam(value = "title") String title,
                                                 @RequestParam(value = "writer") String writer){
        if(StringUtils.isNotEmpty(title)&&StringUtils.isNotEmpty(writer)){
            String companyUuid = sessionService.getCompanyUUid(request.getSession());
            if(StringUtils.isNotEmpty(companyUuid)){
                CreatEmailDomain creatEmailDomain = new CreatEmailDomain();
                creatEmailDomain.setCompanyUuid(companyUuid);
                creatEmailDomain.setServiceState(serviceState);
                creatEmailDomain.setTitle(title);
                creatEmailDomain.setWriter(writer);
                return emailInformService.compileCreatEmail(creatEmailDomain);

            }
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新开通账号通知邮件模版");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return NewReturnUtils.failureResponse(ReturnMsg.getAcEmailMsg(1));
    }

    /**
     * 邮件通知开通账号配置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/createAccountEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> creatEmailEcho(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)) {
            return emailInformService.echoCreatEmail(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getAcEmailMsg(5));
    }

    /**
     * 下发种子密钥
     * @param request
     * @param title 邮件主题
     * @param writer 邮件内容
     * @return
     */
    @RequestMapping(value = "/seedkey",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> seedkeySettings(HttpServletRequest request,
                                              @RequestParam(value = "title") String title,
                                              @RequestParam(value = "writer") String writer){
        if(StringUtils.isNotEmpty(title)&&StringUtils.isNotEmpty(writer)){
            String companyUuid = sessionService.getCompanyUUid(request.getSession());
            if(StringUtils.isNotEmpty(companyUuid)){
                SeedkeyEmailDomain seedkeyEmailDomain = new SeedkeyEmailDomain();
                seedkeyEmailDomain.setCompanyUuid(companyUuid);
                seedkeyEmailDomain.setTitle(title);
                seedkeyEmailDomain.setWriter(writer);
                return emailInformService.compileSeedkeyEmail(seedkeyEmailDomain);
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getIssueSeedkey(1));
    }

    /**
     * 下发种子密钥配置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/seedkeyEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> seedkeyEcho(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)) {
            return emailInformService.echoSeedkeyEmail(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getIssueSeedkey(5));
    }

}
