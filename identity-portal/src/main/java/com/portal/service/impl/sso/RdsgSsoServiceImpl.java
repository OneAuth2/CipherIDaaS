package com.portal.service.impl.sso;

import com.portal.commons.AppType;
import com.portal.commons.Constants;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.*;
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
import static com.portal.commons.Constants.RDSG_TYPE;
import static com.portal.enums.ResultCode.PASSWORD_CONFIG_NOT_COMPLETE;
import static com.portal.enums.ResultCode.USER_NO_SUB;

/**
 * TODO:
 * create by liuying at 2019/12/10
 *
 * @author liuying
 * @since 2019/12/10 16:39
 */
@Service("rdsgSsoService")
public class RdsgSsoServiceImpl implements SsoLoginService {

    private static final Logger logger = LoggerFactory.getLogger(RdsgSsoServiceImpl.class);

    @Autowired
    private SsoLoginService ssoLoginService;

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private RedisClient<String,Object> redisClient;

    @Autowired
    private PortalToXDsgService portalToXDsgService;

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private CasAndRdsgConfigService casAndRdsgConfigService;

    @Autowired
    private HttpServletResponse response;


    @Override
    public void sendBaseInfo(String serviceUrl, String uuid, String companyUuid, String type) {
        ssoLoginService.sendBaseInfo(serviceUrl,uuid,companyUuid,type);
    }

    @Override
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {
        logger.info(MessageFormat.format("RDSG单点登录参数：serviceUrl:【{0}】", serviceUrl));
         //判断子账号是否存在
        if (StringUtils.isEmpty(serviceUrl)){
            serviceController.sendResultMap( ResultCode.APPLICATION_NO_SKIP_URL, Constants.TYPE, AppType.RDSG_TYPE);
        }

        Map<String,Object> resMap=portalToXDsgService.portalToXDsg(response,portalApplyInfo.getClientId(), userInfoDomain.getUuid(), userInfoDomain.getCompanyId());
        //设置类型1
        int type=Constants.RDSG_TYPE;
        //参数校验
        if (resMap == null){
            return ;
        }

        int returnCode= (int) resMap.get(Constants.RETURN_CODE);
        //获取返回值的returnCode
        if (returnCode == Constants.ERROR_RETURN_CODE) {
            String subAccountReal = subAccountRuleService.checkSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
            if(StringUtils.isEmpty(subAccountReal)||subAccountReal.equals("ERROR")){
                serviceController.sendBaseErrorMap(USER_NO_SUB);
            }
            String subPwdReal = subAccountRuleService.checkSubPwdRule(userInfoDomain.getUuid(), portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
            if(StringUtils.isEmpty(subPwdReal)||subPwdReal.equals("ERROR")){
                serviceController.sendBaseErrorMap(PASSWORD_CONFIG_NOT_COMPLETE);
            }
            //判断从账号是否配置成功，如果失败 直接跳转到配置从账号的界面
           serviceController.sendReturnMap(ResultCode.USER_NO_SUB,Constants.TYPE,AppType.RDSG_TYPE,"subAccount",
                    subAccountReal,
                    "subPwd",subPwdReal,"appId",String.valueOf(portalApplyInfo.getId()),"appHost",serviceUrl);
            return;
        }
        //向map中添加类型
        resMap.put(TYPE,AppType.RDSG_TYPE);
        if (returnCode != Constants.SUCCESS_CODE){
            resMap.put(Constants.TYPE,AppType.RDSG_TYPE);
            //把数据返回前端
            ResponseUtils.sendResultMap(response,serviceController.sendBaseSameMap(resMap));
            return;
        }

        String url1 = (String) resMap.get(Constants.APPLY_URL);
        //生成票据并存入redis
        String ticket = TicketUtil.getCasSTTicket();
        //存入redis中
        redisClient.put(ticket, url1);

        //生成到rdsg的地址
        String rDsgUrl=casAndRdsgConfigService.getRdsgServerUrl();
        String rdsgUrl= UrlUtils.getDynamicUrl(portalApplyInfo.getApplyUrl(),rDsgUrl);
        //生成重定向地址
        String url = UrlUtils.getRDsgUrl(rdsgUrl, ticket,url1);

        //添加返回信息的集合
        Map<String, Object> map = new HashMap<>();
        map.put(RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(SERVICE_URL, url);
        map.put(TYPE,AppType.RDSG_TYPE);
        //把数据返回前端
        ResponseUtils.sendResultMap(response,serviceController.sendBaseSameMap(map));

    }
}
