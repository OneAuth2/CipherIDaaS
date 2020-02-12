package com.portal.service.impl;

import com.portal.commons.Constants;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ServiceResultMap;
import com.portal.service.*;
import com.portal.utils.ResponseUtils;
import com.portal.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.portal.enums.ResultCode.APPLICATION_NO_EXIT;

/**
 * @Author: TK
 * @Date: 2019/7/22 12:41
 */
@Service
public class ResponseRedirectServiceImpl implements ResponseRedirectService {

    private static Logger logger = LoggerFactory.getLogger(ResponseRedirectServiceImpl.class);

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private PortalService portalService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void redirctMap(Map<String, Object> map, HttpServletResponse response) {
        int returnCode = 0;
        //入参校验
        if (map == null || !map.containsKey(Constants.RETURN_CODE)) {
            logger.error("enter ResponseRedirectServiceImpl.redirctMap() error! the error is params error ! map={{}}", map);
            return;
        }
    }

    @Override
    public Boolean redirectUrl(String url, String applyId, String userId, HttpServletResponse response, String appHost) {
        //入参校验
        if (StringUtils.isEmpty(url)) {
            logger.error("enter ResponseRedirectServiceImpl.redirectUrl() error ,the error is params is null ! url={{}}", url);
            return false;
        }

        //根据uuId获取userDomain
        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userId);
        ApplicationInfoDomain applicationInfoDomain = applicationService.queryApplication(applyId);
        logger.info("applyId-------------------" + applyId);

        //获取从账号的配置规则
        PortalApplyInfo portalApplyInfo = portalService.selectPortalInfo(new PortalApplyInfo(applyId));
        logger.info("portalApplyInfo---------------------" + portalApplyInfo);
        //如果参数为空直接返回应用未绑定
        if (portalApplyInfo == null || StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())) {
            ResponseUtils.sendResultMap(response, APPLICATION_NO_EXIT, Constants.TYPE, Constants.RDSG_TYPE);
            return false;
        }
        //根据规则获取从账号
        String subAccount = subAccountRuleService.getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), applyId);
        //根据规则获取密码
        String subPwd = subAccountRuleService.getSubPwdRule(userId, portalApplyInfo.getAssociatedAccount(), applyId);

        String subAccountReal = subAccountRuleService.checkSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), applyId);

        String subPwdReal = subAccountRuleService.
                checkSubPwdRule(userInfoDomain.getUuid(), portalApplyInfo.getAssociatedAccount(), applyId);
        if (StringUtils.isEmpty(subAccountReal)) {
            subAccountReal = "";
        }
        if (StringUtils.isEmpty(subPwdReal)) {
            subPwdReal = "";
        }
        String realUrl = UrlUtils.getUrl(Constants.SUB_ACCOUNT, subAccountReal, url + "?");
        realUrl = UrlUtils.getUrl(Constants.SUB_PWD, subPwdReal, realUrl + "&");
        realUrl = UrlUtils.getUrl("appId", String.valueOf(applicationInfoDomain.getId()), realUrl + "&");
        realUrl = UrlUtils.getUrl("appHost", appHost, realUrl + "&");


        if (StringUtils.isEmpty(subAccount) || (StringUtils.isEmpty(subPwd) && portalApplyInfo.getApplicationType() != 6)) {
            //重定向到真实地址
            try {
                response.sendRedirect(realUrl);
                return false;
            } catch (IOException e) {
                logger.error("error when enter ResponseRedirectServiceImpl.redirectUrl()  response.sendRedirect error realUrl={{}}", realUrl);
                ResponseUtils.sendResultMap(response, ServiceResultMap.SYSTEM_ERROR);
                return false;
            }
        }

        return true;

    }

    @Override
    public void redirectPortalBind(String url, String applyId,String uuid,HttpServletResponse response,String appHost) {

        ApplicationInfoDomain applicationInfoDomain = applicationService.queryApplication(applyId);
        //根据uuId获取userDomain
        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(uuid);
        logger.info("applyId-------------------" + applyId);

        //获取从账号的配置规则
        PortalApplyInfo portalApplyInfo = portalService.selectPortalInfo(new PortalApplyInfo(applyId));
        logger.info("portalApplyInfo---------------------" + portalApplyInfo);
        //如果参数为空直接返回应用未绑定
        if (portalApplyInfo == null || StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())) {
            ResponseUtils.sendResultMap(response, APPLICATION_NO_EXIT, Constants.TYPE, Constants.RDSG_TYPE);
            return ;
        }

        String subAccountReal = subAccountRuleService.checkSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), applyId);

        String subPwdReal = subAccountRuleService.
                checkSubPwdRule(userInfoDomain.getUuid(), portalApplyInfo.getAssociatedAccount(), applyId);
        String realUrl = UrlUtils.getUrl(Constants.SUB_ACCOUNT, subAccountReal, url + "?");
        realUrl = UrlUtils.getUrl(Constants.SUB_PWD, subPwdReal, realUrl + "&");
        realUrl = UrlUtils.getUrl("appId", String.valueOf(applicationInfoDomain.getId()), realUrl + "&");
        realUrl = UrlUtils.getUrl("appHost", appHost, realUrl + "&");
        //重定向到真实地址
        try {
            response.sendRedirect(realUrl);
        } catch (IOException e) {
            logger.error("error when enter ResponseRedirectServiceImpl.redirectUrl()  response.sendRedirect error realUrl={{}}", realUrl);
            ResponseUtils.sendResultMap(response, ServiceResultMap.SYSTEM_ERROR);
        }
    }
}
