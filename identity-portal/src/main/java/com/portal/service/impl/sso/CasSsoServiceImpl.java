package com.portal.service.impl.sso;

import com.alibaba.fastjson.JSON;
import com.cipher.china.channel.pojo.DingTalkScanAuthUser;
import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.CasUserDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.ServiceAdapter;
import com.portal.service.SsoLoginService;
import com.portal.service.SubAccountRuleService;
import com.portal.service.UserInfoService;
import com.portal.utils.ResponseUtils;
import com.portal.utils.TicketUtil;
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
 * @since 2019/12/10 14:52
 */
@Service("casSsoService")
public class CasSsoServiceImpl implements SsoLoginService {

    private static final Logger logger = LoggerFactory.getLogger(CasSsoServiceImpl.class);

    @Autowired
    private SsoLoginService ssoLoginService;

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private RedisClient<String,Object> redisClient;

    @Autowired
    private HttpServletResponse response;


    @Override
    public void sendBaseInfo(String serviceUrl, String uuid, String companyUuid, String type) {
        ssoLoginService.sendBaseInfo(serviceUrl,uuid,companyUuid,type);
    }

    @Override
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {
        logger.info(MessageFormat.format("CAS单点登录参数：serviceUrl:【{0}】", serviceUrl));
        if (StringUtils.isEmpty(serviceUrl)){
            serviceController.sendResultMap( ResultCode.APPLICATION_NO_SKIP_URL, Constants.TYPE, AppType.CAS_TYPE);
        }

        //根据规则获取从账号
        String subAccount = subAccountRuleService.getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
        if(StringUtils.isEmpty(subAccount)||subAccount.equals("ERROR")){
            serviceController.sendBaseErrorMap(USER_NO_SUB);
        }
        //生成票据
        String ticket = TicketUtil.getCasSTTicket();
        String s= JSON.toJSONString(new CasUserDomain(userInfoDomain.getUuid(),subAccount));
        //存入redis中
        redisClient.put(ticket,s, Constants.CAS_REDIS_TTL);
        //生成重定向地址
        String url = UrlUtils.getUrl(serviceUrl, ticket);

        logger.info(MessageFormat.format("CAS单点登录重定向的Url：url:【{0}】", url));
        //重定向到目标地址
        Map<String, Object> map = new HashMap<>();
        map.put(RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(SERVICE_URL, url);
        map.put(TYPE,AppType.CAS_TYPE);
        //把数据返回前端
        ResponseUtils.sendResultMap(response,serviceController.sendBaseSameMap(map));
    }
}
