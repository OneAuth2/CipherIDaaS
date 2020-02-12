package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AcAclInfo;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.WifiActionMessage;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.model.MemberLogoutModel;
import cipher.console.oidc.service.AcAclInfoService;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.WifiOffLineService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.Base64Utils;
import cipher.console.oidc.util.HttpRequest;
import cipher.console.oidc.util.ReturnJsonCode;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
@RequestMapping(value = "/cipher/acl")
@EnableAutoConfiguration
public class AcAclController {

    @Autowired
    private AcAclInfoService acAclInfoService;


    @Autowired
    private WifiOffLineService wifiOffLineService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    /*
     * 获取无线免证设备列表
     * （数据隔离修改）
     *
     * */
    @CheckToken
    @RequestMapping(value = "/list",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> list(AcAclInfo acAclInfo, DataGridModel dataGridModel,HttpServletRequest request){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        acAclInfo.setCompanyId(companyId);
        acAclInfo.setPageData(dataGridModel);
        List<AcAclInfo> rows = acAclInfoService.selectAclInfo(acAclInfo);
        Integer total=acAclInfoService.selectCount(acAclInfo);
        Map<String,Object> map = new HashMap<>();
        map.put("rows",rows);
        map.put("total", total);
        return map;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> deleteAcl(HttpServletRequest request, HttpSession session,
                                         @RequestParam(value = "mac",required = true)String mac){
        Map<String,Object> map = new HashMap<>();
        if (StringUtils.isBlank(mac)){
            map.put("return_msg","删除失败");
            map.put("return_code",ConstantsCMP.Code.FAIL);
            return map;
        }
        mac = formatMacString(mac);
        acAclInfoService.deleteAclInfoByMac(mac);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),  "添加无线免证设备");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        map.put("return_msg","删除成功");
        map.put("return_code", ConstantsCMP.Code.SUCCESS);
        return map;
    }

    String formatMacString(String mac){
        return mac.trim()
                .replace("-","")
                .replace(".","")
                .replace(",","")
                .replace("/","")
                .toLowerCase();
    }

    @RequestMapping(value = "/logout", params = "json")
    @ResponseBody
    public Map<String, Object> onlineMemberLogout(HttpServletResponse response,
                                                  @RequestParam(value = "mac",required = true)String mac,
                                                  @RequestParam(value = "ip",required = true)String ip,
                                                  @RequestParam(value = "name",required = true)String name) {

        Map<String, Object> map = new HashMap<>();
        try {
            WifiActionMessage wifiActionMessage = new WifiActionMessage();
            wifiActionMessage.setIp(ip);
            wifiActionMessage.setMac(mac);
            wifiActionMessage.setUserName(name);
            wifiOffLineService.sendWifiOffLineMsg(wifiActionMessage);
            AcAclInfo acAclInfo = new AcAclInfo();
            acAclInfo.setMac(mac);
            acAclInfo.setIp(null);
            acAclInfoService.updateAclInfo(acAclInfo);
            map.put("return_msg", "下线成功");
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.Code.FAIL);
        }
        return map;
    }


}
