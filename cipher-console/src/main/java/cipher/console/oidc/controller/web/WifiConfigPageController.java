package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.WifiConfigPageService;
import cipher.console.oidc.token.CheckToken;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: TK
 * desc:wife可配置页面的相关接口
 * @Date: 2019/3/6 20:35
 */
@Controller
@RequestMapping(value = "/cipher/wifeconfig")
@EnableAutoConfiguration
public class WifiConfigPageController {




    /*
     *
     * 数据隔离修改
     * */

    @Autowired
    private WifiConfigPageService wifiConfigPageService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> wifiConfigPageAdd(HttpServletRequest request, WifiPotalPageSettingInfo wifiPotalPageSettingInfo, HttpSession session) {
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map =new HashedMap();
        try {
            int count = wifiConfigPageService.getCount(companyId);
            if (count > 2) {
                map.put("code", 1);
                map.put("msg", "添加wifi配置页面不能多于两个");
                return map;
            }
            int count1 = wifiConfigPageService.getProtalType(companyId, wifiPotalPageSettingInfo.getPortalType());
            if (count1 > 0) {
                map.put("code", 1);
                map.put("msg", "已有该类型的配置页面，请到编辑页面进行修改！");
                return map;
            }
            wifiPotalPageSettingInfo.setCompanyId(companyId);
            int flag = wifiConfigPageService.insertNewConfigPage(wifiPotalPageSettingInfo);
            if (flag == 0) {
                map.put("code", 1);
                map.put("msg", "插入失败");
                return map;
            }
            map.put("code", 0);
            map.put("msg", "插入成功");

        }catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "内部服务器失败");
            e.printStackTrace();
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),  "更新无线wifi配置页面");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }



}
