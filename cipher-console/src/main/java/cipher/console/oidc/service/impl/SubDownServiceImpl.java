package cipher.console.oidc.service.impl;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.GlobalAuthType;
import cipher.console.oidc.common.SubRuleType;
import cipher.console.oidc.common.subapp.SubAppReturnKey;
import cipher.console.oidc.controller.web.SubDownController;
import cipher.console.oidc.domain.subapp.*;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.AppMapper;
import cipher.console.oidc.mapper.PasswordAuthorizationMapper;
import cipher.console.oidc.mapper.SubAccountDownMapper;
import cipher.console.oidc.mapper.SubAccountMapper;
import cipher.console.oidc.model.SubAccountAuthModel;
import cipher.console.oidc.model.SubAcountConfigModel;
import cipher.console.oidc.model.ZhOaUserModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.service.subapp.TencentEmailService;
import cipher.console.oidc.service.subapp.WangyiEmailService;
import cipher.console.oidc.service.subapp.WpsService;
import cipher.console.oidc.service.subapp.ZhOaInsertUserService;
import cipher.console.oidc.util.aes.AES;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubDownServiceImpl implements SubDownService {

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AppService appService;

    @Autowired
    private WpsService wpsService;


    @Autowired
    private TencentEmailService tencentEmailService;

    @Autowired
    private WangyiEmailService wangyiEmailService;

    @Autowired
    private SubAccountMapService subAccountMapService;

    @Autowired
    private SubAccountService subAccountService;

    @Autowired
    private ZhOaInsertUserService zhOaInsertUserService;

    @Autowired
    private SubAccountMapper subAccountMapper;

    @Autowired
    private SubAccountDownMapper subAccountDownMapper;




    private static final Logger logger = LoggerFactory.getLogger(SubDownController.class);

    @Override
    public Map<String, Object>  getSubDownInfo(String appClientId, List<AppSonAccountDomain> userList) {
        int success = 0;
        int fail = 0;
        Map<String, Object> res = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        try {
            String ruleTypeStr = "";
            //根据应用id获取应用信息
            ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(appClientId);
            //获取第一条用户信息
            String subAccount = userList.get(0).getSonAccountNumber();
            SubRuleType subRuleType = getSubRuleType(applicationInfoDomain.getApplicationType());
            ruleTypeStr = subRuleType.getType();
            if (StringUtils.isNotEmpty(subAccount)) {
                for (AppSonAccountDomain domain : userList) {
                    if (StringUtils.isNotEmpty(domain.getSonAccountNumber())) {
                        domain.setAppName(domain.getSonAccountNumber());
                    }
                }
            } else {
                if (null != applicationInfoDomain) {
                    if (StringUtils.isNotEmpty(applicationInfoDomain.getAccountType())) {
                        Integer ruleType = Integer.valueOf(applicationInfoDomain.getAccountType());
                        switch (ruleType) {
                            case 1:
                                for (AppSonAccountDomain domain : userList) {
                                    domain.setAppName(domain.getAccountNumber());
                                    ruleTypeStr = SubRuleType.SAIFU_NUMBER.getType();
                                }
                                break;
                            case 2:
                                for (AppSonAccountDomain domain : userList) {
                                    domain.setAppName(domain.getMail());
                                    ruleTypeStr = SubRuleType.MAIL.getType();
                                }
                                break;
                            case 3:
                                for (AppSonAccountDomain domain : userList) {
                                    String mail = domain.getMail().substring(0, domain.getMail().indexOf("@"));
                                    domain.setAppName(mail);
                                    ruleTypeStr = SubRuleType.MAIL_PRIX.getType();
                                }
                            case 4:
                                for (AppSonAccountDomain domain : userList) {
                                    domain.setAppName(domain.getPhoneNumber());
                                    ruleTypeStr = SubRuleType.PHONE.getType();
                                }
                                break;
                            case 5:
                                for (AppSonAccountDomain domain : userList) {
                                    String jobNo = domain.getJobNo();
                                    domain.setJobNo(jobNo);
                                    ruleTypeStr = SubRuleType.JOB_NO.getType();
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            AppSonAccountDomain appSonAccountDomain = new AppSonAccountDomain();
            appSonAccountDomain.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
            //获取应用下发子账号规则
            AllRuleInfoDomain allRuleInfoDomain = appMapper.getRuleInfo(String.valueOf(applicationInfoDomain.getId()));
            if (null != allRuleInfoDomain) {
                String ruleValue = allRuleInfoDomain.getRuleValue();
                List<SubAcountConfigModel> ruleList = new Gson().fromJson(ruleValue, new TypeToken<List<SubAcountConfigModel>>() {
                }.getType());
                // Integer appType = SubDownEnum.getSubDownTypeEnum(appClientId);
                String password = "";
                if (StringUtils.isNotEmpty(String.valueOf(applicationInfoDomain.getAppSysId()))) {
                    Integer appType = applicationInfoDomain.getAppSysId();
                    if (StringUtils.isNotEmpty(subAccount)) {
                        //针对单个应用下发取他输入的账号
                        password = userList.get(0).getPassword();
                    } else {
                        //不是当个应用的账号取应用设置的初始密码
                        password = allRuleInfoDomain.getInitPwd();
                    }
                    switch (appType) {
                        case 5:
                            //腾讯邮箱子账号下发
                            List<TencentCreateSubReqDomain> tencentCreateSubReqDomainList = convertTencentCreateSubReq(userList, ruleList);
                            if (null != tencentCreateSubReqDomainList && tencentCreateSubReqDomainList.size() > 0) {
                                for (TencentCreateSubReqDomain tencentCreateSubReqDomain : tencentCreateSubReqDomainList) {
                                    logger.info("创建腾讯邮箱账号信息:" + tencentCreateSubReqDomain);
                                    tencentCreateSubReqDomain.setPassword(password);
                                    res = checkTencentEmail(tencentCreateSubReqDomain, appClientId);
                                    JSONObject jsonObject = JSONObject.fromObject(res);
                                    int errcode = jsonObject.getInt("ERR_CODE");
                                    logger.info("创建腾讯邮箱账号返回的json:" + errcode);
                                    if (errcode == 0) {
                                        success++;
                                        if (applicationInfoDomain.getIsSameAccount() == 0) {
                                            SubAccountInfoDomain form = new SubAccountInfoDomain();
                                            form.setAccountNumber(tencentCreateSubReqDomain.getAccountNumber());
                                            form.setUserId(tencentCreateSubReqDomain.getUserId());
                                            form.setPassword(password);
                                            form.setAppClientId(appClientId);
                                            form.setAppId(applicationInfoDomain.getId());
                                            form.setSubAccount(tencentCreateSubReqDomain.getUserid());
                                            map = doSaveSub(form);
                                        }
                                        map.put("return_code", ConstantsCMP.Code.SUCCESS);
                                        map.put("return_msg", "配置子账号成功");
                                    } else {
                                        map.put("return_code", ConstantsCMP.Code.FAIL);
                                        map.put("return_msg", "当前应用已配置：从账号与" + ruleTypeStr + "同步规则,从账号下发失败");
                                        fail++;
                                    }
                                }
                            }
                            else {
                                map.put("return_code", ConstantsCMP.Code.FAIL);
                                map.put("return_msg", "应用信息配置有误");
                                return map;
                            }
                            break;
                        case 1:
                            //网易邮箱子账号下发
                            List<WangyiCreateSubInfoDomain> wangyiCreateSubReqDomainList = convertWangyiCreateSubReq(userList, ruleList);
                            if (null != wangyiCreateSubReqDomainList && wangyiCreateSubReqDomainList.size() > 0) {
                                for (WangyiCreateSubInfoDomain wangyiCreateSubInfoDomain : wangyiCreateSubReqDomainList) {
                                    logger.info("创建网易邮箱账号信息:" + wangyiCreateSubInfoDomain);
                                    WangyiCreateSubReqDomain wangyiCreateSubReqDomain = new WangyiCreateSubReqDomain();
                                    try {
                                        BeanUtils.copyProperties(wangyiCreateSubReqDomain, wangyiCreateSubInfoDomain);
                                    }catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    wangyiCreateSubReqDomain.setPassword(password);
                                    res = checkWangyiEmail(wangyiCreateSubReqDomain, appClientId);
                                    JSONObject jsonObject = JSONObject.fromObject(res);
                                    int errcode = jsonObject.getInt("ERR_CODE");
                                    logger.info("创建网易邮箱账号返回的json:" + errcode);
                                    if (errcode == 0) {
                                        success++;
                                        if (applicationInfoDomain.getIsSameAccount() == 0) {
                                            SubAccountInfoDomain form = new SubAccountInfoDomain();
                                            form.setAccountNumber(wangyiCreateSubInfoDomain.getAccountNumber());
                                            form.setPassword(password);
                                            form.setUserId(wangyiCreateSubInfoDomain.getUserId());
                                            form.setAppClientId(appClientId);
                                            form.setAppId(applicationInfoDomain.getId());
                                            form.setSubAccount(wangyiCreateSubInfoDomain.getAccount_name());
                                            map = doSaveSub(form);
                                        }
                                        map.put("return_code", ConstantsCMP.Code.SUCCESS);
                                        map.put("return_msg", "配置子账号成功");
                                    } else {
                                        map.put("return_code", ConstantsCMP.Code.FAIL);
                                        map.put("return_msg", "当前应用已配置：从账号与" + ruleTypeStr + "同步规则,从账号下发失败");
                                        fail++;
                                    }
                                }
                            }
                            else {
                                map.put("return_code", ConstantsCMP.Code.FAIL);
                                map.put("return_msg", "应用信息配置有误");
                                return map;
                            }
                            break;
                        case 6:
                            //金山云子账号下发
                            if (null != userList && userList.size() > 0) {
                                for (AppSonAccountDomain userInfoDomain : userList) {
                                    if (StringUtils.isEmpty(userInfoDomain.getGender())) {
                                        userInfoDomain.setGenderStr(0);
                                    } else if (userInfoDomain.getGender().equals("女")) {
                                        userInfoDomain.setGenderStr(2);
                                    } else {
                                        userInfoDomain.setGenderStr(1);
                                    }
                                }
                            }
                            List<WpsCreateUserInfoDomain> wpsCreateSubReqDomainList = convertWpsCreateSubReq(userList, ruleList);
                            if (null != wpsCreateSubReqDomainList && wpsCreateSubReqDomainList.size() > 0) {
                                for (WpsCreateUserInfoDomain wpsCreateUserInfoDomain : wpsCreateSubReqDomainList) {
                                    logger.info("创建金山云账号信息:" + wpsCreateUserInfoDomain);
                                    WpsCreateUserReqDomain wpsCreateUserReqDomain = new WpsCreateUserReqDomain();
                                    try {
                                        BeanUtils.copyProperties(wpsCreateUserReqDomain, wpsCreateUserInfoDomain);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                    wpsCreateUserReqDomain.setPassword(password);
                                    res = checkWpsAccount(wpsCreateUserReqDomain, appClientId);
                                    JSONObject jsonObject = JSONObject.fromObject(res);
                                    int errcode = jsonObject.getInt("ERR_CODE");
                                    logger.info("创建金山云账号信息:" + errcode);
                                    if (errcode == 0) {
                                        success++;
                                        if (applicationInfoDomain.getIsSameAccount() == 0) {
                                            SubAccountInfoDomain form = new SubAccountInfoDomain();
                                            form.setAccountNumber(wpsCreateUserInfoDomain.getAccountNumber());
                                            form.setPassword(password);
                                            form.setUserId(wpsCreateUserInfoDomain.getUserId());
                                            form.setAppClientId(appClientId);
                                            form.setAppId(applicationInfoDomain.getId());
                                            form.setSubAccount(wpsCreateUserReqDomain.getUnique_name());
                                            map = doSaveSub(form);
                                        }
                                        map.put("return_code", ConstantsCMP.Code.SUCCESS);
                                        map.put("return_msg", "配置子账号成功");
                                    } else {
                                        map.put("return_code", ConstantsCMP.Code.FAIL);
                                        map.put("return_msg", "当前应用已配置：从账号与" + ruleTypeStr + "同步规则,从账号下发失败");
                                        fail++;
                                    }
                                }
                            }
                            else {
                                map.put("return_code", ConstantsCMP.Code.FAIL);
                                map.put("return_msg", "应用信息配置有误");
                                return map;
                            }
                            break;
                        case 7:
                            //众合OA子账号下发
                            List<ZhOaUserModel> zhOaUserDomainList = convertZhOaUserCreateSubReq(userList, ruleList);
                            if (null != zhOaUserDomainList && zhOaUserDomainList.size() > 0) {
                                for (ZhOaUserModel zhOaUserInfoDomain : zhOaUserDomainList) {
                                    logger.info("创建众合OA账号信息--------------:" + zhOaUserInfoDomain);
                                    ZhOaUserModel zhOaUserReqDomain = new ZhOaUserModel();
                                    try {
                                        BeanUtils.copyProperties(zhOaUserReqDomain, zhOaUserInfoDomain);
                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                        //TODO OA系统下发子账号默认为取主账号前缀
                                        String username=zhOaUserInfoDomain.getAccountNumber();
                                        if (username.contains("@")) {
                                            username=username.substring(0, username.indexOf("@"));
                                            zhOaUserInfoDomain.setUsername(username);
                                        }

                                    res = checkZhOaAccount(zhOaUserInfoDomain, appClientId);
                                    JSONObject jsonObject = JSONObject.fromObject(res);
                                    int errcode = jsonObject.getInt("ERR_CODE");
                                    logger.info("创建众合OA账号信息:" + errcode);
                                    if (errcode == 0) {
                                        success++;
                                        //从账号账号和主张不一致
                                            SubAccountInfoDomain form = new SubAccountInfoDomain();
                                            form.setAccountNumber(zhOaUserReqDomain.getAccountNumber());
                                            form.setPassword(password);
                                            form.setUserId(zhOaUserReqDomain.getUserId());
                                            form.setAppClientId(appClientId);
                                            form.setAppId(applicationInfoDomain.getId());
                                            form.setSubAccount(zhOaUserReqDomain.getUsername());
                                            map = doSaveSub(form);
                                        map.put("return_code", ConstantsCMP.Code.SUCCESS);
                                        map.put("return_msg", "配置子账号成功");
                                    } else {
                                        String  msg = jsonObject.getString("ERR_MSG");
                                        map.put("return_code", ConstantsCMP.Code.FAIL);
                                        map.put("return_msg", "当前应用已配置：从账号与" + ruleTypeStr + "同步规则,从账号下发失败," + msg);
                                        fail++;
                                    }
                                }
                            }else {
                                map.put("return_code", ConstantsCMP.Code.FAIL);
                                map.put("return_msg", "应用信息配置有误");
                                return map;
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                //子账号保存到本地系统
                if (applicationInfoDomain.getIsSameAccount() == 0) {
                    for (AppSonAccountDomain appSonAccount : userList) {
                        SubAccountInfoDomain form = new SubAccountInfoDomain();
                        form.setAccountNumber(appSonAccount.getAccountNumber());
                        form.setPassword(appSonAccount.getPassword());
                        form.setAppClientId(appClientId);
                        form.setSubAccount(appSonAccount.getSonAccountNumber());
                        form.setUserId(appSonAccount.getUserId());
                        form.setAppId(applicationInfoDomain.getId());
                        map = doSaveSub(form);
                        JSONObject jsonObject = JSONObject.fromObject(map);
                        int return_code = jsonObject.getInt("return_code");
                        if (return_code == 0) {
                            success++;
                            map.put("return_code", ConstantsCMP.Code.SUCCESS);
                            map.put("return_msg", "配置成功");
                        } else {
                            String returnMsg = jsonObject.getString("return_msg");
                            map.put("return_code", ConstantsCMP.Code.FAIL);
                            map.put("return_msg", returnMsg);
                            fail++;
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }

        map.put("successed", success);
        map.put("fail", fail);
        return map;
    }

    @Override
    public Map<String, Object> doSaveSubAccount(SubAccountInfoDomain form) {
        return doSaveSubAccountInfo(form);
    }


    //腾讯邮箱字段映射
    private List<TencentCreateSubReqDomain> convertTencentCreateSubReq(List<AppSonAccountDomain> userList, List<SubAcountConfigModel> subAcountConfigModelList) {
        List<TencentCreateSubReqDomain> tencentCreateSubReqList = new ArrayList<>();
        for (AppSonAccountDomain appSonAccountDomain : userList) {
            try {
                TencentCreateSubReqDomain tencentCreateSubReq = new TencentCreateSubReqDomain();
                for (SubAcountConfigModel subAcountConfigModel : subAcountConfigModelList) {
                    if (StringUtils.isNotEmpty(subAcountConfigModel.getLocalValue())) {
                        Field adField = appSonAccountDomain.getClass().getDeclaredField(subAcountConfigModel.getLocalValue());
                        adField.setAccessible(true);
                        if (adField.get(appSonAccountDomain) != null) {
                            Field userField = tencentCreateSubReq.getClass().getDeclaredField(subAcountConfigModel.getSubKey());
                            userField.setAccessible(true);
                            userField.set(tencentCreateSubReq, adField.get(appSonAccountDomain));
                        }
                    }

                }

                Field adField = appSonAccountDomain.getClass().getDeclaredField("uuid");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = tencentCreateSubReq.getClass().getDeclaredField("userId");
                    userField.setAccessible(true);
                    userField.set(tencentCreateSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("accountNumber");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = tencentCreateSubReq.getClass().getDeclaredField("accountNumber");
                    userField.setAccessible(true);
                    userField.set(tencentCreateSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("appName");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = tencentCreateSubReq.getClass().getDeclaredField("userid");
                    userField.setAccessible(true);
                    userField.set(tencentCreateSubReq, adField.get(appSonAccountDomain));
                }
                //过滤掉没有userPrincipalName的账号，如电脑主机
                if (appSonAccountDomain.getAccountNumber() != null) {
                    tencentCreateSubReqList.add(tencentCreateSubReq);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return tencentCreateSubReqList;
    }


    //网易邮箱字段映射
    private List<WangyiCreateSubInfoDomain> convertWangyiCreateSubReq(List<AppSonAccountDomain> userList, List<SubAcountConfigModel> subAcountConfigModelLit) {
        List<WangyiCreateSubInfoDomain> wangyiCreateSubReqList = new ArrayList<>();
        for (AppSonAccountDomain appSonAccountDomain : userList) {
            try {
                WangyiCreateSubInfoDomain wangyiCreateSubReq = new WangyiCreateSubInfoDomain();
                for (SubAcountConfigModel subAcountConfigModel : subAcountConfigModelLit) {
                    if (StringUtils.isNotEmpty(subAcountConfigModel.getLocalValue())) {
                        Field adField = appSonAccountDomain.getClass().getDeclaredField(subAcountConfigModel.getLocalValue());
                        adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                        if (adField.get(appSonAccountDomain) != null) {
                            Field userField = wangyiCreateSubReq.getClass().getDeclaredField(subAcountConfigModel.getSubKey());
                            userField.setAccessible(true);
                            userField.set(wangyiCreateSubReq, adField.get(appSonAccountDomain));
                        }
                    }
                }
                Field adField = appSonAccountDomain.getClass().getDeclaredField("accountNumber");
                adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wangyiCreateSubReq.getClass().getDeclaredField("accountNumber");
                    userField.setAccessible(true);
                    userField.set(wangyiCreateSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("uuid");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wangyiCreateSubReq.getClass().getDeclaredField("userId");
                    userField.setAccessible(true);
                    userField.set(wangyiCreateSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("appName");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wangyiCreateSubReq.getClass().getDeclaredField("account_name");
                    userField.setAccessible(true);
                    userField.set(wangyiCreateSubReq, adField.get(appSonAccountDomain));
                }
                //过滤掉没有userPrincipalName的账号，如电脑主机
                if (appSonAccountDomain.getAccountNumber() != null) {
                    wangyiCreateSubReqList.add(wangyiCreateSubReq);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return wangyiCreateSubReqList;
    }


    //金山WPS字段映射
    private List<WpsCreateUserInfoDomain> convertWpsCreateSubReq(List<AppSonAccountDomain> userList, List<SubAcountConfigModel> subAcountConfigModelLit) {
        List<WpsCreateUserInfoDomain> wpsCreateUserInfoDomainList = new ArrayList<>();
        for (AppSonAccountDomain appSonAccountDomain : userList) {
            try {
                WpsCreateUserInfoDomain wpsCreateUserInfoDomain = new WpsCreateUserInfoDomain();
                for (SubAcountConfigModel subAcountConfigModel : subAcountConfigModelLit) {
                    if (StringUtils.isNotEmpty(subAcountConfigModel.getLocalValue())) {
                        Field adField = appSonAccountDomain.getClass().getDeclaredField(subAcountConfigModel.getLocalValue());
                        adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                        if (adField.get(appSonAccountDomain) != null) {
                            Field userField = wpsCreateUserInfoDomain.getClass().getDeclaredField(subAcountConfigModel.getSubKey());
                            userField.setAccessible(true);
                            userField.set(wpsCreateUserInfoDomain, adField.get(appSonAccountDomain));
                        }
                    }
                }
                Field adField = appSonAccountDomain.getClass().getDeclaredField("accountNumber");
                adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wpsCreateUserInfoDomain.getClass().getDeclaredField("accountNumber");
                    userField.setAccessible(true);
                    userField.set(wpsCreateUserInfoDomain, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("uuid");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wpsCreateUserInfoDomain.getClass().getDeclaredField("userId");
                    userField.setAccessible(true);
                    userField.set(wpsCreateUserInfoDomain, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("appName");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = wpsCreateUserInfoDomain.getClass().getDeclaredField("unique_name");
                    userField.setAccessible(true);
                    userField.set(wpsCreateUserInfoDomain, adField.get(appSonAccountDomain));
                }
                //过滤掉没有userPrincipalName的账号，如电脑主机
                if (appSonAccountDomain.getAccountNumber() != null) {
                    wpsCreateUserInfoDomainList.add(wpsCreateUserInfoDomain);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return wpsCreateUserInfoDomainList;
    }


    //众合OA系统字段映射
    private List<ZhOaUserModel> convertZhOaUserCreateSubReq(List<AppSonAccountDomain> userList, List<SubAcountConfigModel> subAcountConfigModelLit) {
        List<ZhOaUserModel> zhOaUserCreateSubReqList = new ArrayList<>();
        for (AppSonAccountDomain appSonAccountDomain : userList) {
            try {
                ZhOaUserModel zhOaUserSubReq = new ZhOaUserModel();
                for (SubAcountConfigModel subAcountConfigModel : subAcountConfigModelLit) {
                    if (StringUtils.isNotEmpty(subAcountConfigModel.getLocalValue())) {
                        Field adField = appSonAccountDomain.getClass().getDeclaredField(subAcountConfigModel.getLocalValue());
                        adField.setAccessible(true);
  //                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                        if (adField.get(appSonAccountDomain) != null) {
                            Field userField = zhOaUserSubReq.getClass().getDeclaredField(subAcountConfigModel.getSubKey());
                            userField.setAccessible(true);
                            userField.set(zhOaUserSubReq, adField.get(appSonAccountDomain));
                        }
                    }
                }
                Field adField = appSonAccountDomain.getClass().getDeclaredField("accountNumber");
                adField.setAccessible(true);
//                    LOGGER.info("字段名：" + adField.getName() + "<=====>值：" + adField.get(ldapUser));
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = zhOaUserSubReq.getClass().getDeclaredField("accountNumber");
                    userField.setAccessible(true);
                    userField.set(zhOaUserSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("uuid");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = zhOaUserSubReq.getClass().getDeclaredField("userId");
                    userField.setAccessible(true);
                    userField.set(zhOaUserSubReq, adField.get(appSonAccountDomain));
                }

                adField = appSonAccountDomain.getClass().getDeclaredField("userName");
                adField.setAccessible(true);
                if (adField.get(appSonAccountDomain) != null) {
                    Field userField = zhOaUserSubReq.getClass().getDeclaredField("username");
                    userField.setAccessible(true);
                    userField.set(zhOaUserSubReq, adField.get(appSonAccountDomain));
                }
                if (appSonAccountDomain.getAccountNumber() != null) {
                    zhOaUserCreateSubReqList.add(zhOaUserSubReq);
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {

                e.printStackTrace();
            }

        }
        return zhOaUserCreateSubReqList;
    }





    //腾讯邮箱账号下发
    public Map<String, Object> checkTencentEmail(TencentCreateSubReqDomain tencentCreateSubReqDomain, String appClientId) {
        Map<String, Object> newMap = new HashMap<>();
        ApplicationInfoDomain applicationInfoDomain = appService.queryApplication(appClientId);
        if (null != applicationInfoDomain) {
            String configInfo = applicationInfoDomain.getConfigInfo();
            JSONObject jsonobject = JSONObject.fromObject(configInfo);
            TengXunConfigInfoDomain configInfoDomain = (TengXunConfigInfoDomain) JSONObject.toBean(jsonobject, TengXunConfigInfoDomain.class);
            logger.info("腾讯邮箱id" + configInfoDomain.getCropId() + "腾讯邮箱secret" + configInfoDomain.getDeptMngId());
            String accessToken = tencentEmailService.getAccessToken(configInfoDomain.getCropId(), configInfoDomain.getDeptMngId());
            if (null != accessToken) {
                TencentDepDomain tencentDepDomain = tencentEmailService.qeryDept(accessToken, 1L);
                List<TencentDepDomain.DepartmentBean> department = tencentDepDomain.getDepartment();
                List<Long> departmentId = new ArrayList<>();
                if(department.size()>0){
                    departmentId.add(department.get(0).getId());
                }else{
                    departmentId.add(1L);
                }
                tencentCreateSubReqDomain.setDepartment(departmentId);
                if (StringUtils.isNotEmpty(tencentCreateSubReqDomain.getGender()) && tencentCreateSubReqDomain.getGender().equals("男")) {
                    tencentCreateSubReqDomain.setGender("1");
                } else {
                    tencentCreateSubReqDomain.setGender("2");
                }
                newMap = tencentEmailService.createSubAccount(accessToken, tencentCreateSubReqDomain);
            } else {
                newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
                newMap.put(SubAppReturnKey.errMsg, "获取accessToken失败");
            }
        }
        return newMap;
    }


    //网易邮箱账号下发
    public Map<String, Object> checkWangyiEmail(WangyiCreateSubReqDomain wangyiCreateSubReqDomain, String appClientId) {
        Map<String, Object> newMap = new HashMap<>();
        ApplicationInfoDomain applicationInfoDomain = appService.queryApplication(appClientId);
        if (null != applicationInfoDomain) {
            String configInfo = applicationInfoDomain.getConfigInfo();
            JSONObject jsonobject = JSONObject.fromObject(configInfo);
            WangyiConfigInfoDomain configInfoDomain = (WangyiConfigInfoDomain) JSONObject.toBean(jsonobject, WangyiConfigInfoDomain.class);
            if (null != configInfoDomain) {
                logger.info("网易邮箱域名" + configInfoDomain.getDomain() + "网易邮箱secret" + configInfoDomain.getSecret() + "网易标识" + configInfoDomain.getProduct());
                wangyiCreateSubReqDomain.setDomain(configInfoDomain.getDomain());
                wangyiCreateSubReqDomain.setProduct(configInfoDomain.getProduct());
                wangyiCreateSubReqDomain.setAddr_right("0");
                wangyiCreateSubReqDomain.setAddr_visible("0");
                wangyiCreateSubReqDomain.setPass_type("0");
                newMap = wangyiEmailService.createSubAccount(wangyiCreateSubReqDomain, configInfoDomain.getSecret());
            } else {
                newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
                newMap.put(SubAppReturnKey.errMsg, "获取网易邮箱基本配置信息失败");
            }

        }
        return newMap;
    }


    //金山云账号下发
    public Map<String, Object> checkWpsAccount(WpsCreateUserReqDomain wpsCreateUserReqDomain, String appClientId) {
        Map<String, Object> newMap = new HashMap<>();
        ApplicationInfoDomain applicationInfoDomain = appService.queryApplication(appClientId);
        if (null != applicationInfoDomain) {
            String configInfo = applicationInfoDomain.getConfigInfo();
            JSONObject jsonobject = JSONObject.fromObject(configInfo);
            WpsConfigInfoDomain configInfoDomain = (WpsConfigInfoDomain) JSONObject.toBean(jsonobject, WpsConfigInfoDomain.class);
            if (null != configInfoDomain) {
                logger.info("金山云apiid" + configInfoDomain.getApiId() + "金山云apiidsecret" + configInfoDomain.getSecretKey() + "金山云appid" + configInfoDomain.getAppId() +
                        "金山云accessId" + configInfoDomain.getAccessId() + "金山云sessionId" + configInfoDomain.getSessionId());
                WpsCommonReqDomin wpsCommonReqDomin = new WpsCommonReqDomin();
                wpsCommonReqDomin.setAccessId(configInfoDomain.getAccessId());
                wpsCommonReqDomin.setApiPre(configInfoDomain.getApiId());
                wpsCommonReqDomin.setAppId(configInfoDomain.getAppId());
                wpsCommonReqDomin.setSecret(configInfoDomain.getSecretKey());
                wpsCommonReqDomin.setSsoApiPre(configInfoDomain.getSessionId());
                wpsCommonReqDomin.setCompanyId("2");
                newMap = wpsService.createUser(wpsCommonReqDomin, wpsCreateUserReqDomain, 2L);
            } else {
                newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
                newMap.put(SubAppReturnKey.errMsg, "获取金山配置信息失败");
            }
        }
        return newMap;
    }


    //众合OA账号下发
    public Map<String, Object> checkZhOaAccount(ZhOaUserModel zhOaUserReqDomain, String appClientId) {
        Map<String, Object> newMap = new HashMap<>();
        try {
            ApplicationInfoDomain applicationInfoDomain = appService.queryApplication(appClientId);
            if (null != applicationInfoDomain) {
                boolean isTrue = zhOaInsertUserService.insertAccount(zhOaUserReqDomain);
                if (isTrue) {
                    newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.SUCCESS);
                    newMap.put(SubAppReturnKey.errMsg, "下发OA成功");
                } else {
                    newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
                    newMap.put(SubAppReturnKey.errMsg, "下发OA账号失败");
                }
            } else {
                newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
                newMap.put(SubAppReturnKey.errMsg, "获取众合OA信息失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            newMap.put(SubAppReturnKey.errCode, ConstantsCMP.Code.FAIL);
            newMap.put(SubAppReturnKey.errMsg, "服务器错误");
        }
        return newMap;
    }


    /*
    * 保存从账号信息
    * */

    public Map<String, Object> doSaveSubAccountInfo(SubAccountInfoDomain form) {
        Map<String, Object> map = new HashMap<>();
        SubAccountDomain subAccountDomain = null;
        String password="";
        try {
            if(StringUtils.isEmpty(form.getSubAccount())){
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "参数错误");
                return map;
            }
            if (null != form && !org.springframework.util.StringUtils.isEmpty(form.getSubAccount()) && !org.springframework.util.StringUtils.isEmpty(form.getAppClientId())) {
                subAccountDomain = subAccountService.getTheSubAccount(form.getSubAccount(), form.getAppClientId());
                if (null == subAccountDomain) {
                    if(StringUtils.isNotEmpty(form.getPassword())) {
                        password = AES.encryptToBase64(form.getPassword(), ConstantsCMP.AES_KEY);
                    }
                    form.setPassword(password);
                    int subId = subAccountService.insertSubAccountInfo(form);
                    SubAccountDomain subDomain = subAccountMapper.querySubAccountInfo(form.getUserId(), form.getAppClientId());
                    if (null != subDomain) {
                        SubAccountAuthModel subAccountAuthModel = new SubAccountAuthModel();
                        subAccountAuthModel.setSubId(subDomain.getId());
                        subAccountAuthModel.setAccountNumber(form.getAccountNumber());
                        subAccountAuthModel.setUuid(form.getUserId());
                        subAccountMapper.deleteSubAccountMap(subAccountAuthModel);
                    }
                    SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
                    subAccountMapDomain.setSubId(form.getId());
                    subAccountMapDomain.setUserId(form.getUserId());
                    subAccountMapDomain.setAccountNumber(form.getAccountNumber());
                    SubAccountMapDomain domain = subAccountMapService.querySubMap(subAccountMapDomain);
                    if (null == domain) {
                        subAccountMapService.insertSubAccountMap(subAccountMapDomain);
                    }
                } else {
                    if(StringUtils.isNotEmpty(form.getPassword())) {
                        password = AES.encryptToBase64(form.getPassword(), ConstantsCMP.AES_KEY);
                    }
                    subAccountDomain.setPassword(password);
                    subAccountService.updateSubAccountInfo(subAccountDomain);
                    SubAccountMapDomain newDomain = new SubAccountMapDomain();
                    newDomain.setAccountNumber(form.getAccountNumber());
                    newDomain.setUserId(form.getUserId());
                    newDomain.setSubId(subAccountDomain.getId());
                    SubAccountDomain subDomain = subAccountMapper.querySubAccountInfo(form.getUserId(), form.getAppClientId());
                    if (null != subDomain) {
                        SubAccountAuthModel subAccountAuthModel = new SubAccountAuthModel();
                        subAccountAuthModel.setSubId(subDomain.getId());
                        subAccountAuthModel.setUuid(form.getUserId());
                        subAccountAuthModel.setAccountNumber(form.getAccountNumber());
                        subAccountMapper.deleteSubAccountMap(subAccountAuthModel);
                    }
                    SubAccountMapDomain domain = subAccountMapService.querySubMap(newDomain);
                    if (null == domain) {
                        subAccountMapService.insertSubAccountMap(newDomain);
                    }
                }
                map.put("return_code", ConstantsCMP.Code.SUCCESS);
                map.put("return_msg", "配置成功");
            } else {
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "配置失败");
            }
        } catch (Exception e) {
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "配置失败");
            e.printStackTrace();
        }
        return map;
    }



    /*
     * 保存从账号信息
     * */

    public Map<String, Object> doSaveSub(SubAccountInfoDomain form) {
        Map<String, Object> map = doSaveSubAccountInfo(form);
        JSONObject jsonObject = JSONObject.fromObject(map);
        int returnCode = jsonObject.getInt("return_code");
        if (returnCode == 0) {
            SubAccountDownDomain subAccountDownDomain=new SubAccountDownDomain();
            subAccountDownDomain.setUserId(form.getUserId());
            subAccountDownDomain.setAppId(form.getAppId());
            SubAccountDownDomain record= subAccountDownMapper.getSubAccountDownInfo(subAccountDownDomain);
            if(null==record){
                subAccountDownMapper.insertSubAccountDownInfo(subAccountDownDomain);
            }

        }
        return map;
    }






    private SubRuleType getSubRuleType(int authType) {
        switch (authType) {
            case 1:
                return SubRuleType.SAIFU_NUMBER;
            case 2:
                return SubRuleType.PHONE;
            case 3:
                return SubRuleType.MAIL;
            default:
                return SubRuleType.MAIL_PRIX;
        }
    }



}

