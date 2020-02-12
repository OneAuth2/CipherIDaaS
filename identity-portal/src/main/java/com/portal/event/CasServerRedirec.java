package com.portal.event;

import com.alibaba.fastjson.JSON;
import com.portal.commons.Constants;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.CasUserDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.ApplicationService;
import com.portal.service.JudgeLimit;
import com.portal.utils.ResponseUtils;
import com.portal.utils.SessionUtils;
import com.portal.utils.TicketUtil;
import com.portal.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.*;
import static com.portal.enums.ResultCode.APPLICATION_RULE_NOT_EXIT;

/**
 * @Author: TK
 * @Date: 2019/7/10 16:43
 */
public class CasServerRedirec {

    private static final Logger logger = LoggerFactory.getLogger(CasServerRedirec.class);

    public static void redirecResult(CasServerRedirectEvent event, RedisClient redisClient) {
        HttpServletRequest request = event.getRequest();
        HttpServletResponse response = event.getResponse();
        ApplicationService applicationService = event.getApplicationService();
        JudgeLimit judgeLimit = event.getJudgeLimit();

        //获取xdsg的地址
        String xDsgUrl = (String) SessionUtils.getSessionByName(request, Constants.X_DSG_URL);
        //获取存入session中serviceURL的地址以及名字并判断跳转地址是否存在
        String serviceUrl = (String) SessionUtils.getSessionByName(request, Constants.SERVICEURL);
        //移除session中serviceURL、xDsgURL的值
        request.getSession().removeAttribute(Constants.SERVICEURL);
        request.getSession().removeAttribute(Constants.X_DSG_URL);
        //判断是走哪个流程并设置标识 默认为cas的标识
        int type=Constants.CAS_TYPE;
        //走RDSG的标识
        if (StringUtils.isNotEmpty(xDsgUrl)){
            type=Constants.RDSG_TYPE;
        }

        //移除session中的值
        String userName = (String) SessionUtils.getSessionByName(request, Constants.USERNAME);
        String companyUuid=(String) SessionUtils.getSessionByName(request, Constants.COMPANY_UUID);
        if (StringUtils.isEmpty(serviceUrl) || StringUtils.isEmpty(userName)||StringUtils.isEmpty(companyUuid)) {
            ResponseUtils.sendResultMap(response, ResultCode.APPLICATION_NO_SKIP_URL,Constants.TYPE,type);
            return;
        }

        //通过appid获取应用的信息并判断应用是否存在
        ApplicationInfoDomain applicationInfoDomain = getApplication(applicationService, serviceUrl, xDsgUrl, userName);
        if (applicationInfoDomain == null) {
            ResponseUtils.sendResultMap(response, ResultCode.APPLICATION_NO_URL,Constants.TYPE,type);
            return;
        }

        //判断该账号是否拥有该应用的权限
        Boolean limit = judgeLimit.isOwnAppLimit(userName, applicationInfoDomain.getId().toString());
        if (!limit) {
            ResponseUtils.sendResultMap(response, ResultCode.USER_NO_LIMIT,Constants.TYPE,type);
            return;
        }

        /*
        *根据url以及主账号获取从账号信息
        *
        */
        //根据uuId获取userDomain
        UserInfoDomain userInfoDomain=event.getUserInfoService().getUserInfoByUUid(userName);
        //获取从账号的配置规则

        PortalApplyInfo portalApplyInfo = event.getPortalService().selectPortalInfo(new PortalApplyInfo(applicationInfoDomain.getApplicationId()));
        //如果参数为空直接返回应用从账号规则未设置
        if (portalApplyInfo == null ||StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())){
            ResponseUtils.sendResultMap(response,APPLICATION_RULE_NOT_EXIT,Constants.TYPE,type);
            return ;
        }
        //根据规则获取从账号
        String subAccount=event.getSubAccountRuleService().getSubAccountRule(userInfoDomain ,portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId());



        //判断是否去xdsg请求url
        if (StringUtils.isNotEmpty(xDsgUrl)) {
            //根据规则获取密码
            String subPwd=event.getSubAccountRuleService().getSubPwdRule(userName,portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId());
            //判断子账号是否存在
            if (!checkRdsgIsOwnSubAccount(subAccount,subPwd,event,portalApplyInfo,userInfoDomain,applicationInfoDomain,serviceUrl)){
                return;
            }
            responseXdsg(redisClient,event,applicationInfoDomain.getApplicationId(),userInfoDomain,companyUuid,portalApplyInfo,applicationInfoDomain,serviceUrl);
            return;
        }

        //走cas的流程
        responseCas(response, redisClient, userInfoDomain,subAccount, serviceUrl,applicationInfoDomain);
        return;

    }


    /**
     * 判断是否拥有子账号 如果没有子账号 直接跳转到配置从账号界面
     * @param subAccount
     * @param subPwd
     * @param event
     * @param portalApplyInfo
     * @param userInfoDomain
     * @param applicationInfoDomain
     * @param serviceUrl
     * @return
     */
    public static Boolean checkRdsgIsOwnSubAccount(String subAccount,String subPwd,CasServerRedirectEvent event,PortalApplyInfo portalApplyInfo,UserInfoDomain userInfoDomain,
                                                   ApplicationInfoDomain applicationInfoDomain,String serviceUrl){
        if (StringUtils.isEmpty(subAccount)||(StringUtils.isEmpty(subAccount)&& portalApplyInfo.getApplicationType() == 6)) {

            ResponseUtils.sendResultMap(event.getResponse(), ResultCode.USER_NO_SUB,Constants.TYPE,Constants.RDSG_TYPE,"subAccount",
                    event.getSubAccountRuleService().
                            checkSubAccountRule(userInfoDomain,portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId()),
                    "subPwd",event.getSubAccountRuleService().
                            checkSubPwdRule(userInfoDomain.getUuid(),portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId()),"appId",String.valueOf(applicationInfoDomain.getId()),"appHost",serviceUrl);
            return false;
        }

        if (StringUtils.isEmpty(subAccount)||StringUtils.isEmpty(subPwd)){
            ResponseUtils.sendResultMap(event.getResponse(), ResultCode.USER_NO_SUB,Constants.TYPE,Constants.RDSG_TYPE,"subAccount",
                    event.getSubAccountRuleService().
                            checkSubAccountRule(userInfoDomain,portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId()),
                    "subPwd",event.getSubAccountRuleService().
                            checkSubPwdRule(userInfoDomain.getUuid(),portalApplyInfo.getAssociatedAccount(),applicationInfoDomain.getApplicationId()),"appId",String.valueOf(applicationInfoDomain.getId()),"appHost",serviceUrl);
            return false;
        }
        return true;
    }
    /**
     * create by 安歌
     * create time 2019年7月18日14:59:47
     * 根据路径获取应用
     * @param serviceUrl
     * @param xdsgUrl
     * @param userId
     * @return
     */
    public static ApplicationInfoDomain getApplication(ApplicationService applicationService, String serviceUrl, String xdsgUrl, String userId) {

        //入参校验
        if (serviceUrl == null) {
            return null;
        }

        //走cas流程获取的应用
        if (StringUtils.isEmpty(xdsgUrl)) {

            //获取应用的id并判断该url是否在库中存在
            String appId = applicationService.getIdFromApplicationInfoByServiceUrl(serviceUrl);
            //appid直接为空
            if (StringUtils.isEmpty(appId)) {
                return null;
            }
            //通过appid获取应用的信息并判断应用是否存在
            ApplicationInfoDomain applicationInfoDomain = applicationService.getApplicationById(appId);

            return applicationInfoDomain;
        }

        //走XDSG分支的应用
        String appId = applicationService.getIdByAppHost(serviceUrl);
        //判断是否该应用
        if (org.apache.commons.lang.StringUtils.isEmpty(appId)) {

            return null;
        }

        ApplicationInfoDomain applicationInfoDomain = applicationService.getApplicationById(appId);
        return applicationInfoDomain;


    }

    /**
     * 走cas流程的具体流程
     *create by 安歌
     * create time 2019年7月22日11:13:49
     * @param response
     * @param redisClient
     * @param userInfoDomain
     * @param serviceUrl
     */
    public static void responseCas(HttpServletResponse response, RedisClient redisClient, UserInfoDomain userInfoDomain,String subAccount, String serviceUrl,ApplicationInfoDomain applicationInfoDomain) {

        if (StringUtils.isEmpty(subAccount)){
            ResponseUtils.sendResultMap(response,ResultCode.USER_NO_SUB,Constants.TYPE,CAS_TYPE);
        }
        //生成票据
        String ticket = TicketUtil.getCasSTTicket();

        String s=JSON.toJSONString(new CasUserDomain(userInfoDomain.getUuid(),subAccount));
        //存入redis中
        redisClient.put(ticket,s, Constants.CAS_REDIS_TTL);
        //生成重定向地址
        String url = UrlUtils.getUrl(serviceUrl, ticket);

       // HttpUtils.sendPost(applicationInfoDomain.getxDsgUrl() +"/cipher/addSession","userName="+userInfoDomain.getUuid());
       // logger.info(applicationInfoDomain.getxDsgUrl() +"/cipher/addSession","userName="+userInfoDomain.getUuid());
        logger.info(url);

        //重定向到目标地址
        Map<String, Object> map = new HashMap<>();
        map.put(RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(SERVICE_URL, url);
        map.put(TYPE,CAS_TYPE);
        //把数据返回前端
        responseMap(response, map);

    }

    /**
     * 走RDSG的流程
     * create by 安歌
     * create time 2019年7月22日11:13:29
     * @param redisClient
     * @param event
     * @param applicationId
     * @param userInfoDomain
     * @param companyUuid
     */
    public static void responseXdsg(RedisClient redisClient,CasServerRedirectEvent event,String applicationId,UserInfoDomain userInfoDomain

            ,String companyUuid,PortalApplyInfo portalApplyInfo,ApplicationInfoDomain applicationInfoDomain,String appHost) {

        Map<String,Object> resMap=event.getPortalToXDsgService().portalToXDsg(event.getResponse(),applicationId, userInfoDomain.getUuid(), companyUuid);
        //设置类型1
        int type=Constants.RDSG_TYPE;
        //参数校验
        if (resMap == null){
            return ;
        }



        int returnCode= (int) resMap.get(Constants.RETURN_CODE);
        //获取返回值的returnCode
        if (returnCode == Constants.ERROR_RETURN_CODE) {

            String subAccountReal = event.getSubAccountRuleService().checkSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), applicationId);

            String subPwdReal = event.getSubAccountRuleService().
                    checkSubPwdRule(userInfoDomain.getUuid(), portalApplyInfo.getAssociatedAccount(), applicationId);
            //判断从账号是否配置成功，如果失败 直接跳转到配置从账号的界面
            ResponseUtils.sendResultMap(event.getResponse(), ResultCode.USER_NO_SUB,Constants.TYPE,type,"subAccount",
                    subAccountReal,
                    "subPwd",subPwdReal,"appId",String.valueOf(applicationInfoDomain.getId()),"appHost",appHost);

            return;
        }
        //向map中添加类型
        resMap.put(TYPE,RDSG_TYPE);
        if (returnCode != Constants.SUCCESS_CODE){
            //resMap.remove("return_code");
            resMap.put(Constants.TYPE,type);
            //把数据返回前端
            responseMap(event.getResponse(), resMap);
            return;
        }

        String url1 = (String) resMap.get(Constants.APPLY_URL);
        //生成票据并存入redis
        String ticket = TicketUtil.getCasSTTicket();
        //存入redis中
        redisClient.put(ticket, url1);

        //生成到rdsg的地址
        String rdsgUrl=UrlUtils.getDynamicUrl(applicationInfoDomain.getApplicationUrl(),event.getrDsgUrl());
        //生成重定向地址
        String url = UrlUtils.getRDsgUrl(rdsgUrl, ticket,url1);

        //添加返回信息的集合
        Map<String, Object> map = new HashMap<>();
        map.put(RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(SERVICE_URL, url);
        map.put(TYPE,RDSG_TYPE);
        //把数据返回前端
        responseMap(event.getResponse(), map);

    }

    /**
     * 把map返回前端
     * create by 安歌
     * create time 2019年7月17日18:57:03
     * @param response
     * @param map
     * @throws IOException
     */
    public static void responseMap(HttpServletResponse response, Map map) {

        //入参校验
        if (map == null) {
            return;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=UTF-8");
        //定义输出流
        PrintWriter out = null;
        try {
            //初始化对象
            out = response.getWriter();
            //将map转为json输出到前端
            String s = JSON.toJSONString(map);
            out.write(s);
            out.flush();

        } catch (IOException e) {
            //打印错误
            logger.error("System error by out ,this out={}", out);
            logger.error(e.getMessage(), e);
            ResponseUtils.sendResultMap(response, ResultCode.PORTAL_SYSTEM_ERROR);
        }finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
