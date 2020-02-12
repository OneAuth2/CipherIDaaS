package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.IconSettingsService;
import cipher.console.oidc.service.SessionService;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping(value = "/cipher/setting")
public class IconSettingsController {

    @Autowired
    private IconSettingsService iconSettingsService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     *门户登录页面设置
     * @param request
     * @param title 页面标题文案
     * @param leftTitle 左上角标题文案
     * @param rightTitle 认证窗体文案
     * @param iconfont 左上角logo路径
     * @param logo 中间logo路径
     * @return
     */
    @RequestMapping(value = "/doorPage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> doorPageSettings(HttpServletRequest request,
                                               @RequestParam(value = "title",required = false) String title,
                                               @RequestParam(value = "leftTitle",required = false) String leftTitle,
                                               @RequestParam(value = "rightTitle",required = false) String rightTitle,
                                               @RequestParam(value = "iconfont",required = false) String iconfont,
                                               @RequestParam(value = "logo",required = false) String logo,HttpSession session){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新门户登录页面设置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return iconSettingsService.compileDoorPage(companyUuid,title,leftTitle,rightTitle,iconfont,logo);
    }

    /**
     * 门户登录页面设置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/doorPageEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> echoDoorPage(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            return iconSettingsService.echoDoorPage(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getDoorPageMsg(5));
    }

    /**
     * portal首页配置
     * @param request
     * @param title 页面标题文案
     * @param iconfont 左上角logo路径
     * @return
     */
    @RequestMapping(value = "/applicationPage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> applicationPageSettings(HttpServletRequest request,
                                                      @RequestParam(value = "title") String title,
                                                      @RequestParam(value = "iconfont") String iconfont,HttpSession session){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新portal首页配置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return iconSettingsService.compileApplicationPage(companyUuid,title,iconfont);
    }

    /**
     * portal首页配置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/applicationPageEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> echoApplicationPage(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            return iconSettingsService.echoApplicationPage(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getApplicationPageMsg(5));
    }

    /**
     * 管理后台登录页配置
     * @param request
     * @param title  页面标题文案
     * @param leftTitle 左上角标题文案
     * @param rightTitle  认证窗体文案
     * @param iconfont  左上角logo路径
     * @return
     */
    @RequestMapping(value = "/managePage",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> managePageSettings(HttpServletRequest request,
                                                 @RequestParam(value = "title",required = false) String title,
                                                 @RequestParam(value = "leftTitle",required = false) String leftTitle,
                                                 @RequestParam(value = "rightTitle",required = false) String rightTitle,
                                                 @RequestParam(value = "iconfont",required = false) String iconfont, HttpSession session){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新管理后台登录页配置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return iconSettingsService.compileManagePage(companyUuid,title,leftTitle,rightTitle,iconfont);
    }

    /**
     * 管理后台登录页配置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/managePageEcho",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> echoManagePage(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            return iconSettingsService.echoManagePage(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getManagePageMsg(5));
    }

    /**
     * 页面标题LOGO配置
     * @param request
     * @param title 标题
     * @param iconfont 图片地址
     * @param session
     * @return
     */
    @RequestMapping(value = "/titleTag",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> titleTag(HttpServletRequest request,
                                       @RequestParam(value = "title") String title,
                                       @RequestParam(value = "iconfont") String iconfont,
                                       HttpSession session){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&StringUtils.isNotEmpty(title)&&StringUtils.isNotEmpty(iconfont)){
            return iconSettingsService.compileTitleTag(companyUuid,title,iconfont);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getTitleTag(1));
    }

    /**
     * 页面标题LOGO配置回显
     * @param request
     * @return
     */
    @RequestMapping(value = "/titleTagEcho")
    @ResponseBody
    public Map<String,Object> echoTitleTag(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)){
            return iconSettingsService.echoTitleTag(companyUuid);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getTitleTag(5));
    }

}
