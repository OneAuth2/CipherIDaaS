package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.RegisterApprovalService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.UserService;
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
@RequestMapping(value = "/cipher/register")
public class RegisterApprovalController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private RegisterApprovalService registerApprovalService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /**
     * 账号注册审批列表
     * @param request
     * @param pageModel 分页规则
     * @return
     */
    @RequestMapping(value = "/registerApproval",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> approvalList(HttpServletRequest request, DataGridModel pageModel){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&pageModel!=null){
            return registerApprovalService.getApprovalList(companyUuid, pageModel);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getApprovalListMsg(5));
    }

    /**
     * 账号注册审批记录
     * @param request
     * @param pageModel 分页规则
     * @return
     */
    @RequestMapping(value = "/approvalRecords",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> recordsList(HttpServletRequest request, DataGridModel pageModel){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(companyUuid)&&pageModel!=null){
            return registerApprovalService.getRecordsList(companyUuid,pageModel);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getRecordsListMsg(5));
    }

    /**
     * 账号注册审批
     * @param request
     * @param uuid
     * @param status
     * @return
     */
    @RequestMapping(value = "/accountApproval",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> accountApproval(HttpServletRequest request, @RequestParam(value = "uuid") String uuid, @RequestParam(value = "status") Integer status,HttpSession session){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),  "更新审批信息");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        if(StringUtils.isNotEmpty(companyUuid)&&StringUtils.isNotEmpty(uuid)&&status>0){
            return registerApprovalService.getApprovalResult(companyUuid,uuid,status);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.APPROVALFAILED);
    }


}
