package cipher.console.oidc.controller.web;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.WxUserService;
import cipher.console.oidc.token.CheckToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019-10-1114:56
 **/
@RestController
@RequestMapping(value = "/cipher/wxUser")
public class WxUserController {

    private static final Logger logger = LoggerFactory.getLogger(WxUserController.class);
    @Autowired
    private SessionService sessionService;
    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;
    @Autowired
    private RedisClient<String,Object> redisClient;

    /**
     * 从缓冲表获取企业微信的用户列表
     *
     * @param session       获取公司id
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param queryStr      根据姓名搜索
     * @param dataGridModel 分页参数
     * @return
     */
    //@CheckToken
    @RequestMapping(value = "/getUserList")
    public Map<String, Object> getUserList(HttpSession session,
                                           @RequestParam(value = "status", required = false) Integer status,
                                           @RequestParam(value = "queryStr", required = false) String queryStr,
                                           DataGridModel dataGridModel) {
       String companyUUid = sessionService.getCompanyUUid(session);
        return wxUserService.queryBufferWxList(dataGridModel, companyUUid, status, queryStr);
    }


    /**
     * 扫描企业微信用户
     *
     * @param request 获取公司id
     * @return 返回扫描到的总的记录条数
     */
    //@CheckToken
    @RequestMapping(value = "/scanWxUser")
    public Map<String, Object> scanWxUser(HttpServletRequest request, @RequestParam(required = false) String companyId) {
     String companyUUid = null;
        try {
            HttpSession session = request.getSession();
            companyUUid = sessionService.getCompanyUUid(session);
        } catch (Exception e) {
            logger.error("定时同步不能获取到session");
        }

        if (StringUtils.isEmpty(companyUUid)) {
            companyUUid = companyId;
        }

        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.ORIGINATION_UPDATE.getType(), "扫描企业微信用户信息");
        try {
            adminBehaviorInfoService.insertSelective(record, request.getSession());
        } catch (Exception e) {
            logger.error("定时同步不能获取到session");
        }
        return wxUserService.scanWxUser(companyUUid);
    }


    /**
     * 同步部分企业用户
     *
     * @param userIdList 待同步用户的userid列表
     * @param session
     * @return
     */
  //  @CheckToken
    @RequestMapping(value = "/sync")
    public Map<String, Object> syncWxUser(
            @RequestParam(value = "userIdList") String userIdList,
            HttpSession session) {

        if (StringUtils.isEmpty(userIdList)) {
            return NewReturnUtils.failureResponse("数据不能为空!");
        }
        String companyUUid = sessionService.getCompanyUUid(session);
        redisClient.remove(CacheKey.getCacheOrganitionTreeList(companyUUid));
        redisClient.remove(CacheKey.getCacheGroupTreeList(companyUUid));
        try {
            String[] split = userIdList.split(",");
            List<String> list = Arrays.asList(split);
            return wxUserService.syncDepartWxUser(companyUUid, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.ORIGINATION_UPDATE.getType(), "部分同步企业微信用户");
        adminBehaviorInfoService.insertSelective(record, session);
        return NewReturnUtils.failureResponse("服务器异常!");
    }


  //  @CheckToken
    @RequestMapping(value = "/syncAll")
    public Map<String, Object> syncAllWxUser(HttpServletRequest request,
                                               @RequestParam(required = false) String companyIdParam) {
        String companyUUid = null;

        redisClient.remove(CacheKey.getCacheOrganitionTreeList(companyUUid));
        redisClient.remove(CacheKey.getCacheGroupTreeList(companyUUid));

        try {
            HttpSession session = request.getSession();
            companyUUid = sessionService.getCompanyUUid(session);
        } catch (Exception e) {
            logger.error("定时任务无法获取到session");
        }

        if (StringUtils.isEmpty(companyUUid)) {
            companyUUid = companyIdParam;
        }


        try {
            return wxUserService.syncAllWxUser(companyUUid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            AdminBehaviorInfo record = new AdminBehaviorInfo(AdminBehaviorEnum.ORIGINATION_UPDATE.getType(), "全部同步企业微信用户");
            adminBehaviorInfoService.insertSelective(record, request.getSession());
        } catch (Exception e) {
            logger.error("插入日志失败:{},{}", e.getMessage(), e.getCause());
        }

        return NewReturnUtils.failureResponse("服务器异常!");
    }

}
