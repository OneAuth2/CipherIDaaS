package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.enums.SyncOu;
import cipher.console.oidc.model.ModifyAccountNumberModel;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import cipher.console.oidc.service.ldap.AdSync2DbService;
import cipher.console.oidc.util.ReturnJsonCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/10/26 16:00
 */
@Controller
@RequestMapping(value = "/cipher")
public class Buffer2UserController {

    @Autowired
    private AdUserBufferService adUserBufferService;


    private static final Logger LOGGER = LoggerFactory.getLogger(Buffer2UserController.class);

    @Autowired
    private AdSync2DbService adSync2DbService;
    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RedisClient<String,Object> redisClient;

    /**
     * 部分同步
     *
     * @param request
     * @param response
     * @param bufferUserIdList
     * @return
     */
    @RequestMapping(value = "/syncBuffer2User", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncBuffer2User(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                               @RequestParam(value = "bufferIdList") List<Integer> bufferUserIdList) {

        LOGGER.info("进入部分同步");
       // String companyId="123456";
         String companyId=ConstantsCMP.getSessionCompanyId(request);
        redisClient.remove(CacheKey.getCacheOrganitionTreeList(companyId));
        redisClient.remove(CacheKey.getCacheGroupTreeList(companyId));
        Map<String, Object> map = new HashMap<>();

        if (CollectionUtils.isEmpty(bufferUserIdList)) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "请先选择要操作的数据");
            return map;
        }

        List<AdUserBufferDomain> toUpdateList = adUserBufferService.queryListByIdList(bufferUserIdList);
        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),"AD部分同步账号");
        adminBehaviorInfoService.insertSelective(record,session);
        return adSync2DbService.ayncBuffer2User(toUpdateList,companyId);
    }


    /**
     * 全部同步
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/syncAll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> syncAll(HttpServletRequest request,
                                       @RequestParam(required = false) String companyIdParam) {
        LOGGER.info("进入同步所有");
        try {
            AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(), "AD全部同步账号");
            adminBehaviorInfoService.insertSelective(record, request.getSession());
        } catch (Exception e) {
            LOGGER.error("插入同步日志失败");
        }

        String companyId = null;
        try {
            companyId = ConstantsCMP.getSessionCompanyId(request);
        } catch (Exception e) {
            LOGGER.error("定时任务后去不到session");
        }

        if (StringUtils.isEmpty(companyId)) {
            companyId = companyIdParam;
        }
        redisClient.remove(CacheKey.getCacheOrganitionTreeList(companyId));
        redisClient.remove(CacheKey.getCacheGroupTreeList(companyId));
        List<AdUserBufferDomain> adUserBufferDomainList = adUserBufferService.queryAllBufferUser(companyId);
        return adSync2DbService.ayncBuffer2User(adUserBufferDomainList, companyId);
    }


}
