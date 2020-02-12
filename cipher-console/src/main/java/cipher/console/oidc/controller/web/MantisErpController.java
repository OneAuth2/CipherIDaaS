package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.ErpConfigInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.MantisErpService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.ReturnJsonCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/mantis")
@EnableAutoConfiguration
public class MantisErpController {

    @Autowired
    private MantisErpService mantisErpService;

    @Autowired
    RedisClient<String, Object> redisClient;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;





    /*
    * 认证源信息
    * （数据隔离修改）
    * */
    @CheckToken
    @RequestMapping(value = "/erp")
    @ResponseBody
    public ErpConfigInfo pageSet(HttpServletRequest request){
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        return mantisErpService.selectTheConfig(companyId);
    }


    /*
     * 认证源信息编辑
     * （数据隔离修改）
     * */
    @CheckToken
    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> updateUrl(ErpConfigInfo erpConfigInfo, HttpSession session){
        Map<String,Object> map = new HashMap();
        if (StringUtils.isBlank(String.valueOf(erpConfigInfo.getId()))){
            map.put("return_msg","修改失败");
            map.put("return_code", ReturnJsonCode.FAIL);
            return map;
        }
        mantisErpService.updateTheConfig(erpConfigInfo);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),  "更新ERP认证源");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        map.put("return_msg","修改成功");
        map.put("return_code", ReturnJsonCode.SUCCESS);
        return map;
    }

}
