package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.RadiusConfigDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.RadiusConfigInfoService;
import cipher.console.oidc.util.ResponseUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: zt
 * radius相关配置
 * @Date: 2018/7/26 9:54
 */
@Controller
@RequestMapping(value = "/cipher/radius")
public class RadiusController {


    @Autowired
    private RadiusConfigInfoService radiusConfigInfoService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;



    @ApiOperation(value = "获取radius配置", notes = "获取radius配置信息")
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public RadiusConfigDomain radiusConfig(HttpServletRequest request, HttpServletResponse response) {
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        return radiusConfigInfoService.queryRadiusConfig(companyId);
    }


    @ApiOperation(value = "更新radius配置", notes = "更新radius配置信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(RadiusConfigDomain radiusConfigDomain, HttpServletResponse response, HttpSession session) {
        System.err.println("接收到的radius的配置信息:" + radiusConfigDomain);
        try {
            radiusConfigInfoService.updateRadiusConfig(radiusConfigDomain);
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),  "更新radiu配置");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
            ResponseUtils.customSuccessResponse(response, "更新Radius配置成功");
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "更新Radius配置失败");
        }
    }


    @RequestMapping(value = "/radiusSate", method = RequestMethod.GET)
    @ResponseBody
    public String getRadiusState(HttpServletRequest request, HttpServletResponse response) {
        Runtime runtime = Runtime.getRuntime();
//        System.err.println(runtime);
        try {
            String cmd = "netstat -anp|grep 8072";
//            String cmd = "pwd";
            Process process = runtime.exec(cmd);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder res = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                res.append(line).append("\n");
            }
            System.err.println(res);
           return res.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
