package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.exceldomain.ApplicationAuditExcle;
import cipher.console.oidc.domain.web.ApplicationAuditInfo;
import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.TypeInfo;
import cipher.console.oidc.enums.ApplicationAuditEnum;
import cipher.console.oidc.service.ApplicationAuditInfoService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.FileUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static cipher.console.oidc.common.ReturnUtils.failureResponse;
import static cipher.console.oidc.common.ReturnUtils.successResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */
@Controller
@RequestMapping(value = "/cipher/applyAudit")
@EnableAutoConfiguration
public class ApplicationAuditController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuditController.class);

    @Autowired
    private ApplicationAuditInfoService applicationAuditInfoService;

     /*
     *
     * 应用审计
     * （数据隔离修改）
     * */
     @CheckToken
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(ApplicationInfo form, DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        if(StringUtils.isNotEmpty(form.getStartTime())){
            form.setStartTime(form.getStartTime()+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(form.getEndTime())){
            form.setEndTime(form.getEndTime()+" 23:59:59");
        }
        LOGGER.info("enter ApplicationAuditController.getList;parameters: " + form.toString());
        return applicationAuditInfoService.getApplicationPageList(form,pageModel);
    }

    /**
     * 取出应用访问次图示数据
     * @author cozi
     * @date 2019-07-16
     * @param days
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/chart",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getChartList(@RequestParam(value = "days")String days, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        List<String> daysc = JSON.parseArray(days, String.class);
         if(daysc!=null&&daysc.size()>0){
             return applicationAuditInfoService.getChartData(daysc,companyId);
         }
         return NewReturnUtils.failureResponse("数据查询失败");
    }

    /*
     *
     * 应用审计
     * （数据隔离修改）
     * */
    @CheckToken
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplicationInfo> getApplyList(@RequestParam(value = "applicationId") String applicationId,HttpServletRequest request) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        LOGGER.info("enter ApplicationAuditController.getApplyList");
        return applicationAuditInfoService.getApplyList(applicationId,companyId);
    }


    @RequestMapping(value = "/applylist")
    @ResponseBody
    public  Map<String,Object>  applypage(@RequestParam(value = "applicationId") String applicationId) {
        //FIXME:FIXED-诗昭
        Map<String,Object> map = new HashMap<>();
        ApplicationInfo domain=new ApplicationInfo();
        domain.setId(Integer.valueOf(applicationId));
        ApplicationInfo form=applicationAuditInfoService.getApplyInfo(domain);
        if (form == null){
            return failureResponse("应用不存在");
        }
        map.put("applicationId", applicationId);
        map.put("applicationName", form.getApplicationName());
        return map;
    }

    /*
     * 根据应用id获取审计列表
     * （数据隔离修改）
     * */
    @CheckToken
    @RequestMapping(value = "/getAuditInfo",params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getapplyInfo(ApplicationAuditInfo form,DataGridModel pageModel,HttpServletRequest request) {
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        LOGGER.info("enter ApplicationAuditController.getapplyInfo;parameters: " + form.toString());
        return applicationAuditInfoService.getApplicationAuditPageList(form,pageModel);
    }


    @RequestMapping(value = "/content", method = RequestMethod.POST)
    @ResponseBody
    public List<TypeInfo> getcontent() {
        LOGGER.info("enter ApplicationAuditController.getcontent; " );
        ApplicationAuditEnum[] values = ApplicationAuditEnum.values();
        List<TypeInfo> list=new ArrayList<>();
        for (ApplicationAuditEnum applicationAuditEnum : values) {
            TypeInfo typeInfo=new TypeInfo();
            typeInfo.setType(applicationAuditEnum.getType());
            typeInfo.setDesc(applicationAuditEnum.getDesc());
            list.add(typeInfo);
        }
        return list;
    }


    //导出报表
    @RequestMapping(value = "/exportExcle")
    public void AdminBehaviortExport(HttpServletRequest request,HttpServletResponse response,ApplicationAuditInfo form){
        LOGGER.info("enter ApplicationAuditController.AdminBehaviortExport; parameters"+form.toString() );
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        List<ApplicationAuditExcle> list=applicationAuditInfoService.exportExcle(form);
        FileUtil.exportExcel(list,"应用审计表","",ApplicationAuditExcle.class,"应用审计表.xls",response);
    }

}
