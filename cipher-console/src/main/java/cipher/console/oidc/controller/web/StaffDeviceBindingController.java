package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.DeviceInfo;
import cipher.console.oidc.domain.web.StaffDeviceBindingInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.DeviceInfoService;
import cipher.console.oidc.service.StaffDeviceBindingService;
import cipher.console.oidc.util.IpUtil;
import org.apache.commons.collections.map.HashedMap;
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
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/*
* 无线绑定
* */
@Controller
@RequestMapping(value = "/cipher/staffdevice")
@EnableAutoConfiguration
public class StaffDeviceBindingController {


    private static final Logger LOGGER = LoggerFactory.getLogger(StaffDeviceBindingController.class);

    @Autowired
    private StaffDeviceBindingService staffDeviceBindingService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;




    /*
    * 获取无线WiFi绑定列表
    * （修改）
    * */
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(StaffDeviceBindingInfo form, DataGridModel pageModel,HttpServletRequest request) {
        LOGGER.info("enter DeviceInfoController.getList;parameters: " + form.toString());
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String,Object> map=new HashMap<>();
        try{
            form.setCompanyId(companyId);
            return staffDeviceBindingService.selectStaffDeviceBindingInfoList(form, pageModel);
        }catch (Exception e){
            e.printStackTrace();
            map.put("return_code",ConstantsCMP.Code.EXPERTION);
            map.put("return_msg","服务器错误");
            return map;
        }

    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "id") String id, HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashedMap();
        int flag = staffDeviceBindingService.deleteStaffDeviceBindingInfo(Integer.valueOf(id));
         if(flag>0){
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "解绑成功");
        }else {
             map.put("return_code", ConstantsCMP.Code.FAIL);
             map.put("return_msg", "解绑失败");
         }

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "删除无线设备绑定上限值");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }






}
