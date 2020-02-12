package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.LadpInfoDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.LadpManageService;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;



@Controller
@RequestMapping(value = "/cipher/ladpManage")
@EnableAutoConfiguration
public class LadpController {

    @Autowired
    private LadpManageService ladpManageService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisClient redisClient;


    private static final Logger logger = LoggerFactory.getLogger(LadpController.class);


    /**
     * 查询已有的信息
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public LadpInfoDomain queryDataInfo(HttpServletResponse response) {
        logger.debug("enter LadpController.queryDataInfo");
        LadpInfoDomain ladpInfoDomain = new LadpInfoDomain();
        return ladpManageService.getLadpManageInfo(ladpInfoDomain);
    }


    @RequestMapping(value = "/update")
    public void insertLadpInfo(HttpServletResponse response, HttpServletRequest request, LadpInfoDomain form, HttpSession session) {
        logger.debug("enter LadpController.insertEmailInfo");
        LadpInfoDomain ladpInfoDomain = ladpManageService.getLadpManageInfo(form);
        try {
            String userName = ConstantsCMP.getSessionUser(request);
            String companyId=ConstantsCMP.getSessionCompanyId(request);
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.STRATEGY_MAINTENANCE.getType(),  "插入或更改ladp的配置");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ObjectUtils.isEmpty(ladpInfoDomain)) {
            ladpManageService.insertLadpManageInfo(form);
            ResponseUtils.customSuccessResponse(response, "保存配置信息成功");
        }
        try {

            int i = ladpManageService.updateLadpManageInfo(form);
           /* if(i==1){
                redisClient.flushDB();
            }*/
            ResponseUtils.customSuccessResponse(response, "更新配置信息成功");
        } catch (Exception e) {
            logger.error("enter LadpController.insertLadpInfo; Error:" + e.getCause());
            ResponseUtils.customFailueResponse(response, "更新配置失败，请稍后重试");
            e.printStackTrace();
        }
    }
}











