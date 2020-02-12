package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.exceldomain.AdminBehaviorExcle;
import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.exceldomain.WifiVistorExcle;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.VistorLoginLogInfoMapper;
import cipher.console.oidc.model.MemberLogoutModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.FileUtil;
import cipher.console.oidc.util.HttpRequest;
import cipher.console.oidc.util.ResponseUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/25.
 */

@Controller
@RequestMapping(value = "/cipher/vistor")
@EnableAutoConfiguration
public class VistorInfoController {

    private static final Logger logger = LoggerFactory.getLogger(VistorInfoController.class.getSimpleName());

    @Autowired
    private VistorInfoService vistorInfoService;

    @Autowired
    private GoldMantisService goldMantisService;

    @Autowired
    private OnlineVisitorService onlineVisitorService;

    @Autowired
    private VistorLoginLogInfoMapper vistorLoginLogInfoMapper;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private SessionService sessionService;


   /*
   * 获取访客信息列表
   * （数据隔离修改）
   * */
    @CheckToken
    @RequestMapping(value = "/list", params = "json")
    @ResponseBody
    public Map<String, Object> queryData(HttpServletRequest request, DataGridModel pageModel, GoldMantisUser form) {
        logger.debug("Enter VistorInfoController.queryData");
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        return vistorInfoService.getVistorList(form,pageModel);
    }



    @RequestMapping(value = "/echarts")
    @ResponseBody
    public Map<String,Object> echarts(String date1,String date2,HttpServletRequest request) throws ParseException {
        String companyId = sessionService.getCompanyUUid(request.getSession());
//        for (int i=0;i<30;i++){
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_MONTH, -i);
//            Date date = calendar.getTime();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            String s = sdf.format(date);
//            String date5 = s + " 00:00:00";
//            vistorLoginLogInfoMapper.insertDemo(date5);
//        }
        return vistorInfoService.getEcharts(date1,date2,companyId);
    }




    @RequestMapping(value = "/echartsYear")
    @ResponseBody
    public Map<String,Object> echartsYear(String data1,String data2,HttpServletRequest request) throws ParseException {
        String companyId = sessionService.getCompanyUUid(request.getSession());
        return vistorInfoService.echartsYear(data1,data2,companyId);
    }

    @RequestMapping(value = "/chartData")
    @ResponseBody
    public Map<String,Object> getChartsData(@RequestParam(value = "dimension",defaultValue = "0") Integer dimension,
                                            @RequestParam(value = "granularity",defaultValue = "0")Integer granularity,
                                            @RequestParam(value = "startTime")String startTime,
                                            @RequestParam(value = "endTime")String endTime,
                                            HttpServletRequest request,
                                            HttpServletResponse response){
        if(StringUtils.isEmpty(dimension.toString())){
            return NewReturnUtils.failureResponse("统计维度不能为空！");
        }
        if(StringUtils.isEmpty(granularity.toString())){
            return NewReturnUtils.failureResponse("时间粒度不能为空！");
        }
        String companyId = sessionService.getCompanyUUid(request.getSession());
        return vistorInfoService.getChartsData(dimension,granularity,startTime,endTime,companyId);
    }



    @RequestMapping(value = "/online/list", params = "json")
    @ResponseBody
    public Map<String, Object> onlineMember(HttpServletResponse response, DataGridModel pageModel, GoldMantisUser form) {
        logger.debug("Enter VistorInfoController.onlineMember");
        Map<String,Object> map = new HashMap<>();
        map.put("rows",onlineVisitorService.selectAllOnlineMember(pageModel));
        map.put("total",onlineVisitorService.selectOnlineMemberAccount());
        return map;
    }



    @RequestMapping(value = "/logout", params = "json")
    @ResponseBody
    @Deprecated
    public Map<String, Object> onlineMemberLogout(HttpServletResponse response,
                                                  @RequestParam(value = "username",required = true)String username,
                                                  @RequestParam(value = "ip",required = true)String ip,
                                                  @RequestParam(value = "mac",required = true)String mac) {
        username = username.replace("/","");
        ip = ip.replace("/","");
        Map<String, Object> map = new HashMap<>();
        map.put("username",username);
        map.put("ip",ip);
        String logout = HttpRequest.post("http://192.168.1.190:9633/console/logout").form(map).body();
        MemberLogoutModel memberLogoutModel = JSON.parseObject(logout,new TypeReference<MemberLogoutModel>() {});
        map.put("errorCode",memberLogoutModel.getErrorCode()+"");
        map.put("errorMessage",memberLogoutModel.getErrorMessage());
        System.out.println(map);
        return map;
    }


    /*
    * 添加访客信息
    * （数据隔离修改）
    *
    * */
   // @CheckToken
    @RequestMapping(value = "/add", method = RequestMethod.POST )
    @ResponseBody
    public void add(HttpServletResponse response, DataGridModel pageModel, GoldMantisUser form, HttpServletRequest request, HttpSession session) throws Exception {
        logger.debug("Enter VistorInfoController.add");
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "添加访客:"+form.getLoginName());
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        String userName= ConstantsCMP.getSessionUser(request);
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setAdminLoginName(userName);
        form.setCompanyId(companyId);
        form.setPassword(form.getPassword().trim());
        GoldMantisUser mobileAExist = goldMantisService.queryGoldMantisUserByMobile(form);
        try {
            if(StringUtils.isNotEmpty(form.getStartTime())) {
                String startTime = form.getStartTime();
                form.setStartTime(startTime);
            }
            if(StringUtils.isNotEmpty(form.getEndTime())) {
                String endTime = form.getEndTime();
                form.setEndTime(endTime);
            }
            if (mobileAExist == null) {
                vistorInfoService.insertVistorUser(form);
            }else {
                form.setId(mobileAExist.getId());
                vistorInfoService.updateVistorUser(form);
            }
            ResponseUtils.customSuccessResponse(response, "操作成功！");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customSuccessResponse(response, "服务器错误！");
        }
    }





    /*
     * 获取在线访客列表
     *
     * v1.5.6
     *
     * */
    @RequestMapping(value = "/getlist",  method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> queryData(HttpServletRequest request, DataGridModel pageModel, OnlineVisitor form) {
        logger.debug("Enter VistorInfoController.queryData");
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        return vistorInfoService.getOnlineVistorList(form,pageModel);
    }




    /*
     * 导出无线审计报表
     *
     * v1.5.6
     *
     * */
    @RequestMapping(value = "/export",  method = RequestMethod.POST )
    @ResponseBody
    public void export(HttpServletRequest request, HttpServletResponse response,DataGridModel pageModel, OnlineVisitor form,HttpSession session) {
        logger.debug("Enter VistorInfoController.export");
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        List<WifiVistorExcle> list=vistorInfoService.export(form);
        FileUtil.exportExcel(list,"无线审计表","",WifiVistorExcle.class,"无线审计表.xls",response);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "导出无线审计报表");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
    }






}
