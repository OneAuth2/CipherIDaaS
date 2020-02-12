package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import cipher.console.oidc.service.AppService;
import cipher.console.oidc.service.DynamicStateService;
import cipher.console.oidc.service.SessionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * otp管理
 * @author cozi
 * @date 2019-08-30
 */
@Controller
@RequestMapping(value = "/cipher/dynamic")
public class DynamicStateController {

    @Autowired
    private AppService appService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private DynamicStateService dynamicStateService;

    /**
     *获取otp管理列表
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getDynamicStateList(
            @RequestParam(value = "queryName",required = false) String queryName,
            @RequestParam(value = "issueStatus",defaultValue = "0") Integer issueStatus,
            DataGridModel pageModel,
            HttpServletRequest request,
            HttpServletResponse response){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        return dynamicStateService.getDynamicStateList(queryName,issueStatus,companyId,pageModel);
    }

    /**
     * 下发种子密钥
     * @param userId 用户id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/issue",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> issueSeedKey(String userId,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        return dynamicStateService.issueSeedKey(userId,companyId);
    }

    /**
     * 重置种子密钥
     * @param userId 用户id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> resetSeedkey(String userId,
                                           HttpServletRequest request,
                                           HttpServletResponse response){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        return dynamicStateService.resetSeedkey(userId,companyId);

    }


    /**
     * 获取组织结构树
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getUserTree(HttpServletRequest request,HttpServletResponse response){
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        Map<String,Object> map=new HashMap<>();

        List<String> selectedUserId = new ArrayList<>();
        if(StringUtils.isNotEmpty(companyId)){
            selectedUserId = dynamicStateService.getSelectedUserId(companyId);
        }

        List<TreeNodesDomain> trees= appService.getUserTree(selectedUserId,companyId);
        map = NewReturnUtils.successResponse(ReturnMsg.DYNAMICSTATELISTORG);
        map.put("trees",trees);
        return map;
    }


    /**
     * 批量下发种子密钥
     * @param userIds 批量下发的用户id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/batch",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> batchIssue(String userIds,HttpServletRequest request,HttpServletResponse response){
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        if(StringUtils.isNotEmpty(userIds)&&StringUtils.isNotEmpty(companyId)){
            return dynamicStateService.batchIssueSeedkey(userIds,companyId);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.DYNAMICSTATELISTBATCHFAILED);
    }


}
