package com.portal.service.impl.sso;

import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.SystemConfigInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.saml.OpenSamlImplementation;
import com.portal.saml.entity.SamlEntity;
import com.portal.service.*;
import com.portal.utils.ResponseUtils;
import com.portal.utils.SessionUtils;
import com.portal.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.*;
import static com.portal.commons.Constants.TYPE;
import static com.portal.enums.ResultCode.APPLICATION_RULE_NOT_EXIT;

/**
 * TODO:
 * create by shizhao at 2019-12-13
 *
 * @author: shizhao
 * @since: 2019-12-13 10:28
 */
@Service(value = "smmlSsoService")
public class SamlSsoServiceImpl implements SsoLoginService {

    private Logger logger = LoggerFactory.getLogger(SamlSsoServiceImpl.class);

    @Autowired
    private SsoLoginService ssoLoginService;

    @Autowired
    private SamlService samlService;


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;

    @Autowired
    private ServiceAdapter serviceController;


    @Override
    public void sendBaseInfo(String serviceUrl, String uuid, String companyUuid, String type) {
        ssoLoginService.sendBaseInfo(serviceUrl,uuid,companyUuid,type);
    }

    @Override
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {
        SystemConfigInfo systemConfigInfo = systemConfigInfoService.getSystemConfigInfo();

        try {
            serviceUrl= URLEncoder.encode(serviceUrl,"utf-8");
            serviceUrl=systemConfigInfo.getSamlLoginUrl() + "?SAMLRequest=" + serviceUrl;
            //重定向到目标地址
            Map<String, Object> map = new HashMap<>();
            map.put(RETURN_CODE, Constants.SUCCESS_CODE);
            map.put(SERVICE_URL, serviceUrl);
            map.put(TYPE,AppType.SAML_TYPE);
            //把数据返回前端
            ResponseUtils.sendResultMap(response,map);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
