package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.controller.BaseController;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.*;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.WifiStrategyInfoService;
import cipher.console.oidc.token.CheckToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shizhao on 2018/4/12.
 * 无线WIFI的策略业务
 */
@Controller
@RequestMapping(value = "/cipher/wifi/strategy")
@EnableAutoConfiguration
public class WifiAuthController extends BaseController{

    @Autowired
    private WifiStrategyInfoService wifiStrategyInfoService;

    private static final Logger logger = LoggerFactory.getLogger(WifiAuthController.class);

    @Autowired
    private SessionService sessionService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;


    /**
     * 获取员工策略
     * (数据隔离修改)
     * */
    @CheckToken
    @PostMapping(value = "/staff")
    @ResponseBody
    public Map<String,Object> getStaffStrategy(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        logger.info("Enter WifiAuthController.getStaffStrategy()");
        Map<String,Object> map =new HashMap<>();
        StaffInfoDomain staffInfo = wifiStrategyInfoService.getStaffStrategy(companyUuid);
        //数据库中没有员工策略
        if (staffInfo == null){
            staffInfo = new StaffInfoDomain();
            wifiStrategyInfoService.insertStrategy(new WifiStaffStrategy(staffInfo,companyUuid));
        }
        Integer id = wifiStrategyInfoService.getStrategyId(companyUuid,"staff");
        map.put("id", id);
        map.put("strategy", staffInfo);
        logger.info("The Strategy = [{}]",staffInfo);
        return sendBaseNormalMap(map);
    }

    /**
     * 获取访客策略
     * （数据隔离修改）
     * */
    @CheckToken
    @PostMapping(value = "/visitor")
    @ResponseBody
    public Map<String,Object> getVisitorStrategy(HttpServletRequest request){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        logger.info("Enter WifiAuthController.getVisitorStrategy()");
        Map<String,Object> map =new HashMap<>();
        VisitorInfoDomain visitorInfo = wifiStrategyInfoService.getVisitorStrategy(companyUuid);

        if (visitorInfo == null){
            visitorInfo = new VisitorInfoDomain();
            wifiStrategyInfoService.insertStrategy(new WifiVisitorStrategy(visitorInfo,companyUuid));
        }
        Integer id = wifiStrategyInfoService.getStrategyId(companyUuid,"visitor");
        map.put("id", id);
        map.put("strategy", visitorInfo);
        logger.info("The Strategy = [{}]",visitorInfo);
        return sendBaseNormalMap(map);
    }

    /**
     * 获取MAC绑定信息
     * （数据隔离修改）
     *
     * */
    @CheckToken
    @PostMapping(value = "/mac")
    @ResponseBody
    public Map<String,Object> getDeviceCount(HttpServletRequest request){
        logger.info("Enter WifiAuthController.getDeviceCount()");
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        MacDeviceBinding macDeviceBinding = wifiStrategyInfoService.getMacDeviceBinding(companyId);
        if (macDeviceBinding == null){
            macDeviceBinding = new MacDeviceBinding();
            macDeviceBinding.setCompanyId(companyId);
            wifiStrategyInfoService.insertStrategy(new WifiMacDeviceBindingStrategy(macDeviceBinding));
        }
        Map<String,Object> map = new HashMap<>();
        map.put("strategy",macDeviceBinding);
        logger.info("The Strategy = [{}]",macDeviceBinding);
        return sendBaseNormalMap(map);
    }

    /**
     * 更新员工策略
     * （数据隔离修改）
     * */
    @CheckToken
    @PostMapping(value = "/staff/update")
    @ResponseBody
    public Map<String,Object> updateStaffStrategy(HttpServletRequest request, HttpSession session,
                                                  @RequestParam(value = "authType",required = true,defaultValue = "0")Integer authType,
                                                  @RequestParam(value = "sourceType",required = true,defaultValue = "0")Integer sourceType,
                                                  @RequestParam(value = "macFactor",required = true,defaultValue = "0")Integer macFactor,
                                                  @RequestParam(value = "timeUnits",required = true,defaultValue = "0")Integer timeUnits,
                                                  @RequestParam(value = "timeRanges",required = true,defaultValue = "0")Integer timeRanges){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        StaffInfoDomain staffInfoDomain = new StaffInfoDomain(authType,sourceType,macFactor,timeUnits,timeRanges);
        logger.info("Enter WifiAuthController.updateStaffStrategy()");
        //员工策略为空
        if (staffInfoDomain == null){
            logger.info("Update Staff Strategy But The staffInfoDomain=[null]");

            return sendBaseErrorMap(ResultCode.STRATEGY_IS_FRAGMENTARY);
        }
        StaffAuthType staffAuthType = StaffAuthType.getAuthType(staffInfoDomain.getAuthType());
        //不存在的认证类型
        if (staffAuthType == null){
            logger.info("Update Staff Strategy But The staffAuthType=[{}] ,It Can't Be Accepted",staffInfoDomain.getAuthType());

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_AUTH_TYPE_CAN_NOT_BE_ACCEPTED);
        }

        //认证源异常
        if (StaffAuthSource.getAuthSource(staffInfoDomain.getSourceType()) == null){
            logger.info("Update Staff Strategy But The sourceType=[{}] ,It Can't Be Accepted",staffInfoDomain.getSourceType());

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_AUTH_SOURCE_CAN_NOT_BE_ACCEPTED);
        }

        //MAC认证状态开启异常
        if (staffInfoDomain.getMacFactor() <0 || staffInfoDomain.getMacFactor()>1){
            logger.info("Update Staff Strategy But The Mac Auth Error");

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_MAC_STATUS_ERROR);
        }

        //时间单位异常
        if (staffInfoDomain.getTimeUnits() < 0 || staffInfoDomain.getTimeUnits() >1){
            logger.info("Update Staff Strategy But The Time Utils Error");

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_TIME_UTIL_ERROR);
        }

        //时间长度为负
        if (staffInfoDomain.getTimeRanges()<0){
            logger.info("Update Staff Strategy But The Time Range Error");

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_TIME_RANGE_ERROR);
        }

        //插入数据库失败
        if (!wifiStrategyInfoService.insertStrategy(new WifiStaffStrategy(staffInfoDomain,companyUuid))){
            logger.info("Update Staff Strategy But The DataBase Catch An Exception");

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_UPDATE_FAILED);
        }

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "更新无线员工策略");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        return sendBaseNormalMap();
    }

    /**
     * 更新访客策略
     * （数据隔离修改）
     * */
    @CheckToken
    @PostMapping(value = "/visitor/update")
    @ResponseBody
    public Map<String,Object> updateVisitorStrategy(HttpServletRequest request,HttpSession session,
                                                    @RequestParam(value = "sourceType",required = true,defaultValue = "0")Integer sourceType,
                                                    @RequestParam(value = "timeRange",required = true,defaultValue = "2")Integer timeRange,
                                                    @RequestParam(value = "authType",required = true,defaultValue = "0")Integer authType,
                                                    @RequestParam(value = "needStuffInfo",required = true,defaultValue = "0")Integer needStuffInfo,
                                                    @RequestParam(value = "needStuffScan",required = true,defaultValue = "0")Integer needStuffScan,
                                                    @RequestParam(value = "authSf",required = true,defaultValue = "0")Integer authSf,
                                                    @RequestParam(value = "authDd",required = true,defaultValue = "0")Integer authDd,
                                                    @RequestParam(value = "authDb",required = true,defaultValue = "0")Integer authDb){
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        VisitorInfoDomain visitorInfoDomain = new VisitorInfoDomain(sourceType,timeRange,authType,needStuffInfo,needStuffScan,authSf,authDd,authDb);

        logger.info("Enter WifiAuthController.updateVisitorStrategy()");
        //访客策略为空
        if (visitorInfoDomain == null){
            logger.info("Update Visitor Strategy But The visitorInfoDomain=[null]");

            return sendBaseErrorMap(ResultCode.STRATEGY_IS_FRAGMENTARY);
        }

        VisitorAuthSource authSource = VisitorAuthSource.getSource(visitorInfoDomain.getSourceType());
        //无法识别的访客源
        if (authSource == null){
            logger.info("Update Visitor Strategy But The AuthSource = [{}]",visitorInfoDomain.getSourceType());

            return sendBaseErrorMap(ResultCode.STRATEGY_VISITOR_SOURCE_TYPE);
        }

        //访客有效时长为负
        if (visitorInfoDomain.getTimeRange() < 0){
            logger.info("Update Visitor Strategy But The Time Range Error");

            return sendBaseErrorMap(ResultCode.STRATEGY_VISITOR_TIME_RANGE_ERROR);
        }

        /*//不支持的访客认证类型
        VisitorAuthType visitorAuthType = VisitorAuthType.getVisitorAuthType(visitorInfoDomain.getAuthType());
        if (visitorAuthType == null){
            logger.info("Update Visitor Strategy But The AuthType = [{}] Can't Be Accept ",visitorInfoDomain.getAuthType());

            return sendBaseErrorMap(ResultCode.STRATEGY_VISITOR_AUTH_TYPE_ERROR);
        }*/

        //访客信息校验异常
        if (visitorInfoDomain.getNeedStuffInfo()<0 || visitorInfoDomain.getNeedStuffInfo()>1){
            logger.info("Update Visitor Strategy But NeedStuffInf Error ");

            return sendBaseErrorMap(ResultCode.STRATEGY_VISITOR_NEED_STAFF_INFO_ERROR);
        }

        //员工协助扫码开关状态异常
        if (visitorInfoDomain.getNeedStuffScan()<0 || visitorInfoDomain.getNeedStuffScan()>1){
            logger.info("Update Visitor Strategy But NeedStuffScan Error ");

            return sendBaseErrorMap(ResultCode.STRATEGY_VISITOR_NEED_SCAN_INFO_ERROR);
        }

        if (!wifiStrategyInfoService.insertStrategy(new WifiVisitorStrategy(visitorInfoDomain,companyUuid))){
            logger.info("Update Visitor Strategy But DataBase Catch An Exception ");

            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_UPDATE_FAILED);
        }

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "更新无线访客策略");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        return sendBaseNormalMap();

    }


    /**
     * 更新MAC绑定信息
     * */
    @PostMapping(value = "/mac/update")
    @ResponseBody
    public Map<String,Object> updateMacBindingStrategy(@RequestParam(value = "count",required = true,defaultValue = "2")Integer count,
                                                       HttpServletRequest request,HttpSession session){

       String companyId=ConstantsCMP.getSessionCompanyId(request);
        MacDeviceBinding macDeviceBinding = new MacDeviceBinding(count);


        logger.info("Enter WifiAuthController.updateMacBindingStrategy()");

        //mac绑定策略为空
        if (macDeviceBinding == null){
            logger.info("Update MacBinding But The macDeviceBinding=[null]");

            return sendBaseErrorMap(ResultCode.STRATEGY_IS_FRAGMENTARY);
        }

        //mac设备绑定数量错误
        if (macDeviceBinding.getCount() == null||macDeviceBinding.getCount() < 0){
            logger.info("Update MacBinding But The device count =[null]");

            return sendBaseErrorMap(ResultCode.STRATEGY_MAC_DEVICE_BINDING_ERROR);
        }


        //更新设备信息错误
        WifiMacDeviceBindingStrategy wifiMacDeviceBindingStrategy= new WifiMacDeviceBindingStrategy(macDeviceBinding);
        wifiMacDeviceBindingStrategy.setCompanyUuid(companyId);
        if (!wifiStrategyInfoService.insertStrategy(wifiMacDeviceBindingStrategy)){

            logger.info("Update MacBinding But DataBase Catch");
            return sendBaseErrorMap(ResultCode.STRATEGY_STUFF_UPDATE_FAILED);
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "更新无线设备绑定上限值");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        return sendBaseNormalMap();
    }






}
