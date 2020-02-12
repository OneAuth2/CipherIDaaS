package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.EquipBehaviorExcle;
import cipher.console.oidc.domain.web.EquipBehaviorDomain;
import cipher.console.oidc.domain.web.EquipBehaviorInfo;
import cipher.console.oidc.service.EquipBehaviorService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author cozi
 * @date 2019-08-24
 * 设备审计
 */

@Controller
@RequestMapping("/cipher/equipBehavior")
public class EquipBehaviorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EquipBehaviorController.class);

    @Autowired
    private EquipBehaviorService equipBehaviorService;

    /**
     * 获取设备审计列表
     * @param equipBehaviorDomain 查询条件
     * @param pageModel 分页信息
     * @param request
     * @param response
     * @return
     */
    @CheckToken
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(EquipBehaviorDomain equipBehaviorDomain, DataGridModel pageModel,
                                       HttpServletRequest request, HttpServletResponse response){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        equipBehaviorDomain.setCompanyId(companyId);
        return equipBehaviorService.getEquipPageList(equipBehaviorDomain,pageModel);
    }

    /**
     *通过vpnid查出详情信息列表
     * @param equipBehaviorInfo 查询条件
     * @param pageModel 分页信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logList",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getEquipLogList(EquipBehaviorInfo equipBehaviorInfo,DataGridModel pageModel,
                                               HttpServletRequest request, HttpServletResponse response){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        equipBehaviorInfo.setCompanyId(companyId);
        return equipBehaviorService.getEquipLogList(equipBehaviorInfo,pageModel);
    }

    /*
     * 导出报表
     * （数据隔离修改）
     * */
    @RequestMapping(value = "/exportExcle")
    public void AdminBehaviortExport(HttpServletResponse response, EquipBehaviorInfo equipBehaviorInfo, HttpServletRequest request){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        equipBehaviorInfo.setCompanyId(companyId);
        LOGGER.info("enter UserBehaviorController.getcontent;parameters"+equipBehaviorInfo.toString());
        List<EquipBehaviorExcle> list=equipBehaviorService.getEquipBehaviorExcle(equipBehaviorInfo);
        FileUtil.exportExcel(list,"设备审计","",EquipBehaviorExcle.class,"设备审计表.xls",response);
    }

}
