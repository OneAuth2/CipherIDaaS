package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.ReturnUtils;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.DeviceInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.DeviceInfoMapper;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.DeviceInfoService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.IpUtil;
import com.alibaba.fastjson.JSON;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

/**
 * Created by 95744 on 2018/8/15.
 */


@Controller
@RequestMapping(value = "/cipher/device")
@EnableAutoConfiguration
public class DeviceInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceInfoController.class);

    @Autowired
    private DeviceInfoService deviceInfoService;


    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;


    /*
    * 获取移动设备数量
    * （数据隔离修改）
    *
    * */
    @CheckToken
    @RequestMapping(value = "/queryList")
    @ResponseBody
    public Map<String, Object> page(HttpServletRequest request) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        int number = deviceInfoService.getDeviceNum(companyId);
        return successResponse("number", number);
    }


    /*
    * 获取移动设备列表
    * （数据隔离修改）
    *
    * */
    @CheckToken
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(DeviceInfo form, DataGridModel pageModel,HttpServletRequest request) {
        LOGGER.info("enter DeviceInfoController.getList;parameters: " + form.toString());
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        return deviceInfoService.selectDeviceInfoList(form, pageModel);
    }


    @RequestMapping(value = "/infoAccountNumber")
    @ResponseBody
    public Map<String, Object> pagelist(@RequestParam(value = "accountNumber") String accountNumber) {
        return successResponse("accountNumber", accountNumber);
    }


    @RequestMapping(value = "/getInfolist", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getInfolist(@RequestParam(value = "accountNumber") String accountNumber, DataGridModel pageModel) {
        DeviceInfo form = new DeviceInfo();
        form.setAccountNumber(accountNumber);
        return deviceInfoService.selectDeviceInfoListByAccountNumber(form, pageModel);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam(value = "id") String id, HttpServletRequest request,HttpSession session) {
        Map<String, Object> map = new HashedMap();
        int flag = deviceInfoService.deleteDevice(Integer.valueOf(id));
        if (flag > 0) {
            try {
                AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),"删除设备");
                adminBehaviorInfoService.insertSelective(record,session);
            } catch (Exception e) {
                e.printStackTrace();
            }

            map.put("returnCode", 0);
            map.put("msg", "操作成功");
            return map;
        }
        map.put("returnCode", 1);
        map.put("msg", "操作失败");
        return map;
    }


    @RequestMapping(value = "/getCount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getCount(@RequestParam(value = "accountNumber") String accountNumber, DataGridModel pageModel) {
        Map<String, Object> map = new HashedMap();
        DeviceInfo form = new DeviceInfo();
        form.setAccountNumber(accountNumber);
        map.put("count", deviceInfoService.getCount(form));
        return map;
    }



    /*
    * 修改用户设备绑定数量
    * （修改）
    * */
    @RequestMapping(value = "/onAndoff", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upAndDown(@RequestParam(value = "number", defaultValue = "0") int number,
                                         @RequestParam(value = "openSelect", defaultValue = "0") int openSelect,
                                          HttpServletRequest request,HttpSession session
    ) {
        Map<String, Object> map = new HashedMap();
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        int flag = deviceInfoService.updateDevice(number, openSelect,companyId);
        if (flag > 0) {
            map.put("returnCode", 0);
            map.put("msg", "操作成功");
        } else {
            map.put("returnCode", 1);
            map.put("msg", "操作失败");
        }
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),"修改设备数量");
        adminBehaviorInfoService.insertSelective(record,session);
        return map;
    }
}
