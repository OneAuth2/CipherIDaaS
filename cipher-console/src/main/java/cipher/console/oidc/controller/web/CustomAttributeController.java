package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.CustomAttributeInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.CustomAttributeService;
import cipher.console.oidc.service.SessionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creare by cozi
 * date 2019-06-12
 */
@Controller
@RequestMapping(value = "/cipher/attr")
public class CustomAttributeController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private CustomAttributeService customAttributeService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     * 自定义属性添加
     * @param attributeName 属性名
     * @param attributeDescription 属性描述
     * @param request
     * @return
     */
    @RequestMapping(value = "/adding",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> addAttribute(@RequestParam(value = "attributeName") String attributeName,
                                           @RequestParam(value = "attributeDescription") String attributeDescription,
                                           HttpServletRequest request, HttpSession session){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"添加自定义属性");
        adminBehaviorInfoService.insertSelective(record,session);
        if(StringUtils.isNotEmpty(attributeName)){
            return customAttributeService.addUserAttribute(companyId, attributeName, attributeDescription);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.ADDATTRIBUTEFAILED);
    }

    /**
     * 自定义属性编辑
     * @param uuid 属性id
     * @param attributeName 属性名
     * @param attributeDescription 属性描述
     * @param request
     * @return
     */
    @RequestMapping(value = "/edited",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> editAttribute(@RequestParam(value = "uuid") String uuid,
                                            @RequestParam(value = "attributeName") String attributeName,
                                            @RequestParam(value = "attributeDescription") String attributeDescription,
                                            HttpServletRequest request,HttpSession session){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"更新自定义属性");
        adminBehaviorInfoService.insertSelective(record,session);
        if(StringUtils.isNotEmpty(uuid)&&StringUtils.isNotEmpty(attributeName)){
            return customAttributeService.editUserAttribute(uuid,companyId,attributeName,attributeDescription);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.EDITATTRIBUTEFAILED);
    }

    /**
     * 自定义属性删除
     * @param uuid 属性id
     * @param request
     * @return
     */
    @RequestMapping(value = "/del",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> delAttribute(@RequestParam(value = "uuid") String uuid,
                                           HttpServletRequest request,HttpSession session){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"删除自定义属性");
        adminBehaviorInfoService.insertSelective(record,session);
        if(StringUtils.isNotEmpty(uuid)){
            try {
                customAttributeService.delUserAttribute(uuid,companyId);
            } catch (Exception e) {
                e.printStackTrace();
                //删除失败进行回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return NewReturnUtils.failureResponse(ReturnMsg.DELATTRIBUTEFAILED);
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.DELATTRIBUTESUCCESS);
    }

    /**
     * 自定义属性列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> attributeList(HttpServletRequest request){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        Map<String,Object> map = new HashMap<>();
        List<CustomAttributeInfo> attributeList = customAttributeService.getAttributeList(companyId);
        if(attributeList!=null){
            map=NewReturnUtils.successResponse(ReturnMsg.ATTRIBUTELISTSUCCESS);
            map.put("attribute",attributeList);
        }else {
            map=NewReturnUtils.failureResponse(ReturnMsg.ATTRIBUTELISTFAILED);
            map.put("attribute","");
        }
        return map;
    }

}
