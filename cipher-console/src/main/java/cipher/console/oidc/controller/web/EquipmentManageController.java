package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.domain.web.VpnConfigurationDomain;
import cipher.console.oidc.service.EquipmentManageService;
import cipher.console.oidc.service.SessionService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/equip")
public class EquipmentManageController {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private EquipmentManageService equipmentManageService;

    /**
     * 获取vpn设备信息列表
     * @author cozi
     * @date 2019-07-05
     * @param request
     * @param response
     * @return
     **/
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getVpnConfigList(HttpServletRequest request,
                                               VpnConfigurationDomain vpnConfigurationDomain,
                                               DataGridModel pagemodel,
                                               HttpServletResponse response){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyId)){
            vpnConfigurationDomain.setPageModel(pagemodel);
            List<VpnConfigurationDomain> vpnConfigList = equipmentManageService.getVpnConfigList(companyId,vpnConfigurationDomain);
            int vpnConfigCount = equipmentManageService.getVpnConfigCount(companyId);
            map = NewReturnUtils.successResponse(ReturnMsg.getVpnConfigListMsg(4));
            map.put("rows",vpnConfigList);
            map.put("total",vpnConfigCount);
            return map;
        }
        map = NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(5));
        map.put("return_result","");
        return map;
    }

    /**
     * 添加或者修改vpn设备信息
     * @param vpnConfigurationDomain
     * @param request
     * @return
     **/
    @RequestMapping(value = "/compile",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> modifyVpnConfig(VpnConfigurationDomain vpnConfigurationDomain, HttpServletRequest request){
        UserInfoDomain userInfoDomain = (UserInfoDomain) request.getSession().getAttribute(ConstantsCMP.CIPHER_CONSOLE_USER_SESSION_INFO);
        if(vpnConfigurationDomain!=null){
            String companyId = sessionService.getCompanyUUid(request.getSession());
            vpnConfigurationDomain.setCompanyId(companyId);
            return equipmentManageService.modifyVpnConfig(vpnConfigurationDomain,userInfoDomain);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(2));
    }

    @RequestMapping(value = "/details")
    @ResponseBody
    public Map<String,Object> vpnConfigDetails(HttpServletRequest request,
                                               @RequestParam(value = "uuid") String uuid){
        String companyId = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(uuid)&&StringUtils.isNotEmpty(companyId)){
            return equipmentManageService.vpnConfigByUuid(uuid,companyId);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getVpnConfigListMsg(5));
    }

    /**
     * vpn设备信息删除
     * @param request
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Map<String,Object> vpnConfigDelete(HttpServletRequest request,
                                               @RequestParam(value = "uuid") String uuid){
        UserInfoDomain userInfoDomain = (UserInfoDomain) request.getSession().getAttribute(ConstantsCMP.CIPHER_CONSOLE_USER_SESSION_INFO);
        String companyId = sessionService.getCompanyUUid(request.getSession());
        if(StringUtils.isNotEmpty(uuid)&&StringUtils.isNotEmpty(companyId)){
            return equipmentManageService.vpnConfigDel(uuid,companyId,userInfoDomain);
        }
        return NewReturnUtils.failureResponse("vpn设备信息删除失败");
    }

}
