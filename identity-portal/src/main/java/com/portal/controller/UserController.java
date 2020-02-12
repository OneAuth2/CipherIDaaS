package com.portal.controller;




import com.portal.commons.Constants;

import com.portal.commons.ReturnCode;
import com.portal.domain.*;
import com.portal.model.SubAccountAuthModel;
import com.portal.publistener.UserBehaviorPublistener;

import com.portal.service.*;
import com.portal.token.CheckToken;
import com.portal.utils.ConstantsUrls;
import com.portal.utils.HttpUtils;
import com.portal.utils.IpUtil;
import com.portal.utils.ResponseStatus;
import com.portal.utils.aes.AES;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static com.portal.commons.Constants.*;
import static com.portal.enums.ResultCode.*;
import static com.portal.enums.ResultCode.SUCCESS;

@RestController
@RequestMapping(value = "/cipher/user")
public class UserController extends BaseController {

    private static final String SSO_LOGIN_API = "https://bi.nabeluse.com/ReportServer";

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    public static final String AES_KEY = "u13ED9Sb5UX4eUnu";


    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private GroupService groupService;


    @Autowired
    private SubAccountService subAccountService;


    @Autowired
    private ISessionService sessionService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;

    @Autowired
    private DingTalkService dingTalkService;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;



    @Autowired
    private  UserPathService userPathService;

    @Autowired
    private WxInfoService wxInfoService;






    /**
     * 获取用户信息
     *
     * @param companyUuid
     * @param uuid        (数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "/getUserInfo")
    public Map<String, Object> getUserInfo(HttpServletRequest request,
                                           @RequestParam(value = "companyUUid") String companyUuid,
                                           @RequestParam(value = "userId") String uuid) {

        //入参校验
        if (org.apache.commons.lang3.StringUtils.isBlank(companyUuid) || org.apache.commons.lang3.StringUtils.isBlank(uuid)) {
            logger.warn(" enter UserController.getUserInfo  Error As The companyUUid =[{}] uuid=[{}] ] ",
                    new Object[]{companyUuid, uuid});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        Map<String, Object> map = new HashMap<>();
        UserInfoDomain userInfoDomain = new UserInfoDomain();
        userInfoDomain.setUuid(uuid);
        UserInfoDomain user = userInfoService.queryUserInfo(userInfoDomain);

        GroupInfoDomain domain = new GroupInfoDomain();
        domain.setUserId(uuid);
        domain.setCompanyId(companyUuid);
        List<GroupInfoDomain> groupInfoList = groupService.getGroupInfoList(domain);

        String groupName = "";
        List<String> groupNameList = new ArrayList<>();
        if (null != groupInfoList && groupInfoList.size() > 0) {
            for (GroupInfoDomain groupInfoDomains : groupInfoList) {
                groupNameList.add(groupInfoDomains.getGroupName());
                groupName = StringUtils.join(groupNameList, ",");
            }
        }
        user.setGroupName(groupName);
        map.put("user", user);
        map.put("return_code", ReturnCode.SUCCESS);
        map.put("return_msg", "操作成功");
        return map;
    }


    /**
     * 获取用户密码信息
     * （数据隔离修改）
     */
    @RequestMapping(value = "/getPwdInfo")
    public Map<String, Object> getPwdInfo(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String uuid = ConstantsUrls.getConsoleSessionUuid(request);
            UserInfoDomain userInfoDomain = new UserInfoDomain();
            userInfoDomain.setUuid(uuid);
            UserInfoDomain user = userInfoService.queryUserInfo(userInfoDomain);
            if (null == user) {
                map.put("return_code", ReturnCode.FAIL);
                map.put("return_msg", "用户信息不存在");
                return map;
            }
            map.put("user", user);
            map.put("return_code", ReturnCode.SUCCESS);
            map.put("return_msg", "操作成功");
        } catch (Exception e) {
            logger.error("Enter UserController.getPwdInfo() but failed..==");
            map.put("return_code", ReturnCode.ERROR);
            map.put("return_msg", "服务器错误");
        }

        return map;
    }

    /**
     * 修改用户信息
     * (数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "/updateuser")
    @ResponseBody
    public Map<String, Object> updateuser(@RequestParam(value = "companyUUid") String companyUuid,
                                          @RequestParam(value = "userId") String uuid,
                                          @RequestParam(value = "phoneNumber") String phoneNumber,
                                          @RequestParam(value = "mail") String mail,
                                          @RequestParam(value = "userName") String userName,
                                          @RequestParam(value = "nickname") String nickname

    ) {
        //入参校验
        if (StringUtils.isEmpty(uuid)) {
            logger.warn(" enter UserController.updateuser  Error As The user =[{}] ",
                    new Object[]{uuid});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        Map<String, Object> map = new HashedMap();
        //根據uuid获取用户信息
        UserInfoDomain userInfoDomainByUuid = userInfoService.getUserInfoByUUid(uuid);

        //判断手机号是否修改
        if (!phoneNumber.equals(userInfoDomainByUuid.getPhoneNumber())) {

            //获取手机号是否已经被绑定
            UserInfoDomain userInfoDomain = userInfoService.getUserInfoByPhone(phoneNumber);
            //判断手机号是否已经被使用
            if (userInfoDomain != null) {

                return sendBaseErrorMap(PHONE_IS_EXIST);
            }
        }

        //判断邮箱是否修改
        if (!mail.equals(userInfoDomainByUuid.getMail())) {

            //获取邮箱是否已被绑定
            UserInfoDomain userInfoDomain1 = userInfoService.getUserInfoByMail(mail);
            //判断邮箱是否已经被使用
            if (userInfoDomain1 != null) {
                return sendBaseErrorMap(MAIL_IS_EXIST);
            }

        }

        UserInfoDomain user = new UserInfoDomain(uuid, nickname, mail, phoneNumber, userName);
        try {
            userInfoService.updateUserInfo(user);
        } catch (Exception e) {
            map.put("return_code", ReturnCode.ERROR);
            map.put("return_msg", "服务器错误");
            e.printStackTrace();
        }
        map.put("return_code", ReturnCode.SUCCESS);
        map.put("return_msg", "用户信息修改成功");
        return map;
    }


    @CheckToken
    @RequestMapping(value = "/getSubName")
    @ResponseBody
    public Map<String, Object> getSubName(HttpServletRequest request, @RequestParam(value = "clientId") String clientId,
                                          @RequestParam(value = "username") String username) {
        //入参校验
        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(username)) {
            logger.warn(" enter UserController.getSubName  Error As The clientId =[{}] username =[{}]",
                    new Object[]{clientId, username});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        Map<String, Object> map = new HashedMap();
        try {
            SubClientInfoDomain subClientInfoDomain = userInfoService.getSubNameInfo(username, clientId);
            if (null == subClientInfoDomain) {
                map.put("returnCode", 0);
            } else if (StringUtils.isNotEmpty(subClientInfoDomain.getSubAccount()) && StringUtils.isEmpty(subClientInfoDomain.getPassword())) {
                map.put("subName", subClientInfoDomain.getSubAccount());
                map.put("returnCode", 0);
            } else {
                map.put("subName", subClientInfoDomain.getSubAccount());
                map.put("subpwd", subClientInfoDomain.getPassword());
                map.put("returnCode", 1);
            }

        } catch (Exception e) {
            logger.error("Enter UserController.getSubName() but failed..==");

        }
        return map;
    }

    /**
     * 保存应用子账号
     * （数据隔离修改）
     */
    @CheckToken
    @RequestMapping(value = "/saveSubName", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> repwd(HttpServletRequest request,
                                     @RequestParam(value = "companyUUid") String companyUUid,
                                     @RequestParam(value = "subName") String subName,
                                     @RequestParam(value = "userId") String uuid,
                                     @RequestParam(value = "subPwd", required = false) String subpwd,
                                     @RequestParam(value = "clientId", required = false) String clientId) {


        //入参校验
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(subName) || StringUtils.isEmpty(subpwd) || StringUtils.isEmpty(clientId)) {
            logger.warn(" enter UserController.repwd  Error As The clientId =[{}] username =[{}]",
                    new Object[]{uuid, subName, subpwd, clientId});
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        Map<String, Object> map = new HashMap<>();
        SubAccountDomain subAccountDomain = null;
        try {
            if (!org.springframework.util.StringUtils.isEmpty(subName) || !org.springframework.util.StringUtils.isEmpty(clientId)) {
                SubAccountDomain form = new SubAccountDomain();
                form.setAppClientId(clientId);
                form.setSubAccount(subName.trim());
                subAccountDomain = subAccountService.getTheSubAccount(subName.trim(), clientId.trim());
                if (null == subAccountDomain) {
                    String password = AES.encryptToBase64(subpwd.trim(), AES_KEY);
                    form.setPassword(password);
                    subAccountService.insertSubAccountInfo(form);
                    SubAccountDomain subDomain = subAccountService.querySubAccountInfo(uuid, clientId);
                    if (null != subDomain) {
                        SubAccountAuthModel subAccountAuthModel = new SubAccountAuthModel();
                        subAccountAuthModel.setSubId(subDomain.getId());
                        subAccountAuthModel.setUserId(uuid);
                        subAccountService.deleteSubAccountMap(subAccountAuthModel);
                    }
                    SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
                    //获取刚插入的id
                    Integer subId = subAccountService.queryPrimaryId(subName.trim(), clientId.trim());
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
                    String password = AES.encryptToBase64(subpwd.trim(), AES_KEY);
                    subAccountDomain.setSubPwd(password);
                    subAccountService.updateSubAccountInfo(subAccountDomain);
                    SubAccountMapDomain newDomain = new SubAccountMapDomain();
                    newDomain.setUserId(uuid);
                    newDomain.setSubId(subAccountDomain.getId());
                    SubAccountDomain subDomain = subAccountService.querySubAccountInfo(uuid, clientId);
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
                map.put("return_code", ReturnCode.SUCCESS);
                map.put("return_msg", "添加从账号成功");
                return map;
            } else {
                map.put("return_code", ReturnCode.FAIL);
                map.put("return_msg", "参数错误");
                return map;
            }
        } catch (Exception e) {
            map.put("return_code", ReturnCode.FAIL);
            map.put("return_msg", "服务器错误");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        map.put("return_code", ReturnCode.SUCCESS);
        map.put("return_msg", "添加从账号成功");
        return map;
    }


    /**
     * 用户注销
     * @param companyUUid
     * @param uuid
     * */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout(@RequestParam(value = "companyUUid") String companyUUid, HttpSession session,
                                      @RequestParam(value = "userId") String uuid, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        Map<String, Object> map = new HashMap<>();
        try {
             sessionService.removeSession(uuid);
             SystemConfigInfo systemConfigInfo=systemConfigInfoService.getSystemConfigInfo();
             HttpUtils.sendPost(systemConfigInfo.getDsgServerUrl() +"/cipher/removeSession","userName="+uuid);
             session.removeAttribute(Constants.USERNAME);
             session.removeAttribute(Constants.COMPANY_UUID);
             /*Cookie cookie = new Cookie("JSESSIONID",session.getId());
             cookie.setPath(request.getContextPath()+"/");
             cookie.setMaxAge(0);
             httpServletResponse.addCookie(cookie);*/
             Cookie cookie = new Cookie("JSESSIONID",session.getId());
             cookie.setPath(request.getContextPath()+"/");
             cookie.setMaxAge(0);
             httpServletResponse.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ResponseStatus.SUCCESS);
            map.put("return_msg", "服务器错误");
        }
        try {
            UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(uuid,null,2, IpUtil.getIp(),"注销portal成功",companyUUid);
            userBehaviorPublistener.publish(userBehaviorInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("return_code", ResponseStatus.SUCCESS);
        map.put("return_msg", "注销成功");
        return map;

    }



    /**
     * 根据用户的ID获取手机号和邮箱
     */
    @PostMapping(value = "/getContactInfo")
    @ResponseBody
    public Map<String, Object> getContactInfo(HttpServletRequest request, HttpServletResponse response,
                                              @RequestParam(value = "companyUUid") String companyId,
                                              @RequestParam(value = "userId") String userId) {
        //空的信息判断
        if (isEmpty(companyId, userId)) {
            logger.info("There Is Empty Info As CompanyId=[{}],UserId=[{}]", companyId, userId);

            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        //查询用户信息
        UserInfoDomain userInfo = userInfoService.getUserInfoByUUid(userId);
        if (userInfo == null) {
            logger.info("Not Fount User = [{}]", userId);

            return sendBaseErrorMap(USER_IS_NOT_EXIST);
        }

        Map<String, Object> map = new HashMap();

        setMapParam(map, Constants.IS_MAIL_COLLECT, Constants.AUTH_ON)
                .setMapParam(map, Constants.IS_PHONE_COLLECT, Constants.AUTH_ON);

        //邮箱为空
        if (isEmpty(userInfo.getMail())) {
            setMapParam(map, Constants.IS_MAIL_COLLECT, Constants.AUTH_OFF);
        }

        //手机号为空
        if (isEmpty(userInfo.getPhoneNumber())) {
            setMapParam(map, Constants.IS_PHONE_COLLECT, Constants.AUTH_OFF);
        }


        setMapParam(map, Constants.PHONE_NUMBER, userInfo.getPhoneNumber())
                .setMapParam(map, Constants.MAIL, userInfo.getMail());


        return sendBaseNormalMap(map);
    }


    @PostMapping(value = "/getDingTalkId")
    @ResponseBody
    public Map<String, Object> getDingTalkId(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "companyUUid") String companyId) {

        DingTalkConfig dingTalkConfig = dingTalkService.getDingTalkConfigByCompanyId(companyId);
        if (dingTalkConfig == null) {
            return sendBaseErrorMap(DING_TALK_INFO_NOT_FOUND);
        }
        Map<String,Object> map = new HashMap<>();
        return setMapParam(map,DING_TALK_SCAN_ID,dingTalkConfig.getScanAppId()).
                setMapParam(map,DING_TALK_CALL_BACK_URL,dingTalkConfig.getCallbackUrl()).sendBaseNormalMap(map);
    }




    @PostMapping(value = "/getWeiXinAppId")
    @ResponseBody
    public Map<String, Object> getWeiXinAppId(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "companyUUid") String companyId) {

        WeiXinConfig weiXinConfig = wxInfoService.getWeiXinConfigInfo(companyId);

        if (weiXinConfig == null) {
            return sendBaseErrorMap(WEIXIN_CONFIG_INFO_NOT_FOUND);
        }

        Map<String,Object> map = new HashMap<>();
        return setMapParam(map,WX_APPID,weiXinConfig.getCorpId()).
                setMapParam(map,WX_STATE,weiXinConfig.getAgentId()).
                setMapParam(map,WX_REDIRET_URI,weiXinConfig.getCallbackUrl()).sendBaseNormalMap(map);
    }



    /*@PostMapping(value = "/saveCsPath")
    @ResponseBody
    public Map<String, Object> saveCsPath(HttpServletRequest request, HttpServletResponse response,
                                          @RequestParam(value = "userId") String userId,
                                          @RequestParam(value = "applyId") String appId,
                                          @RequestParam(value = "path") String path) {

        //空的信息判断
        if (isEmpty(appId, userId, path)) {
            logger.info("There Is Empty Info As appId=[{}],UserId=[{}],path=[{}]", appId, userId, path);
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }
        Map<String,Object> map=new HashMap<>();
        UserPathInfo userPathInfo=new UserPathInfo();
        userPathInfo.setUserId(userId);
        userPathInfo.setAppId(appId);
        userPathInfo=userPathService.getUserPathInfo(userPathInfo);

        if(null!=userPathInfo){
            userPathInfo.setPath(path);
            userPathService.updateUserPahtInfo(userPathInfo);
        }else {
            userPathService.insertUserPahtInfo(new UserPathInfo(userId, appId, path));
        }

        map=userPathService.getCsDate(userId,appId);
        return map;

    }

*/


    @PostMapping(value = "/saveCsPath")
    @ResponseBody
    public Map<String, Object> saveCsPath(HttpServletRequest request, HttpServletResponse response,
                                             @RequestParam(value = "userId") String userId,
                                             @RequestParam(value = "appId") String appId,
                                             @RequestParam(value = "path") String path) {


        //空的信息判断
        if (isEmpty(appId, userId, path)) {
            logger.info("There Is Empty Info As appId=[{}],UserId=[{}],path=[{}]", appId, userId, path);
            return sendBaseErrorMap(PARAMETER_FAILURE);
        }

        userPathService.insertUserPahtInfo(new UserPathInfo(userId, appId, path));
        return sendBaseNormalMap(SUCCESS);

    }
}




