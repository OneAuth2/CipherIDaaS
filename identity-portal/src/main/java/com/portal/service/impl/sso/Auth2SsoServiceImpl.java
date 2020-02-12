package com.portal.service.impl.sso;

import com.portal.auth2.enums.ExpireEnum;
import com.portal.auth2.model.SsoClientDetails;
import com.portal.auth2.utils.DateUtils;
import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.service.*;
import com.portal.utils.IpUtil;
import com.portal.utils.ResponseUtils;
import com.portal.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.*;
import static com.portal.enums.ResultCode.USER_NO_SUB;

/**
 * TODO:
 * create by liuying at 2019/12/10
 *
 * @author liuying
 * @since 2019/12/10 17:23
 */
@Service("authSsoService")
public class Auth2SsoServiceImpl implements SsoLoginService {

    private static final Logger logger = LoggerFactory.getLogger(Auth2SsoServiceImpl.class);


    @Autowired
    private SsoLoginService ssoLoginService;

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private AuthSsoService authSsoService;

    @Autowired
    private PortalService portalService;

    @Autowired
    private HttpServletResponse response;






    @Override
    public void sendBaseInfo(String serviceUrl, String uuid, String companyUuid, String type) {
        ssoLoginService.sendBaseInfo(serviceUrl,uuid,companyUuid,type);
    }

    @Override
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {
        logger.info(MessageFormat.format("AUTH2单点登录参数：serviceUrl:【{0}】", serviceUrl));
        if (StringUtils.isEmpty(serviceUrl)){
            serviceController.sendResultMap( ResultCode.APPLICATION_NO_SKIP_URL, Constants.TYPE, AppType.AUTH_TYPE);
        }

        //根据规则获取从账号
        String subAccount = subAccountRuleService.getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
        if(StringUtils.isEmpty(subAccount)||subAccount.equals("ERROR")){
            serviceController.sendBaseErrorMap(USER_NO_SUB);
        }

        logger.info(MessageFormat.format("AUTH2单点登录重定向的Url：url:【{0}】", serviceUrl));
        //重定向到目标地址
        Map<String, Object> map = new HashMap<>();
        map.put(RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(SERVICE_URL, serviceUrl);
        map.put(TYPE,AppType.AUTH_TYPE);
        //把数据返回前端
        //把数据返回前端
        ResponseUtils.sendResultMap(response,serviceController.sendBaseSameMap(map));
    }
}
