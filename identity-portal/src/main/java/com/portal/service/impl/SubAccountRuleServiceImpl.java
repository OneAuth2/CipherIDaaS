package com.portal.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.portal.commons.ApiGateWayType;
import com.portal.commons.ConstantsCMP;
import com.portal.commons.HttpKey;
import com.portal.commons.ReturnCode;
import com.portal.daoAuthoriza.PortalDAO;
import com.portal.daoAuthoriza.UserDAO;
import com.portal.domain.*;
import com.portal.enums.SubAccountEnum;
import com.portal.model.SubAccountAuthModel;
import com.portal.publistener.UserBehaviorPublistener;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.*;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import com.portal.utils.rsa.RSATool;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.portal.enums.ResultCode.PORTAL_SYSTEM_ERROR;
import static com.portal.utils.aes.AesKey.AES_KEY;


@Service
public class SubAccountRuleServiceImpl implements SubAccountRuleService {

    private static final Logger logger = LoggerFactory.getLogger(SubAccountRuleServiceImpl.class);


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PortalDAO portalDAO;

    @Autowired
    private ISessionService sessionService;

    @Autowired
    private SubAccountService subAccountService;

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ApplicationAudithService applicationAudithService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;

    @Autowired
    private RedisClient<String, Object> redisClient;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;





    @Override
    public String getSubAccountRule(UserInfoDomain userInfo, String associatedAccount, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        return getSubAccount(userInfo, subAccountRuleInfo, applyId);
    }


    @Override
    public String getSubPwdRule(String userId, String associatedAccount, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        return getSubPwd(userId, subAccountRuleInfo, applyId);
    }


    @Override
    public Map<String, Object> getSubAccountInfo(UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {
        return sendApplyRequest(userInfoDomain, portalApplyInfo);
    }

    @Override
    public Map<String, Object> chenckSubAccountAndPwd(String userId, PortalApplyInfo portalApplyInfo, String subName, String subPwd) {
        return checkSubAccountAndPwd(userId, portalApplyInfo, subName, subPwd);
    }

    @Override
    public RealParamDomain getRealParamInfo(String url) {
        return RealParamUtils.getRealParam(url);
    }

    @Override
    public Map<String, Object> saveSubAccount(String subName, String subPwd, String applyId, String uuid) {
        Map<String, Object> map = new HashMap<>();
        SubAccountDomain subAccountDomain = null;
        try {
            SubAccountDomain form = new SubAccountDomain();
            form.setAppClientId(applyId);
            form.setSubAccount(subName.trim());
            subAccountDomain = subAccountService.getTheSubAccount(subName.trim(), applyId.trim());
            if (null == subAccountDomain) {
                String password = AES.encryptToBase64(subPwd.trim(), AES_KEY);
                form.setPassword(password);
                subAccountService.insertSubAccountInfo(form);
                SubAccountDomain subDomain = subAccountService.querySubAccountInfo(uuid, applyId);
                if (null != subDomain) {
                    SubAccountAuthModel subAccountAuthModel = new SubAccountAuthModel();
                    subAccountAuthModel.setSubId(subDomain.getId());
                    subAccountAuthModel.setUserId(uuid);
                    subAccountService.deleteSubAccountMap(subAccountAuthModel);
                }
                SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
                //获取刚插入的id
                Integer subId = subAccountService.queryPrimaryId(subName.trim(), applyId.trim());
                form.setId(subId);
                if (null != form.getId()) {
                    subAccountMapDomain.setSubId(form.getId());
                    subAccountMapDomain.setUserId(uuid);
                    SubAccountMapDomain domain = subAccountService.querySubMap(subAccountMapDomain);
                    if (null == domain) {
                        subAccountService.insertSubAccountMap(subAccountMapDomain);
                    }
                }

            } else {
                String password = AES.encryptToBase64(subPwd.trim(), AES_KEY);
                subAccountDomain.setSubPwd(password);
                subAccountService.updateSubAccountInfo(subAccountDomain);
                SubAccountMapDomain newDomain = new SubAccountMapDomain();
                newDomain.setUserId(uuid);
                newDomain.setSubId(subAccountDomain.getId());
                SubAccountDomain subDomain = subAccountService.querySubAccountInfo(uuid, applyId);
                if (null != subDomain) {
                    SubAccountAuthModel subAccountAuthModel = new SubAccountAuthModel();
                    subAccountAuthModel.setSubId(subDomain.getId());
                    subAccountAuthModel.setUserId(uuid);
                    subAccountService.deleteSubAccountMap(subAccountAuthModel);
                }
                SubAccountMapDomain domain = subAccountService.querySubMap(newDomain);
                if (null == domain) {
                    subAccountService.insertSubAccountMap(newDomain);
                }
            }

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return serviceController.sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }
        return serviceController.sendTheMap(SubAccountEnum.SUCCESS.getCode(), "保存成功");
    }

    @Override
    public String checkSubAccountRule(UserInfoDomain user, String associatedAccount, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        boolean isAssManual = subAccountRuleInfo.getAssManual() == 0 ? true : false;
        String subAccount = "";
        if (isAssManual) {
            return "";
        }
        subAccount = getSubAccount(user, subAccountRuleInfo, applyId);
        return subAccount;
    }

    @Override
    public String checkSubPwdRule(String userId, String associatedAccount, String applyId) {
        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(associatedAccount);
        SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) net.sf.json.JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
        String subPwd = "";
        boolean isAssPwdManual = subAccountRuleInfo.getAssPwdManual() == 0 ? true : false;
        if (isAssPwdManual) {
            return "";
        }
        subPwd = getSubPwd(userId, subAccountRuleInfo, applyId);
        return subPwd;
    }


    //第一次获取子账号信息
    public String getSubAccount(UserInfoDomain userInfo, SubAccountRuleInfo subAccountRuleInfo, String applyId) {
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
            SubClientInfoDomain subClientInfoDomain = userDAO.getSubNameInfo(userInfo.getUuid(), applyId);
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


    //第一次获取子账号密码
    public String getSubPwd(String userId, SubAccountRuleInfo subAccountRuleInfo, String applyId) {
        boolean isAssPwdPrimaryAccount = subAccountRuleInfo.getAssPwdPrimaryAccount() == 0 ? true : false;
        //判断密码是否与主账号一致
        if (isAssPwdPrimaryAccount) {
            String pwd = userDAO.getPwd(userId);
            if (StringUtils.isEmpty(pwd)) {
                return ReturnCode.SUB_RULE_ERROR;
            }
            pwd = AES.decryptFromBase64(pwd, AES_KEY);
            return pwd;
        }
        //判断密码是否是手动配置
        boolean isAssPwdManual = subAccountRuleInfo.getAssPwdManual() == 0 ? true : false;
        if (isAssPwdManual) {
            SubClientInfoDomain subClientInfoDomain = userDAO.getSubNameInfo(userId, applyId);
            if (null == subClientInfoDomain) {
                return null;
            } else {
                String pwd = AES.decryptFromBase64(subClientInfoDomain.getPassword(), AesKey.AES_KEY);
                return pwd;
            }
        }
        return null;
    }


    //获取安全组ID
    public String getTeamIds(String userId) {
        String teamIds = "";
        List<TeamInfo> teamInfoList = userDAO.getTeamList(userId);
        List<String> teamList = new ArrayList<>();
        if (null != teamInfoList && teamInfoList.size() > 0) {
            for (TeamInfo teamInfo : teamInfoList) {
                if (null != teamInfo && StringUtils.isNotEmpty(teamInfo.getDsgTeamId())) {
                    teamList.add(teamInfo.getDsgTeamId());
                    teamIds = org.apache.commons.lang3.StringUtils.join(teamList, ",");
                }
            }
        }
        return teamIds;
    }


    //获取session失效时间
    public String getSessionMaxAge(String appSession, int ttl) {
        String appSessionMaxAge;
        if (StringUtils.isNotEmpty(appSession)) {
            net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(appSession);
            UserSessionDomain userSessionDomain = (UserSessionDomain) net.sf.json.JSONObject.toBean(jsonobject, UserSessionDomain.class);
            Date expireTime = userSessionDomain.getExpireTime();
            appSessionMaxAge = String.valueOf(ttl - DateUtil.getSecond(new Date(), expireTime));
        } else {
            appSessionMaxAge = String.valueOf(ttl);
        }
        return appSessionMaxAge;
    }


    //创建token
    private static String getJetToken(String userName) {
        String token = JWT.create().withAudience(userName).sign(Algorithm.HMAC256("cipherchina"));
        return token;
    }


    //拼接xdsg参数
    private List<NameValuePair> postPwdParam(XdsgReqInfo xdsgReqInfo) {
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("subName", xdsgReqInfo.getSubName()));
        nameValuePairList.add(new BasicNameValuePair("subPwd", xdsgReqInfo.getSubPwd()));
        nameValuePairList.add(new BasicNameValuePair("appKey", xdsgReqInfo.getAppKey()));
        nameValuePairList.add(new BasicNameValuePair("userName", xdsgReqInfo.getUserName()));
        nameValuePairList.add(new BasicNameValuePair("apiDsgUrl", xdsgReqInfo.getApiDsgUrl()));
        nameValuePairList.add(new BasicNameValuePair("teamIds", xdsgReqInfo.getTeamIds()));
        nameValuePairList.add(new BasicNameValuePair("sign", xdsgReqInfo.getSign()));
        nameValuePairList.add(new BasicNameValuePair("app_session", xdsgReqInfo.getAppSession()));
        nameValuePairList.add(new BasicNameValuePair("app_session_max_age", xdsgReqInfo.getAppSessionMaxAge()));
        nameValuePairList.add(new BasicNameValuePair("app_token", xdsgReqInfo.getToken()));
        nameValuePairList.add(new BasicNameValuePair("config", xdsgReqInfo.getConfig()));
        nameValuePairList.add(new BasicNameValuePair("xdsgUrl", xdsgReqInfo.getXdsgUrl()));
        return nameValuePairList;
    }


    //创建XDSG基本信息
    private XdsgReqInfo getXdsgReqInfo(PortalApplyInfo portalApplyInfo, String userId) {
        String appSession = sessionService.getSession(userId);
        SystemConfigInfo systemConfigInfo=systemConfigInfoService.getSystemConfigInfo();
        XdsgReqInfo xdsgReqInfo = new XdsgReqInfo(portalApplyInfo.getClientSecret(), getTeamIds(userId), userId, getJetToken(userId),
                RSATool.generateSHA1withRSASigature(portalApplyInfo.getClientId(), RSATool.PRI_KEY), portalApplyInfo.getClientId(), portalApplyInfo.getDsgApiUrl(), appSession, getSessionMaxAge(appSession, systemConfigInfo.getExpireTime()), portalApplyInfo.getXdsgUrl());
        return xdsgReqInfo;
    }


    //应用到X-DSG请求信息
    public Map<String, Object> sendXdsgRequest(XdsgReqInfo xdsgReqInfo, PortalApplyInfo portalApplyInfo, String userId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = HttpClientUtils.doPost(portalApplyInfo.getXdsgUrl() + "/cipher/checkLogin?token=" + getJetToken(userId) + "&sign=" + RSATool.encryptWithPubKey(portalApplyInfo.getClientId(), RSATool.PUB_KEY) + "&applyId=" + portalApplyInfo.getClientId(), postPwdParam(xdsgReqInfo));
        String res = (String) resultMap.get(HttpKey.RES);
        if (StringUtils.isEmpty(res)) {
            serviceController.sendBaseErrorMap(PORTAL_SYSTEM_ERROR);
        }
        JSONObject jsonResult = JSONObject.parseObject(res);
        Integer returnCode = (Integer) jsonResult.get("return_code");
        AppAuditInfo appAuditInfo = null;

        if (returnCode.equals(ResponseStatus.FAIL) || returnCode.equals(ResponseStatus.ERROR) || returnCode.equals(ResponseStatus.PWDERROR)) {
            String returnMsg = (String) jsonResult.get("return_msg");
            UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userId);
            String subAccount = checkSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
            String subPwd = checkSubPwdRule(userId, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());
            map.put("subName", subAccount);
            map.put("subPwd", subPwd);
            serviceController.sendTheMap(map, returnCode, returnMsg);
            //应用日志
            try {
                String userInfo = userInfoDomain.getUserName() + "(" + userInfoDomain.getAccountNumber() + ")";
                appAuditInfo = new AppAuditInfo(Integer.valueOf(portalApplyInfo.getId()),
                        userInfo, 1, xdsgReqInfo.getSubName() + "登录子系统" + portalApplyInfo.getApplyName() + "失败", userInfoDomain.getCompanyId());
                applicationAudithService.insertAppAuditInfo(appAuditInfo);
                //用户日志
                UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(userId, null, ConstantsCMP.UserBehaviorConstant.APPLICATION_VISIT, IpUtil.getIp(), xdsgReqInfo.getSubName() + "登录子系统" + portalApplyInfo.getApplyName() + "失败", userInfoDomain.getCompanyId());
                userBehaviorPublistener.publish(userBehaviorInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String applyUrl = (String) jsonResult.get("applyUrl");
            Integer apiGateWayType = (Integer) jsonResult.get("apiGateWayType");
            //判断应用是否走rdsg
            if (apiGateWayType == ApiGateWayType.API || apiGateWayType == ApiGateWayType.NO_API) {
                //生成票据并存入redis
                String ticket = TicketUtil.getCasSTTicket();
                //存入redis中
                redisClient.put(ticket, applyUrl);
                //生成重定向地址并重定向rdsg其中携带ticket
                String rdgUrl = getRDsgUrl(portalApplyInfo.getDsgApiUrl(), ticket, applyUrl);
                map.put("applyUrl", rdgUrl);

            } else {
                map.put("applyUrl", applyUrl);
            }
            serviceController.sendTheMap(map, returnCode, SubAccountEnum.SUCCESS.getMessage());

            //应用日志
            try {
                UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userId);
                String userInfo = userInfoDomain.getUserName() + "(" + userInfoDomain.getAccountNumber() + ")";
                appAuditInfo = new AppAuditInfo(Integer.valueOf(portalApplyInfo.getId()),
                        userInfo, 1, xdsgReqInfo.getSubName() + "登录子系统" + portalApplyInfo.getApplyName() + "成功", userInfoDomain.getCompanyId());
                applicationAudithService.insertAppAuditInfo(appAuditInfo);
                //用户日志
                UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(userId, null, ConstantsCMP.UserBehaviorConstant.APPLICATION_VISIT, IpUtil.getIp(), xdsgReqInfo.getSubName() + "登录子系统" + portalApplyInfo.getApplyName() + "成功",  userInfoDomain.getCompanyId());
                userBehaviorPublistener.publish(userBehaviorInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    //第一次点击应用
    public Map<String, Object> sendApplyRequest(UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {

        Map<String, Object> map = new HashMap<>();

        XdsgReqInfo xdsgReqInfo = getXdsgReqInfo(portalApplyInfo, userInfoDomain.getUuid());

        if (StringUtils.isEmpty(String.valueOf(portalApplyInfo.getApplicationType()))) {
            return serviceController.sendTheMap(SubAccountEnum.APPLICATION_TYPE_LOST.getCode(), SubAccountEnum.APPLICATION_TYPE_LOST.getMessage());
        }
        //根据应用规则获取从账号信息
        String subAccount = getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());

        //应用从账号信息缺失
         if (StringUtils.isEmpty(subAccount)){
             map.put("subName", subAccount);
             map.put("subPwd", null);
             return serviceController.sendTheMap(map, SubAccountEnum.NULL.getCode(), SubAccountEnum.NULL.getMessage());
         }

        if(StringUtils.isNotEmpty(subAccount) && subAccount.equals("ERROR")){
            return serviceController.sendTheMap(SubAccountEnum.SUBACCOUNT_LOST.getCode(), SubAccountEnum.SUBACCOUNT_LOST.getMessage());
        }
        //判断应用是否需要密码
        if (portalApplyInfo.getApplicationType()!=3 && portalApplyInfo.getApplicationType()!=6 && portalApplyInfo.getApplicationType()!=7 &&portalApplyInfo.getApplicationType()!=10 &&portalApplyInfo.getApplicationType()!=11) {

            String subPwd = getSubPwdRule(userInfoDomain.getUuid(), portalApplyInfo.getAssociatedAccount(), portalApplyInfo.getClientId());

            //应用从账号密码缺失
            if (StringUtils.isEmpty(subPwd)){
                map.put("subName", subAccount);
                map.put("subPwd", subPwd);
                return serviceController.sendTheMap(map, SubAccountEnum.NULL.getCode(), SubAccountEnum.NULL.getMessage());
            }

            if (StringUtils.isNotEmpty(subPwd) && subPwd.equals("ERROR")) {
                return serviceController.sendTheMap(SubAccountEnum.SUBACCOUNT_LOST.getCode(), SubAccountEnum.SUBACCOUNT_LOST.getMessage());
            }
            xdsgReqInfo.setSubPwd(subPwd);

        }

        //CAS 、AUTH2、SMML应用类型不走xdsg
        if(portalApplyInfo.getApplicationType()==7 ||portalApplyInfo.getApplicationType()==10 ||portalApplyInfo.getApplicationType()==11){
            map.put("return_code",ReturnCode.SUCCESS);
            map.put("applyUrl",portalApplyInfo.getApplyUrl());
            return map;
        }
        //RDSG,代理，API-SSO,PKI应用走xdg
        xdsgReqInfo.setSubName(subAccount);
        xdsgReqInfo.setConfig(portalApplyInfo.getConfigInfo());
        map = sendXdsgRequest(xdsgReqInfo, portalApplyInfo, userInfoDomain.getUuid());
        return map;

    }


    //检验应用的子账号密码是否正确
    public Map<String, Object> checkSubAccountAndPwd(String userId, PortalApplyInfo portalApplyInfo, String subName, String subPwd) {
        XdsgReqInfo xdsgReqInfo = getXdsgReqInfo(portalApplyInfo, userId);
        xdsgReqInfo.setSubName(subName);
        xdsgReqInfo.setSubPwd(subPwd);
        Map<String, Object> map = sendXdsgRequest(xdsgReqInfo, portalApplyInfo, userId);
        return map;

    }


    /**
     * 生成rDsg的地址
     */
    public static String getRDsgUrl(String rdsgUrl, String ticket, String appUrl) {
        //定义网络类型取出协议  端口   ip地址
        String finalUrl = "";
        String protocol = "";
        int port = 0;
        String ip = "";
        String uri = null;
        String sfApp = "";
        if (StringUtils.isNotEmpty(appUrl)) {
            GetParamUtil.UrlEntity parse = GetParamUtil.parse(appUrl);
            parse.getParams();
            sfApp = parse.getParams().get("sfApp");
        }
        //获取协议 端口 ip地址的值
        try {
            URL destinationUrl = new URL(rdsgUrl);
            protocol = destinationUrl.getProtocol();
            port = destinationUrl.getPort();
            ip = destinationUrl.getHost();
            uri = destinationUrl.getPath();
        } catch (MalformedURLException e) {
            logger.error("new URL() is Error the error is");
            logger.error(e.getMessage(), e);
        }

        //设置最终finalUrl
        finalUrl = protocol + "://" + ip + ":" + port + uri + "?ticket=" + ticket + "&sfApp=" + sfApp;

        //判断端口为-1 默认端口为80
        if (port == -1) {
            finalUrl = protocol + "://" + ip + uri + "?ticket=" + ticket + "&sfApp=" + sfApp;
        }

        return finalUrl;
    }


    public static void main(String[] args) {
        String s = "http://192.168.1.20:8088?teamId=pY8r8210&sfApp=禅道内网测试&appKey=lR8G53&sessionTime=18000&ssoUri=/chandaoLogin&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiZTIyYjA0NjZiMTQxMWU5YjVhZjAwMTYzZTAwY2M2YSJ9.0sJuDPsuiqSd1HALhDrK1a8TFgBGSVGYxygTezTkiLo&getParam=[{\"key\":\"cipherParam\",\"value\":\"9B48FC6577FB94CF515713F6A74A1907EC24B14A6AC330D28E1A8E88AFEC4746D1DDF7D7C359BDFD77356972C3D1A40274ED6AA8DD4359E3BA10332F391C705A807CA0DA66A1ADDAC2FA235386F7EC8B2B94B2E78E91FFC4D04154A83A81421C\"},{\"key\":\"clientId\",\"value\":\"1l4esJ\"},{\"key\":\"protocol\",\"value\":\"http\"},{\"key\":\"host\",\"value\":\"192.168.1.181\"},{\"key\":\"port\",\"value\":\"8088\"}]";
        RealParamDomain realParamDomain = RealParamUtils.getRealParam(s);
        System.out.println(realParamDomain);
    }


}
