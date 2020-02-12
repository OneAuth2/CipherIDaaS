package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.UserBehaviorExcle;
import cipher.console.oidc.domain.web.TypeInfo;
import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.enums.UserBehaviorEnum;
import cipher.console.oidc.service.UserBehaviorInfoService;
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
@RequestMapping(value = "/cipher/userbehavior")
@EnableAutoConfiguration
public class UserBehaviorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserBehaviorController.class);


    @Autowired
    private UserBehaviorInfoService userBehaviorInfoService;


    /*
    * 用户审计列表
    * （数据隔离修改）
    *
    * */
    @CheckToken
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(UserBehaviorInfo form, DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        LOGGER.info("enter UserBehaviorController.getList;parameters: " + form.toString());
        if(StringUtils.isNotEmpty(form.getStartTime())) {
            String startTime = form.getStartTime() + " 00:00:00";
            form.setStartTime(startTime);
        }
        if(StringUtils.isNotEmpty(form.getEndTime())) {
            String endTime = form.getEndTime() + " 23:59:59";
            form.setEndTime(endTime);
        }
        return userBehaviorInfoService.getUserBehaviorPageList(form,pageModel);
    }

    @RequestMapping(value = "/content", method = RequestMethod.POST)
    @ResponseBody
    public List<TypeInfo> getcontent() {
        LOGGER.info("enter UserBehaviorController.getcontent;");
        UserBehaviorEnum[] values = UserBehaviorEnum.values();
        List<TypeInfo> list=new ArrayList<>();
        for (UserBehaviorEnum userBehaviorEnum : values) {
            TypeInfo typeInfo=new TypeInfo();
            typeInfo.setType(userBehaviorEnum.getType());
            typeInfo.setDesc(userBehaviorEnum.getDesc());
            list.add(typeInfo);
        }
        return list;
    }



    /*
    * 导出报表
    * （数据隔离修改）
    * */
    @RequestMapping(value = "/exportExcle")
    public void AdminBehaviortExport(HttpServletResponse response,UserBehaviorInfo form,HttpServletRequest request){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        LOGGER.info("enter UserBehaviorController.getcontent;parameters"+form.toString());
        List<UserBehaviorExcle> list=userBehaviorInfoService.exportExcle(form);
        FileUtil.exportExcel(list,"用户审计表","",UserBehaviorExcle.class,"用户审计表.xls",response);
    }







}
