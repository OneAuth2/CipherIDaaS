package com.portal.controller;

import com.alibaba.dubbo.common.URL;
import com.alibaba.fastjson.JSON;
import com.portal.auth2.enums.ErrorCodeEnum;
import com.portal.auth2.enums.ExpireEnum;
import com.portal.auth2.model.SsoAccessToken;
import com.portal.auth2.model.SsoClientDetails;
import com.portal.auth2.model.SsoRefreshToken;
import com.portal.auth2.utils.DateUtils;
import com.portal.bto.UserBto;
import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.SystemConfigInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.service.*;
import com.portal.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.SERVICEURL;
import static com.portal.enums.ResultCode.*;

/**
 * TODO:
 * create by liuying at 2019/11/27
 *
 * @author liuying
 * @since 2019/11/27 11:52
 */

@Controller
@RequestMapping(value = "/cipher/oauth")
public class Auth2LoginController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(Auth2LoginController.class);

    @Autowired
    private AuthSsoService authSsoService;

    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private PortalService portalService;

    @Autowired
    private ServiceAdapter serviceController;


    @Autowired
    private SystemConfigInfoService systemConfigInfoService;


    @Autowired
    private com.portal.service.SubAccountRuleService subAccountRuleService;


    @Autowired
    private JudgeLimit judgeLimit;


    /**
     * 获取Token
     *
     * @param request HttpServletRequest
     * @author liuying
     */
    @RequestMapping(value = "/authorize")
    public void authorize(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //回调URL
        String redirectUri = request.getParameter("redirect_uri");
        String state = request.getParameter("state");
        logger.info(MessageFormat.format("auth2单点登录参数：redirect_url:【{0}】,state:【{1}】",
                redirectUri, state));

        SystemConfigInfo systemConfigInfo = systemConfigInfoService.getSystemConfigInfo();
        if (StringUtils.isNotEmpty(redirectUri) || StringUtils.isNotEmpty(state)) {
            try {
                response.sendRedirect(systemConfigInfo.getRedirectPortalUrl() + "?redirectUri=" + redirectUri + "&state=" + state);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response, HttpServletRequest request, String redirectUri, String state) throws IOException {

        HttpSession session = request.getSession();
        String uuid = (String) session.getAttribute(Constants.USERNAME);
        SystemConfigInfo systemConfigInfo = systemConfigInfoService.getSystemConfigInfo();
        if (StringUtils.isEmpty(uuid)) {
            request.getSession().setAttribute(SERVICEURL, AppType.AUTH_TYPE + "-" + redirectUri);
            response.sendRedirect(systemConfigInfo.getCasServerAuthUrl());
            return;
        }
        UserInfoDomain user = userInfoService.getUserInfoByUUid(uuid);
        //判断用户是否存在
        if (null == user) {
            serviceController.sendResultMap(ResultCode.USER_IS_NOT_EXIST, Constants.TYPE, AppType.AUTH_TYPE);
            return;

        }
        //过期时间
        Long expiresIn = DateUtils.dayToSecond(ExpireEnum.ACCESS_TOKEN.getTime());
        //查询接入客户端
        SsoClientDetails ssoClientDetails = new SsoClientDetails();
        ssoClientDetails.setRedirectUrl(redirectUri);
        ssoClientDetails = portalService.getAuthApplicationInfo(ssoClientDetails);
        //判断应用是否存在
        if (null == ssoClientDetails) {
            serviceController.sendResultMap(ResultCode.APPLICATION_NO_EXIT, Constants.TYPE, AppType.AUTH_TYPE);
            return;
        }
        //判断用户是否拥有应用权限
        boolean isTrue = judgeLimit.isOwnAppLimit(uuid, String.valueOf(ssoClientDetails.getId()));
        if (!isTrue) {
            serviceController.sendResultMap(ResultCode.USER_NO_LIMIT, Constants.TYPE, AppType.AUTH_TYPE);
            return;
        }

        //获取用户IP
        String requestIp = IpUtil.getIp();
        //生成Access Token
        String accessTokenStr = authSsoService.createAccessToken(user, expiresIn, requestIp, ssoClientDetails);

        //查询已经插入到数据库的Access Token
        SsoAccessToken ssoAccessToken = authSsoService.selectByAccessToken(accessTokenStr);
        //生成Refresh Token
        String refreshTokenStr = authSsoService.createRefreshToken(user, ssoAccessToken);
        logger.info(MessageFormat.format("单点登录获取Token：username:【{0}】,channel:【{1}】,Access Token:【{2}】,Refresh Token:【{3}】"
                , user.getAccountNumber(), ssoClientDetails.getClientName(), accessTokenStr, refreshTokenStr));

        state = URLEncoder.encode(state, "utf-8");
        String params = "?code=" + accessTokenStr + "&state=" + state;

        try {
            response.sendRedirect(redirectUri + params);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 校验Access Token，并返回用户信息
     *
     * @param request HttpServletRequest
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     */
    @RequestMapping(value = "/access_token", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> verify(HttpServletRequest request, String access_token) {
        Map<String, Object> result = new HashMap<>(8);
        //获取Access Token
        String code = request.getParameter("code");
        try {
            //过期时间
            Long expiresIn = DateUtils.dayToSecond(ExpireEnum.ACCESS_TOKEN.getTime());
            //查询Access Token
            SsoAccessToken ssoAccessToken = authSsoService.selectByAccessToken(code);
            //查询Refresh Token
            SsoRefreshToken ssoRefreshToken = authSsoService.selectByTokenId(ssoAccessToken.getId());
            //组装返回信息access_token
            result.put("access_token", ssoAccessToken.getAccessToken());
            result.put("refresh_token", ssoRefreshToken.getRefreshToken());
            result.put("expires_in", expiresIn);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            this.generateErrorResponse(result, ErrorCodeEnum.UNKNOWN_ERROR);
            return result;
        }
    }

    /**
     * 通过Refresh Token刷新Access Token
     *
     * @param request HttpServletRequest
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     */
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> refreshToken(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);

        //获取Refresh Token
        String refreshTokenStr = request.getParameter("refresh_token");
        //获取用户IP
        String requestIp = IpUtil.getIp();

        try {
            SsoRefreshToken ssoRefreshToken = authSsoService.selectByRefreshToken(refreshTokenStr);

            if (ssoRefreshToken != null) {
                Long savedExpiresAt = ssoRefreshToken.getExpiresIn();
                //过期日期
                LocalDateTime expiresDateTime = DateUtils.ofEpochSecond(savedExpiresAt, null);
                //当前日期
                LocalDateTime nowDateTime = DateUtils.now();

                //如果Refresh Token已经失效，则需要重新生成
                if (expiresDateTime.isBefore(nowDateTime)) {
                    this.generateErrorResponse(result, ErrorCodeEnum.EXPIRED_TOKEN);
                    return result;
                } else {
                    //获取存储的Access Token
                    SsoAccessToken ssoAccessToken = authSsoService.selectByAccessId(ssoRefreshToken.getTokenId());
                    //查询接入客户端
                    SsoClientDetails ssoClientDetails = new SsoClientDetails();
                    ssoClientDetails.setClientId(ssoAccessToken.getClientId());
                    ssoClientDetails = portalService.getAuthApplicationInfo(ssoClientDetails);

                    //获取对应的用户信息
                    UserInfoDomain user = userInfoService.getUserInfoByUUid(ssoAccessToken.getUserId());

                    //新的过期时间
                    Long expiresIn = DateUtils.dayToSecond(ExpireEnum.ACCESS_TOKEN.getTime());
                    //生成新的Access Token
                    String newAccessTokenStr = authSsoService.createAccessToken(user, expiresIn, requestIp, ssoClientDetails);
                    //查询用户信息
                    // UserInfoDomain user=userInfoService.getUserInfoByUUid(ssoAccessToken.getUserId());
                    //UserBo userBo = authSsoService.selectUserBoByUserId(ssoAccessToken.getUserId());

                    logger.info(MessageFormat.format("单点登录重新刷新Token：username:【{0}】,requestIp:【{1}】,old token:【{2}】,new token:【{3}】"
                            , user.getAccountNumber(), requestIp, ssoAccessToken.getAccessToken(), newAccessTokenStr));

                    //组装返回信息
                    result.put("access_token", newAccessTokenStr);
                    result.put("refresh_token", ssoRefreshToken.getRefreshToken());
                    result.put("expires_in", expiresIn);
                    result.put("user_info", user);
                    return result;
                }
            } else {
                this.generateErrorResponse(result, ErrorCodeEnum.INVALID_GRANT);
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.generateErrorResponse(result, ErrorCodeEnum.UNKNOWN_ERROR);
            return result;
        }
    }

    /**
     * 组装错误请求的返回
     */
    private void generateErrorResponse(Map<String, Object> result, ErrorCodeEnum errorCodeEnum) {
        result.put("error", errorCodeEnum.getError());
        result.put("error_description", errorCodeEnum.getErrorDescription());
    }


    /**
     * 校验Access Token，并返回用户信息
     *
     * @param request HttpServletRequest
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     */
    @RequestMapping(value = "/user")
    @ResponseBody
    public Map<String, Object> user(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>(8);
        //获取Access Token
        String accessToken = request.getHeader("Authorization");
        String str1 = accessToken.substring(6);
        SsoAccessToken ssoAccessToken = authSsoService.selectByAccessToken(str1.trim());
        if(null==ssoAccessToken){
            return sendBaseErrorMap(ACCESS_TOKEN_NOT_ESIXT);
        }
        PortalApplyInfo portalApplyInfo = new PortalApplyInfo();
        portalApplyInfo.setId(Integer.valueOf(ssoAccessToken.getClientId()));
        portalApplyInfo = portalService.selectPortalInfo(portalApplyInfo);
        if(null==portalApplyInfo){
            return sendBaseErrorMap(APPLICATION_NO_EXIT);
        }
        UserInfoDomain userInfo = userInfoService.getUserInfoByUUid(ssoAccessToken.getUserId());
        if(null==userInfo){
            return sendBaseErrorMap(USER_IS_NOT_EXIST);
        }
        String subAccount = subAccountRuleService.getSubAccountRule(userInfo, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
        if (StringUtils.isNotEmpty(subAccount) && subAccount.equals("ERROR")) {
            this.generateErrorResponse(result, ErrorCodeEnum.ACCOUNT_ERROT);
            return result;
        }
        try {
            result.put("email", userInfo.getMail());
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            this.generateErrorResponse(result, ErrorCodeEnum.UNKNOWN_ERROR);
            return result;
        }
    }
}
