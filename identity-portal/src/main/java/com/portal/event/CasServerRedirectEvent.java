package com.portal.event;

import com.portal.service.*;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * //不需要二次认证时直接重定向到cas-server
 * /**
 * * 1.是否来自cas-client,
 * * 2.来自于cas-client,
 * * 2.1 需要二次认证
 * * 不跳转，继续前端的的操作
 * * 2.2 不需要二次认证
 * * 直接重定向到cas-client
 * *
 *
 * @Author: zt
 * @Date: 2019-07-02 14:49
 */
public class CasServerRedirectEvent extends ApplicationEvent implements Serializable {

    public CasServerRedirectEvent(Object source) {
        super(source);
    }


    public CasServerRedirectEvent(  HttpServletRequest request,
                                    HttpServletResponse response, ApplicationService applicationService
                                    , JudgeLimit judgeLimit, String rDsgUrl
                                    , PortalToXDsgService portalToXDsgService,String authUrl
                                    ,ResponseRedirectService responseRedirectService) {
        super(CasServerRedirectEvent.class);
        this.request = request;
        this.response = response;
        this.applicationService = applicationService;
        this.judgeLimit = judgeLimit;
        this.rDsgUrl = rDsgUrl;
        this.portalToXDsgService = portalToXDsgService;
        this.authUrl = authUrl;
        this.responseRedirectService = responseRedirectService;
    }

    private String authUrl;

    private ResponseRedirectService responseRedirectService;

    private PortalToXDsgService portalToXDsgService;

    private ApplicationService applicationService;

    private JudgeLimit judgeLimit;

    private String rDsgUrl;

    private UserInfoService userInfoService;

    private PortalService portalService;

    private SubAccountRuleService subAccountRuleService;

    public String getrDsgUrl() {
        return rDsgUrl;
    }

    public void setrDsgUrl(String rDsgUrl) {
        this.rDsgUrl = rDsgUrl;
    }

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public PortalService getPortalService() {
        return portalService;
    }

    public void setPortalService(PortalService portalService) {
        this.portalService = portalService;
    }

    public SubAccountRuleService getSubAccountRuleService() {
        return subAccountRuleService;
    }

    public void setSubAccountRuleService(SubAccountRuleService subAccountRuleService) {
        this.subAccountRuleService = subAccountRuleService;
    }

    /**
     * 操作session
     */
    private HttpServletRequest request;

    private HttpServletResponse response;


    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public JudgeLimit getJudgeLimit() {
        return judgeLimit;
    }

    public void setJudgeLimit(JudgeLimit judgeLimit) {
        this.judgeLimit = judgeLimit;
    }


    public PortalToXDsgService getPortalToXDsgService() {
        return portalToXDsgService;
    }

    public void setPortalToXDsgService(PortalToXDsgService portalToXDsgService) {
        this.portalToXDsgService = portalToXDsgService;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public ResponseRedirectService getResponseRedirectService() {
        return responseRedirectService;
    }

    public void setResponseRedirectService(ResponseRedirectService responseRedirectService) {
        this.responseRedirectService = responseRedirectService;
    }
}
