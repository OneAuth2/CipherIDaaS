package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.AdminBehaviorExcle;
import cipher.console.oidc.domain.exceldomain.NewAdminBehaviorExcle;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.TypeInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/6/4.
 */
@Controller
@RequestMapping(value = "/cipher/adminbehavior")
@EnableAutoConfiguration
public class AdminBehaviorController {

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminBehaviorController.class);


     /*
     * 管理员审计
     * （数据隔离修改）
     * */
    // @CheckToken
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(AdminBehaviorInfo form, DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("enter AdminBehaviorController.getList;parameters: " + form.toString());
       String companyId= ConstantsCMP.getSessionCompanyId(request);
       form.setCompanyId(companyId);
        if(StringUtils.isNotEmpty(form.getStartTime())) {
            String startTime = form.getStartTime() + " 00:00:00";
            form.setStartTime(startTime);
        }
        if(StringUtils.isNotEmpty(form.getEndTime())) {
            String endTime = form.getEndTime() + " 23:59:59";
            form.setEndTime(endTime);
        }

        return adminBehaviorInfoService.getAdminBehaviorPageList(form,pageModel);
    }


    @RequestMapping(value = "/content", method = RequestMethod.POST)
    @ResponseBody
    public List<TypeInfo> getcontent() {
        LOGGER.info("enter AdminBehaviorController.getcontent;parameters: ");
        //System.out.println("hello ========>");
        AdminBehaviorEnum[] values = AdminBehaviorEnum.values();
        List<TypeInfo> list=new ArrayList<>();
        for (AdminBehaviorEnum adminBehaviorEnum : values) {
            TypeInfo typeInfo=new TypeInfo();
            typeInfo.setType(adminBehaviorEnum.getType());
            typeInfo.setDesc(adminBehaviorEnum.getDesc());
          //  System.out.println("hello ========>"+adminBehaviorEnum.getType());
            list.add(typeInfo);
        }
        return list;
    }

    /*
    * 导出报表
    * （数据隔离修改）
    * */
    @RequestMapping(value = "/exportExcle")
    public void AdminBehaviortExport(HttpServletResponse response,AdminBehaviorInfo form,HttpServletRequest request){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        if(StringUtils.isNotEmpty(form.getStartTime())) {
            String startTime = form.getStartTime() + " 00:00:00";
            form.setStartTime(startTime);
        }
        if(StringUtils.isNotEmpty(form.getEndTime())) {
            String endTime = form.getEndTime() + " 23:59:59";
            form.setEndTime(endTime);
        }

        LOGGER.info("enter AdminBehaviorController.AdminBehaviortExport;parameters: " + form.toString());
        List<NewAdminBehaviorExcle> list=adminBehaviorInfoService.exportExcle(form);
        for(NewAdminBehaviorExcle newAdminBehaviorExcle:list){
              newAdminBehaviorExcle.setTypeStr(AdminBehaviorEnum.getAdminBehaviorEnum(Integer.valueOf(newAdminBehaviorExcle.getType())));
        }

        FileUtil.exportExcel(list,"管理员审计表","", NewAdminBehaviorExcle.class,"管理员审计表.xls",response);
    }





}
