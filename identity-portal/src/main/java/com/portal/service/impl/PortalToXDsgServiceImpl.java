package com.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.portal.commons.Constants;
import com.portal.commons.HttpKey;
import com.portal.commons.ReturnCode;
import com.portal.domain.*;
import com.portal.enums.ResultCode;
import com.portal.enums.ServiceResultMap;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.*;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.portal.enums.ServiceResultMap.*;

/**
 * @Author: TK
 * @Date: 2019/7/18 19:43
 */
@Service
public class PortalToXDsgServiceImpl extends BaseMapServiceImpl implements PortalToXDsgService {

    private static Logger logger = LoggerFactory.getLogger(PortalToXDsgServiceImpl.class);


    @Autowired
    private PortalService portalService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserBehaviorService userBehaviorService;

    @Autowired
    private ApplicationAudithService applicationAudithService;

    @Autowired
    private ISessionService sessionService;

    @Autowired
    private CasAndRdsgConfigService casAndRdsgConfigService;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private RedisClient redisClient;


    @Override
    public Map<String, Object> portalToXDsg(HttpServletResponse response, String applyId, String userId, String companyId) {

        //入参校验
        if (StringUtils.isEmpty(applyId) || StringUtils.isEmpty(userId)) {
            return null;
        }

        //获取用户信息
        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userId);
        //获取根据用户uuid获取安全组字符串
        String teamIds = getTeamIds(userId);

        //获取应用信息
        PortalApplyInfo portalApplyInfo = getPortalApplyInfo(applyId);
        if (portalApplyInfo == null) {
            //返回结果
            ResponseUtils.sendResultMap(response, ServiceResultMap.NO_APPLICATION);
            return null;
        }

        //获取token以及sign
        String token = TokenUtils.getToken(userId, applyId);
        String sign = SignUtils.getSign(applyId);

        //获取从账号信息
        //构建去Xdsg的请求对象
        XdsgReqInfo xdsgReqInfo = getXdsgReqInfo(response, portalApplyInfo, userInfoDomain, teamIds, userId, applyId, companyId, token, sign);
        //xdsg为空 则应用走的不是pki类型并且账号规则或则账号密码规则不正确 直接返回前端 去绑定账号或者设置子账号
        if (xdsgReqInfo == null) {

            return null;
        }

        //获取请求结果
        Map<String, Object> resMap = getHttpResult(xdsgReqInfo, portalApplyInfo, applyId);
        //请求结果为空直接返回
        if (resMap == null) {
            logger.error("enter getHttpResult() is error and params is xdsgReqInfo,portalApplyInfo,applyId pleace check params is null", xdsgReqInfo, portalApplyInfo, applyId);
            ResponseUtils.sendResultMap(response, SYSTEM_ERROR);
            return null;
        }
        String res = (String) resMap.get(HttpKey.RES);
        if (StringUtils.isEmpty(res)) {
            logger.error("res is null", res);
            ResponseUtils.sendResultMap(response, SYSTEM_ERROR);
            return null;
        }

        ApplicationInfoDomain applicationInfoDomain = applicationService.queryApplication(applyId);
        //获取jsonObject对象
        JSONObject jsonResult = JSONObject.parseObject(res);

        //获取rdsg认证地址配置
        String rDsgUrl = casAndRdsgConfigService.getRdsgServerUrl();
        //返回请求结果给前端
        return getResMap(jsonResult, xdsgReqInfo, applicationInfoDomain, rDsgUrl);

    }


    /**
     * 根据返回参数获取从xdsg拿到的值  并返回前端
     *
     * @param jsonResult
     * @param applicationInfoDomain
     * @param rDsgUrl
     * @return
     */
    private Map<String, Object> getResMap(JSONObject jsonResult, XdsgReqInfo xdsgReqInfo, ApplicationInfoDomain applicationInfoDomain, String rDsgUrl) {
        //获取返回码并穿设置传回的值
        Integer returnCode = (Integer) jsonResult.get(Constants.RETURN_CODE);
        Map<String, Object> map = new HashMap<>();
        //请求成功但是xdsh返回错误信息
        if (returnCode.equals(ResponseStatus.FAIL) || returnCode.equals(ResponseStatus.ERROR) || returnCode.equals(ResponseStatus.PWDERROR)) {
            String returnMsg = (String) jsonResult.get(Constants.RETURN_MSG);
            map.put(Constants.RETURN_CODE, returnCode);
            map.put(Constants.RETURN_MSG, returnMsg);
            map.put(Constants.SUB_NAME, xdsgReqInfo.getSubName());
            map.put(Constants.SUB_PWD, xdsgReqInfo.getSubPwd());

            return map;
        }

        //正确请求返回applyURL
        String applyUrl = (String) jsonResult.get(Constants.APPLY_URL);
        //生成票据并存入redis
        String ticket = TicketUtil.getCasSTTicket();
//存入redis中
        redisClient.put(ticket, applyUrl);

//生成到rdsg的地址
        String rdsgUrl = UrlUtils.getDynamicUrl(applicationInfoDomain.getApplicationUrl(), rDsgUrl);
//生成重定向地址
        String url = UrlUtils.getRDsgUrl(rdsgUrl, ticket, applyUrl);
        map.put(Constants.RETURN_CODE, ResponseStatus.SUCCESS);
        map.put(Constants.APPLY_URL, applyUrl);

        return map;
    }


    /**
     * 通过httpClient的请求获取返回参数 直接返回
     * create by 安歌
     * create time 2019年7月22日11:06:04
     *
     * @param xdsgReqInfo
     * @param portalApplyInfo
     * @param applyId
     * @return
     */
    private Map<String, Object> getHttpResult(XdsgReqInfo xdsgReqInfo, PortalApplyInfo portalApplyInfo, String applyId) {

        //入参校验
        if (portalApplyInfo == null || xdsgReqInfo == null
                || StringUtils.isEmpty(portalApplyInfo.getXdsgUrl()) || StringUtils.isEmpty(xdsgReqInfo.getToken()) || StringUtils.isEmpty(xdsgReqInfo.getSign())) {

            return null;
        }
        //应用到X-DSG请求信息
        return HttpClientUtils.doPost(portalApplyInfo.getXdsgUrl() + "/cipher/checkLogin?token=" + xdsgReqInfo.getToken() + "&sign=" + xdsgReqInfo.getSign() + "&applyId=" + applyId, HttpUtils.postPwdParam(xdsgReqInfo));

    }


    /**
     * 根据传来的签名创建XDSg请求
     *
     * @param portalApplyInfo
     * @param userInfoDomain
     * @param applyId
     * @param userId
     * @return
     */
    private Boolean getSubResult(HttpServletResponse response, PortalApplyInfo portalApplyInfo, UserInfoDomain userInfoDomain, String applyId, String userId) {

        //入参校验
        if (portalApplyInfo == null || userInfoDomain == null || StringUtils.isEmpty(applyId) || StringUtils.isEmpty(userId)) {
            ResponseUtils.sendResultMap(response, NO_PARAMS_ERROR);
            return false;
        }

        //从账号规则未设置返回
        if (StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())) {
            ResponseUtils.sendResultMap(response, NO_APPLICATION_RULE);
            return false;
        }

        //获取从账号以及密码
        String subAccount = getSubAccountRule(portalApplyInfo.getAssociatedAccount(), userInfoDomain, applyId);
        String subPwd = getSubPwdRule(portalApplyInfo.getAssociatedAccount(), userId, applyId);

        //从账号未配置
        if (StringUtils.isNotEmpty(subAccount) && subAccount.equals("ERROR") || StringUtils.isNotEmpty(subPwd) && subPwd.equals("ERROR")) {
            ResponseUtils.sendResultMap(response, ResultCode.USER_NO_SUB);
            return false;
        }

        //应用从账号密码为空
        if (StringUtils.isEmpty(subAccount) || StringUtils.isEmpty(subPwd)) {
            ResponseUtils.sendResultMap(response, ResultCode.USER_NO_SUB);
            return false;
        }

        return true;
    }


    /**
     * 获取从账号规则
     *
     * @param associatedAccount
     * @param userInfo
     * @param applyId
     * @return
     */
    private String getSubAccountRule(String associatedAccount, UserInfoDomain userInfo, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        //判断是否是邮箱
        boolean isAssEmail = subAccountRuleInfo.getAssEmail() == 0 ? true : false;
        if (isAssEmail) {
            if (StringUtils.isEmpty(userInfo.getMail())) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            return userInfo.getMail();
        }
        //判断是否是邮箱前缀
        boolean isAssEmailPrefix = subAccountRuleInfo.getAssEmailPrefix() == 0 ? true : false;
        if (isAssEmailPrefix) {
            if (StringUtils.isEmpty(userInfo.getMail())) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            String mailPrefix = userInfo.getMail().substring(0, userInfo.getMail().lastIndexOf("@"));//前缀
            return mailPrefix;
        }
        //判断是否是手动配置
        boolean isAssManual = subAccountRuleInfo.getAssManual() == 0 ? true : false;
        if (isAssManual) {
            SubClientInfoDomain subClientInfoDomain = userInfoService.getSubNameInfo(userInfo.getUuid(), applyId);
            if (null == subClientInfoDomain) {
                return null;
            } else {
                return subClientInfoDomain.getSubAccount();
            }

        }
        //判断是否是主账号
        boolean isAssPrimaryAccount = subAccountRuleInfo.getAssPrimaryAccount() == 0 ? true : false;
        if (isAssPrimaryAccount) {
            if (StringUtils.isEmpty(userInfo.getAccountNumber())) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            return userInfo.getAccountNumber();
        }
        //判断是否是手机号
        boolean isAssTelephone = subAccountRuleInfo.getAssTelephone() == 0 ? true : false;
        if (isAssTelephone) {
            if (StringUtils.isEmpty(userInfo.getPhoneNumber())) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            return userInfo.getPhoneNumber();
        }
        //判断是否是工号
        boolean isAssWorkers = subAccountRuleInfo.getAssWorkers() == 0 ? true : false;
        if (isAssWorkers) {
            if (StringUtils.isEmpty(userInfo.getJob())) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            return userInfo.getJob();
        }
        return null;
    }

    /**
     * 获取密码规则
     *
     * @param associatedAccount
     * @param uuid
     * @param applyId
     * @return
     */
    private String getSubPwdRule(String associatedAccount, String uuid, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        boolean isAssPwdPrimaryAccount = subAccountRuleInfo.getAssPwdPrimaryAccount() == 0 ? true : false;
        //判断密码是否与主账号一致
        if (isAssPwdPrimaryAccount) {
            String pwd = userInfoService.getPwd(uuid);
            if (StringUtils.isEmpty(pwd)) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            pwd = AES.decryptFromBase64(pwd, AesKey.AES_KEY);
            return pwd;
        }
        //判断密码是否是手动配置
        boolean isAssPwdManual = subAccountRuleInfo.getAssPwdManual() == 0 ? true : false;
        if (isAssPwdManual) {
            SubClientInfoDomain subClientInfoDomain = userInfoService.getSubNameInfo(uuid, applyId);
            if (null == subClientInfoDomain) {
                return null;
            } else {
                return subClientInfoDomain.getPassword();
            }
        }
        return null;
    }

    /**
     * 根据用户id获取安全组拼接的字符串
     *
     * @param uuid
     * @return
     */
    private String getTeamIds(String uuid) {

        //入参校验
        if (StringUtils.isEmpty(uuid)) {

            return "";
        }

        //获取安全组ID
        List<TeamInfo> teamInfoList = userInfoService.getTeamList(uuid);
        //对返回参数校验
        if (teamInfoList == null || teamInfoList.size() == 0) {
            return "";
        }
        //自定义字符串
        String teamIds = "";
        //自定义list数组并循环向其中添加DSGTeamId
        List<String> teamList = new ArrayList<>();
        for (TeamInfo teamInfo : teamInfoList) {
            if (null != teamInfo && StringUtils.isNotEmpty(teamInfo.getDsgTeamId())) {
                teamList.add(teamInfo.getDsgTeamId());
                teamIds = org.apache.commons.lang3.StringUtils.join(teamList, ",");
            }
        }
        //返回teamIDs
        return teamIds;
    }


    /**
     * 构建到XDSG的请求
     *
     * @return
     */
    private XdsgReqInfo getXdsgReqInfo(HttpServletResponse response, PortalApplyInfo portalApplyInfo, UserInfoDomain userInfoDomain, String teamIds, String userId, String applyId, String companyUuid, String token, String sign) {
        SystemConfigInfo systemConfigInfo = systemConfigInfoService.getSystemConfigInfo();

        //新建区xdsg的对象
        XdsgReqInfo xdsgReqInfo = new XdsgReqInfo();

        //session的存活期
        String appSessionMaxAge = "";
        String appSession = sessionService.getSession(userId);

        //获取session失效时间
        appSessionMaxAge = String.valueOf(systemConfigInfo.getExpireTime());
        if (StringUtils.isNotEmpty(appSession)) {
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(appSession);
            UserSessionDomain userSessionDomain = (UserSessionDomain) net.sf.json.JSONObject.toBean(jsonobject, UserSessionDomain.class);
            Date expireTime = userSessionDomain.getExpireTime();
            appSessionMaxAge = String.valueOf(systemConfigInfo.getExpireTime() - DateUtil.getSecond(new Date(), expireTime));
        }

        //判读应用类型为pki类型新建Xdsg请求
        if (StringUtils.isNotEmpty(String.valueOf(portalApplyInfo.getApplicationType())) && portalApplyInfo.getApplicationType() == 6) {
            xdsgReqInfo = new XdsgReqInfo(portalApplyInfo.getClientSecret(), teamIds, userId, token, sign, portalApplyInfo.getClientId(), portalApplyInfo.getDsgApiUrl(), appSession, appSessionMaxAge, portalApplyInfo.getXdsgUrl());

            return xdsgReqInfo;
        }
        //其他为不走pki类型的应用
        if (getSubResult(response, portalApplyInfo, userInfoDomain, applyId, userId)) {


            xdsgReqInfo = new XdsgReqInfo(portalApplyInfo.getClientSecret(), getSubAccountRule(portalApplyInfo.getAssociatedAccount(), userInfoDomain, applyId), getSubPwdRule(portalApplyInfo.getAssociatedAccount(), userId, applyId),
                    teamIds, userId, token, sign, applyId, portalApplyInfo.getDsgApiUrl(), appSession, appSessionMaxAge, portalApplyInfo.getConfigInfo());
            xdsgReqInfo.setXdsgUrl(portalApplyInfo.getXdsgUrl()
            );
            return xdsgReqInfo;

        }

        return null;

    }

    /**
     * create by安歌
     * create time 2019-7-19 10:45:10
     * 私有方法 获取该应用是否为入库的应用
     *
     * @param applyId
     * @return
     */
    private PortalApplyInfo getPortalApplyInfo(String applyId) {

        //入参校验
        if (StringUtils.isEmpty(applyId)) {

            return null;
        }

        //创建查找条件并且返回查找结果
        PortalApplyInfo portalApplyInfo = new PortalApplyInfo();
        portalApplyInfo.setClientId(applyId);
        //查找应用绑定的portal信息
        portalApplyInfo = portalService.selectPortalInfo(portalApplyInfo);

        return portalApplyInfo;

    }
}
