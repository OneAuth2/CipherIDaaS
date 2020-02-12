package com.portal.controller;


import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.RdsgDomain;
import com.portal.domain.RealParamDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.RealParamUtils;
import com.portal.utils.ResponseUtils;
import com.portal.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.*;
import static com.portal.enums.ResultCode.LOGIN_AUTH_FAILED;


/**
 * 处理从Rdsg处理的请求
 *
 * @Author: TK
 * @Date: 2019/7/17 16:25
 */
@Controller
@RequestMapping(value = "/cipher")
public class RdsgController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(RdsgController.class);

   @Autowired
   private CasAndRdsgConfigService casAndRdsgConfigService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private RedisClient redisClient;


    @Autowired
    private RdsgService rdsgService;




    /**
     * create by 安歌
     * create time 2019年7月17日16:31:25
     *
     * @param request
     * @param response
     * @param appHost  地址
     */
    @RequestMapping(value = "/lua/login")
    public void getTicket(HttpServletRequest request, HttpServletResponse response, String appHost) {

        String platFormUrl =casAndRdsgConfigService.getBindingAccountUrl();
        String authUrl=casAndRdsgConfigService.getRdsgServerAuthUrl();
        //入参校验
        if (StringUtils.isEmpty(appHost)) {
            logger.info("params is null by appHost appHost={{}}", appHost);
            return;
        }
        //Rdsg配置校验
        if (StringUtils.isEmpty(authUrl)) {
            logger.info("params is null by authUrl authUrl={{}}", authUrl);
            ResponseUtils.sendResultMap(response,ResultCode.RDSG_AUTH_URL_NULL);
            return;
        }


        //判断账号的登录状态
        String userName = (String) SessionUtils.getSessionByName(request, USERNAME);
        //根据Apphost获取应用id
        String appId = applicationService.getIdByAppHost(appHost);
        //判断是否该应用
        if (org.apache.commons.lang.StringUtils.isEmpty(appId)) {
            ResponseUtils.sendResultMap(response, ResultCode.APPLICATION_NO_ADD);
            return;
        }

        //通过应用id获取应用信息
        ApplicationInfoDomain applicationInfoDomain = applicationService.getApplicationById(appId);

        //判断应用url是否为空
        String applicationUrl=applicationInfoDomain.getApplicationUrl();
        if (org.apache.commons.lang.StringUtils.isEmpty(applicationUrl)){
            ResponseUtils.sendResultMap(response,ResultCode.APPLICATION_NO_APPLICATIONURL);
            return;
        }

        //如果該用户已经登录 直接跳出当前流程 走登录之后的流程
        if (StringUtils.isNotEmpty(userName)) {
            //获取companyUuid的值
            String companyUuid = (String) SessionUtils.getSessionByName(request, Constants.COMPANY_UUID);
            //公司id为空返回信息
            if (org.apache.commons.lang.StringUtils.isEmpty(companyUuid)) {
                ResponseUtils.sendResultMap(response, ResultCode.COMPANY_UUID);
                return;
            }
            //获取前端跳转处理的URL经过处理 与传来的域名保持一致
            //String platRealFormUrl=UrlUtils.getDynamicUrl(applicationUrl,platFormUrl);
            //新建rdsg的对象
            RdsgDomain rdsgDomain = new RdsgDomain(userName, appId, companyUuid, response, platFormUrl, applicationInfoDomain.getApplicationUrl(), applicationInfoDomain.getApplicationId());
            //走登录之后的流程携带rdsg对象
            rdsgService.isLoggedIn(rdsgDomain);

            return;
        }

        //将Rdsg认证的标识以及ticket跳转的地址存入session
        request.getSession().setAttribute(SERVICEURL, AppType.RDSG_TYPE +"-"+appHost);
        request.getSession().setAttribute(X_DSG_URL, applicationInfoDomain.getxDsgUrl());

//        //获取动态跳转地址
//        String  dynamicUrl= UrlUtils.getDynamicUrl(applicationUrl,authUrl);

        //定义StringBuffer字符串 重转向到IAM认证首页
        try {
            response.sendRedirect(authUrl);
        } catch (IOException e) {
            //打印错误
            logger.error("System error by serviceUrl ,this stringBuffer={}", authUrl);
            logger.error(e.getMessage(), e);
        }

        return;
    }


    /**
     * 根据票据获取应用地址
     *
     * @param httpServletResponse
     * @param request
     * @param ticket              票据
     */
    @RequestMapping(value = "/lua/getTicket")
    @ResponseBody
    public String getSsoUrl(HttpServletResponse httpServletResponse, HttpServletRequest request, String ticket) {

        System.out.println("进入获取应用信息");
        //入参校验
        if (StringUtils.isEmpty(ticket)) {
            logger.info("params is null by ticket ticket={{}}", ticket);

            return null;
        }

        //从redis获取地址
        Map<String, Object> map = new HashMap<>();
        if (!redisClient.containsKey(ticket)) {
            ResponseUtils.sendResultMap(httpServletResponse, LOGIN_AUTH_FAILED);
            return "";
        }

        String url = (String) redisClient.get(ticket);
        redisClient.remove(ticket);
        //TODO 根据路径获取RealParam信息
        //根据url构建真实返回参数
        if (org.apache.commons.lang.StringUtils.isEmpty(url)) {
            return null;
        }

        logger.info("url is null by url url={{}}", url);
        RealParamDomain realParamDomain = RealParamUtils.getRealParam(url);

        if (realParamDomain == null) {
            logger.error("construct param realParamDomain error and realParamDomain={{}}", realParamDomain);
            return "";
        }

        //如果参数为空 返回未认证 结束流程
        if (realParamDomain == null) {
            map.put(Constants.RETURN_CODE, Constants.ERROR_CODE);
            map.put(Constants.RETURN_MSG, Constants.PORTAL_UNAUTHNATION);
            return "";
        }

        //重定向地址不为空 返回成功  并且把地址也返回
        map.put(Constants.RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(Constants.DATA, realParamDomain);
        System.out.println("realParamDomain-------------------" + realParamDomain);
        return JSON.toJSONString(map);

    }

    private String getJetToken(String userName) {
        Integer random = (int) ((Math.random() * 9 + 1) * 100000);
//        String cipherString = userName + applyId + random;
        String token = JWT.create().withAudience(userName).sign(Algorithm.HMAC256("cipherchina"));
        return token;
    }

    /**
     * 重定向到登录页
     *
     * @param response
     * @param request
     */
    @RequestMapping(value = "/index/login")
    public void redirectIndex(HttpServletResponse response, HttpServletRequest request) {

        //获取Rdsg的认证界面
        String authUrl=casAndRdsgConfigService.getRdsgServerAuthUrl();
        if (org.apache.commons.lang.StringUtils.isEmpty(authUrl)){
            logger.info("enter redirectIndex() error and params authUrl is null " ,authUrl);
            ResponseUtils.sendResultMap(response,ResultCode.RDSG_AUTH_URL_NULL);
            return;
        }
        //重定向
        try {
            response.sendRedirect(authUrl);
        } catch (IOException e) {
            logger.error("redirect error");
            logger.error(e.getMessage(), e);
        }
    }


}
