package com.portal.controller;


import com.alibaba.fastjson.JSON;
import com.portal.commons.AppType;
import com.portal.commons.CacheKey;
import com.portal.commons.Constants;
import com.portal.commons.ConstantsCMP;
import com.portal.domain.*;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import static com.portal.commons.Constants.SERVICEURL;
import static com.portal.commons.Constants.USERNAME;
import static com.portal.enums.ResultCode.APPLICATION_RULE_NOT_EXIT;

/**
 * @Author: TK
 * 仿制casserver的流程的控制类
 * @Date: 2019/7/1 19:09
 */
@Controller
@RequestMapping(value = "/cipher")
public class CopyCasServerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopyCasServerController.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ISessionService sessionService;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;



    @Autowired
    CasAndRdsgConfigService casAndRdsgConfigService;


    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private JudgeLimit judgeLimit;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PortalService portalService;

    @Autowired
    private RedisClient<String, String> redisClient;

    @Autowired
    private UserBehaviorService userBehaviorService;

    @Autowired
    private ApplicationAudithService applicationAudithService;


    /**
     * casclient重定向casserver检查是否需要登录
     *
     * @param serviceUrl 重定向地址
     */
    @RequestMapping(value = "/cas/login")
    public void getTicket(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "service") String serviceUrl) {
        UserBehaviorInfo record = null;
        AppAuditInfo appAuditInfo = null;
        String authUrl=casAndRdsgConfigService.getCasServerAuthUrl();
        //入参校验
        if (StringUtils.isEmpty(serviceUrl)) {
            LOGGER.info("params is null by serviceUrl serviceUrl={{}}", serviceUrl);

            return;
        }
        //判断应用跳转地址是否为空
        if (org.apache.commons.lang.StringUtils.isEmpty(authUrl)){
            LOGGER.info("params is null by serviceUrl authUrl={{}}", authUrl);
            ResponseUtils.sendResultMap(response,ResultCode.CAS_AUTH_URL_NULL);
            return;
        }

        //获取该浏览器的session中的username
        String userName = (String) SessionUtils.getSessionByName(request, USERNAME);

        //如果该值不为空携带票据，發佈事件 重定向到客户端
        if (StringUtils.isNotEmpty(userName)) {

            //根据url获取应用信息
            String appId = applicationService.getIdFromApplicationInfoByServiceUrl(serviceUrl);
            ApplicationInfoDomain applicationInfoDomain = applicationService.getApplicationById(appId);
            if (org.apache.commons.lang.StringUtils.isEmpty(appId)) {
                ResponseUtils.sendResultMap(response, ResultCode.APPLICATION_NO_ADD);
            }
            //判断是否拥有权限
            Boolean limit = judgeLimit.isOwnAppLimit(userName, appId);

            if (!limit) {
                ResponseUtils.sendResultMap(response, ResultCode.USER_NO_LIMIT);
                return;
            }

            //生成票据
            String ticket = TicketUtil.getCasSTTicket();
            //根据uuId获取userDomain
            UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userName);
            //获取从账号的配置规则

            PortalApplyInfo portalApplyInfo = portalService.selectPortalInfo(new PortalApplyInfo(applicationInfoDomain.getApplicationId()));
            //如果参数为空直接返回应用从账号规则未设置
            if (portalApplyInfo == null || org.apache.commons.lang.StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())) {
                ResponseUtils.sendResultMap(response, APPLICATION_RULE_NOT_EXIT, Constants.TYPE, Constants.CAS_TYPE);
                return;
            }
            //根据规则获取从账号
            String subAccount = subAccountRuleService.getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), applicationInfoDomain.getApplicationId());

            //sonAccount为空直接返回错误信息
            if (org.apache.commons.lang.StringUtils.isEmpty(subAccount)) {
                ResponseUtils.sendResultMap(response, ResultCode.USER_NO_SUB);
                return;
            }
            //存入redis中
            String s = JSON.toJSONString(new CasUserDomain(userName, subAccount));
            redisClient.put(ticket, s);

            //生成重定向地址并且重定向
            String url = UrlUtils.getUrl(serviceUrl, ticket);
            try {
                //添加登录日志
                record = new UserBehaviorInfo(userName, null, ConstantsCMP.UserBehaviorConstant.APPLICATION_VISIT, IpUtil.getIp(), subAccount + "登录子系统" + portalApplyInfo.getApplyName() + "成功", "登录子系统", new Date(), "123456");
                userBehaviorService.insertUserBehaviorInfo(record);
                String userInfo = userInfoDomain.getUserName() + "(" + userInfoDomain.getAccountNumber() + ")";
                appAuditInfo = new AppAuditInfo(Integer.valueOf(portalApplyInfo.getId()),
                        userInfo, 1, subAccount + "登录子系统" + portalApplyInfo.getApplyName() + "成功", "123456");
                applicationAudithService.insertAppAuditInfo(appAuditInfo);

                response.sendRedirect(url);
            } catch (IOException e) {
                LOGGER.error("error in redirect to " + url);
                LOGGER.error(e.getMessage(), e);

                return;
            }

            return;
        }

        //将cas认证的标识以及ticket跳转的地址存入session  地址serviceUrl样例http://ip:port?ticket=ST-tbrhyrhbtfunfy
        request.getSession().setAttribute(SERVICEURL, AppType.CAS_TYPE +"-"+serviceUrl);

                //定义StringBuffer字符串 重转向到IAM认证首页
        String stringBuffer = null;
        try {
            stringBuffer = authUrl;
            //TODO
            record = new UserBehaviorInfo(userName, null, ConstantsCMP.UserBehaviorConstant.APPLICATION_VISIT, IpUtil.getIp(),  "用户登录状态不存在，重定向到登录页", "登录子系统", new Date(), "123456");
            userBehaviorService.insertUserBehaviorInfo(record);
            response.sendRedirect(stringBuffer);
        } catch (IOException e) {
            //打印错误
            LOGGER.error("System error by serviceUrl ,this stringBuffer={}", stringBuffer);
            LOGGER.error(e.getMessage(), e);
        }
        return;
    }


    @RequestMapping(value = "/cas/logout")
    public void getLogout(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "service", required = false) String service, @RequestParam(value = "url", required = false) String url) {


        if (StringUtils.isNotEmpty(service)){
            try {
                service=URLDecoder.decode(service,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            };
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(service)) {
            service = url;
        }
        //销毁session重定向到登录页
        String username = (String) SessionUtils.getSessionByName(request, USERNAME);

        //移除redis中的内容以及移除session
        session.removeAttribute(USERNAME);
        redisClient.remove(CacheKey.getUserSessionCacheKey(username));
        if (com.alibaba.dubbo.common.utils.StringUtils.isNotEmpty(username)) {
            session.removeAttribute(ConstantsUrls.USER_SESSION_INFO);
            session.removeAttribute(ConstantsUrls.CONSOLE_SESSION_INFO);
            SystemConfigInfo systemConfigInfo=systemConfigInfoService.getSystemConfigInfo();
           // HttpUtils.sendPost(systemConfigInfo.getDsgServerUrl() + "/cipher/removeSession", "userName=" + username);
        }

        //重定向到登录界面
        try {
            response.sendRedirect(service);
        } catch (IOException e) {
            //打印错误
            LOGGER.error("error in sendRedirect to authUrl={{}}", service);
            LOGGER.error(e.getMessage(), e);
            return;
        }

        return;

    }



}
