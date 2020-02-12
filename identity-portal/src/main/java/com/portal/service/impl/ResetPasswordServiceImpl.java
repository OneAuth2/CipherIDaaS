package com.portal.service.impl;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.portal.commons.*;
import com.portal.daoAuthoriza.UserDAO;
import com.portal.domain.*;
import com.portal.publistener.UserBehaviorPublistener;
import com.portal.service.*;
import com.portal.utils.IpUtil;
import com.portal.utils.aes.AES;
import com.portal.utils.aes.AesKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private LdapService ldapService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;

    @Autowired
    private OtpDynamicInfoService otpDynamicInfoService;


    @Override
    public Map<String, Object> updateUserPwd(String uuid, String newpwd, String pwd, String companyUuid) {
        boolean result = redisTemplate.hasKey(CacheKey.getPwdByUserNameCacheKey(uuid));
        if (result == true) {
            redisTemplate.delete(CacheKey.getPwdByUserNameCacheKey(uuid));
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //修改密码
            boolean isTrue = resetAdPwd(uuid, newpwd, companyUuid);
            if (isTrue) {
                try {
                    UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(uuid, null, ConstantsCMP.UserBehaviorConstant.ACCOUNT_MAINTAIN, IpUtil.getIp(), "修改密码成功", companyUuid);
                    userBehaviorPublistener.publish(userBehaviorInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("return_code", ReturnCode.SUCCESS);
                map.put("return_msg", "修改密码成功");
            } else {
                try {
                    UserBehaviorInfo userBehaviorInfo = new UserBehaviorInfo(uuid, null, ConstantsCMP.UserBehaviorConstant.ACCOUNT_MAINTAIN, IpUtil.getIp(), "修改密码失败", companyUuid);
                    userBehaviorPublistener.publish(userBehaviorInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("return_code", ReturnCode.FAIL);
                map.put("return_msg", "修改密码失败");
            }
        } catch (Exception e) {
            map.put("return_code", ReturnCode.FAIL);
            map.put("return_msg", "修改密码失败");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public Map<String, Object> checkUserPwd(String uuid, String newpwd, String pwd, String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(companyUuid) || StringUtils.isBlank(uuid) || StringUtils.isBlank(pwd) || StringUtils.isBlank(newpwd)) {
            map.put("return_code", ReturnCode.FAIL);
            map.put("return_msg", "参数错误");
            return map;
        }

        // User user = userDAO.selectUser(uuid,companyUuid);
        String password = userDAO.getPwd(uuid);
        if (StringUtils.isNotEmpty(password)) {
            String oldpwd = AES.decryptFromBase64(password, AesKey.AES_KEY);
            if (!StringUtils.isEmpty(pwd)) {
                if (!oldpwd.equals(pwd)) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", "原密码错误，请重新输入");
                    return map;
                }
            }
        }
        int pwdType = userDAO.getPwdType();
        boolean flag = true;
        //过滤掉所有的特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(newpwd);
        newpwd = m.replaceAll("").trim();
        switch (pwdType) {
            case 1:
                flag = checkPasswordOne(newpwd);
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.EASY.getType());
                    return map;
                }
                break;
            case 2:
                flag = checkPasswordTwo(newpwd);
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.MEDIUM.getType());
                    return map;
                }
                break;
            default:
                flag = checkPasswordThird(newpwd);
                ;
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.COMPLEX.getType());
                    return map;
                }
                break;
        }

        if (flag == true) {
            map.put("return_code", ReturnCode.SUCCESS);
            map.put("return_msg", "密码检验成功");
        }
        return map;
    }

    @Override
    public Map<String, Object> setNewPassword(String companyUUid, String accountNumber, String pwd) {

        //入参校验
        if (StringUtils.isBlank(companyUUid) || StringUtils.isBlank(accountNumber) || StringUtils.isBlank(pwd)) {
            return null;
        }

        //新建Map集合
        Map<String, Object> map = new HashMap<>();

        //根据公司id获取密码策略
        int pwdType = userDAO.getPwdTypeByCompanyUuid(companyUUid);
        boolean flag = true;

        //switch循环判断属于哪个密码策略
        switch (pwdType) {

            //简单密码策略
            case 1:
                flag = checkPasswordOne(pwd);
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.EASY.getType());
                    return map;
                }
                break;
            //中等密码策略
            case 2:
                flag = checkPasswordTwo(pwd);
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.MEDIUM.getType());
                    return map;
                }
                break;
            //复杂密码侧罗
            default:
                flag = checkPasswordThird(pwd);
                if (flag == false) {
                    map.put("return_code", ReturnCode.FAIL);
                    map.put("return_msg", PasswordType.COMPLEX.getType());
                    return map;
                }
                break;
        }

        //获取全局认证策略
        GlobalAuthType globalAuthType = getGlobalAuthTypeByCompanyId(companyUUid, accountNumber);
        //修改AD域密码
        if (globalAuthType.getCode() == ConstantType.AUTH_AD) {
            GlobalReturnCode.MsgCodeEnum msgCodeEnum = ldapService.modifyPwd(accountNumber, pwd);
            if (msgCodeEnum.getCode() != 0) {
                map.put("return_code", ReturnCode.FAIL);
                map.put("return_msg", msgCodeEnum.getMsg());
                return map;
            }
        }
        //修改本地密码
        try {

            //新建修改用户对象
            UserInfoDomain newuser = new UserInfoDomain();

            //aes加密密码
            pwd = AES.encryptToBase64(pwd, AesKey.AES_KEY);

            //设置参数
            newuser.setPassword(pwd);
            newuser.setAccountNumber(accountNumber);

            //获取旧密码
            String oldPwd = userDAO.getPwd(accountNumber);

            //判断旧密码是否为空
            if (StringUtils.isEmpty(oldPwd)) {
                userDAO.insertPwd(newuser);
            } else {
                userDAO.updateUserPwd(newuser);
            }

            map.put("return_code", ReturnCode.SUCCESS);
            map.put("return_msg", "修改密码成功");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public GlobalAuthType getGlobalAuth(String companyUUid, String userId) {
        return getGlobalAuthTypeByCompanyId(companyUUid, userId);
    }

    @Override
    public GlobalAuthType getGlobalAuthByIdentity(IdentityAuthTypeDomain identityAuthTypeDomain) {

        //入参校验
        if (identityAuthTypeDomain == null) {
            return null;
        }
        return getAuthType(identityAuthTypeDomain.getAuthType());
    }

    @Override
    public GlobalAuthType getGlobalAuth(IdentityAuthTypeDomain identityAuthTypeDomain, String companyUUid, String userId) {
        return getGlobalAuthTypeByCompanyId(identityAuthTypeDomain, companyUUid, userId);
    }

    public static boolean checkPasswordOne(String password) {
        Pattern Password_Pattern = Pattern.compile("(.{6,20})$");
        Matcher matcher = Password_Pattern.matcher(password);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 配置8位及8位以上密码，至少包含数字+大写字母+小写字母
     */

    public static boolean checkPasswordThird(String value) {
        if (null == value) {
            return false;
        } else {
            String passWord = (String) value;
            if (passWord.matches("\\w+")) {
                String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
                return (passWord.length() >= 8 && passWord.length() <= 20 && passWord.matches(regex)) ? true : false;
            } else {
                return false;
            }
        }
    }


    /**
     * 配置8位及8位以上密码，至少包含数字和字母
     */

    public static boolean checkPasswordTwo(String value) {
        if (null == value) {
            return false;
        } else {
            String passWord = (String) value;
            if (passWord.matches("\\w+")) {
                String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$";
                return (passWord.length() >= 8 && passWord.length() <= 20 && passWord.matches(regex)) ? true : false;
            } else {
                return false;
            }
        }
    }

    /**
     * 获取全局的身份认证策略对象
     */
    @Override
    public IdentityAuthTypeDomain getIdentityAuthType(String companyUUid, String userId) {

        //获取该用户属于哪个最高优先级的策略
        IdentityAuthTypeDomain identityAuthTypeDomain = groupService.queryAuthTypeByUserId(userId, companyUUid);

        //获取默认的授权类型
        IdentityAuthTypeDomain defaultAuthType = groupService.queryDefaultAuthTypeByCompanyId(companyUUid);

        //判断优先级策略是否为空为空则则使用默认本地策略
        identityAuthTypeDomain = identityAuthTypeDomain == null ? defaultAuthType : identityAuthTypeDomain;

        return identityAuthTypeDomain;

    }

    @Override
    public SecondLoginInfo getSecondLoginInfo(String companyUUid, String userId) {
        //获取用户的身份认证策略
        IdentityAuthTypeDomain identityAuthTypeDomain = getIdentityAuthType(companyUUid, userId);

        //获取二次认证的方式
        String authModes = identityAuthTypeDomain.getSecondAuthType();

        //获取二次认证类型：串行和并行
        int switches=identityAuthTypeDomain.getSwitches();

        //创建二次认证对象
        SecondLoginInfo secondLoginInfo = null;

        //如果二次认证方式为空
        if (StringUtils.isEmpty(authModes)) {
            return null;
        }

        //进行格式化
        try {
            secondLoginInfo = new Gson().fromJson(authModes, SecondLoginInfo.class);

            boolean twoAuthOtp = secondLoginInfo.getTwoAuthDt()== ConstantType.CONSOLE_ON?true:false;

            if(twoAuthOtp){
               String otpKey= otpDynamicInfoService.getOtpDynamicInfo(userId);
               if(StringUtils.isNotEmpty(otpKey)){
                   secondLoginInfo.setOtpBindKey(ConstantType.OFF);
               }
            }
            secondLoginInfo.setSwitches(switches);
            return secondLoginInfo;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean resetPassword(String companyId, String userId, String password) {

        if (StringUtils.isBlank(userId)) {
            return false;
        }

        if (StringUtils.isBlank(password)) {
            return false;
        }

        UserInfoDomain userInfo = userDAO.selectUserInfoWithPasswordByUserId(userId);

        if (userInfo == null) {
            return false;
        }

        userInfo.setPassword(AES.encryptToBase64(password, AesKey.AES_KEY));

        int pwdType = userDAO.getPasswordTypeByCompanyId(companyId);

        switch (pwdType) {
            case 1:
                if (checkPasswordOne(password)) {
                    try {
                        userDAO.updateUserPwdByUuid(userInfo);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                break;
            case 2:
                if (checkPasswordTwo(password)) {
                    try {
                        userDAO.updateUserPwdByUuid(userInfo);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                break;
            case 3:
                if (checkPasswordThird(password)) {
                    try {
                        userDAO.updateUserPwdByUuid(userInfo);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
                break;
            default:
                break;
        }
        return false;
    }


    public static void main(String[] args) {
        String s = "{\"twoAuthSf\":1,\"twoAuthDd\":1,\"twoAuthDb\":1,\"twoAuthNum\":1,\"twoAuthMail\":1,\"twoAuthDt\":1}";

      //  ParallelInfo parallelInfo = new Gson().fromJson(s, ParallelInfo.class);

        SecondLoginInfo secondLoginInfo = new Gson().fromJson(s, SecondLoginInfo.class);

        SecondLoginInfo target=new SecondLoginInfo();
      //  BeanUtils.copyProperties(parallelInfo,target);

        System.out.println("secondLoginInfo---"+secondLoginInfo);
    }


    /**
     * 获取全局认证的enumeration
     */
    private GlobalAuthType getGlobalAuthTypeByCompanyId(String companyUUid, String userId) {

        //获取该用户属于哪个最高优先级的策略
        IdentityAuthTypeDomain identityAuthTypeDomain = groupService.queryAuthTypeByUserId(userId, companyUUid);


        return getGlobalAuthTypeByCompanyId(identityAuthTypeDomain, companyUUid, userId);

    }

    private GlobalAuthType getGlobalAuthTypeByCompanyId(IdentityAuthTypeDomain identityAuthTypeDomain, String companyUUid, String userId) {
        if (identityAuthTypeDomain != null && identityAuthTypeDomain.getAuthType() != null) {
            return getAuthType(identityAuthTypeDomain.getAuthType());
        }

        return getAuthType(0);
    }


    /**
     * 获取全局的认证类型
     */
    private GlobalAuthType getGlobalAuthType(String uuid, String companyUUid) {

        List<Integer> groupIdList = groupService.queryGroupByAccount(uuid);

        IdentityAuthTypeDomain defaultAuthType = groupService.queryDefaultAuthType(companyUUid);

        int defaultAuthTypeCode = defaultAuthType == null ? 0 : defaultAuthType.getAuthType();

        //该用户没有任何策略，使用默认策略
        if (CollectionUtils.isEmpty(groupIdList)) {
            return getAuthType(defaultAuthTypeCode);
        }

        IdentityAuthTypeDomain identityAuthTypeDomain = groupService.queryAuthTypeByGroupIdList(groupIdList);

        if (identityAuthTypeDomain == null) {
            return getAuthType(defaultAuthTypeCode);
        }

        int authType = identityAuthTypeDomain.getAuthType() == null ? 0 : identityAuthTypeDomain.getAuthType();
        return getAuthType(authType);
    }


    @Override
    public boolean resetAdPwd(String uuid, String newpwd, String companyUuid) {
        //获取全局认证策略
        GlobalAuthType globalAuthType = getGlobalAuthType(uuid, companyUuid);
        //修改AD域密码
        if (globalAuthType.getCode() == 1) {
            GlobalReturnCode.MsgCodeEnum msgCodeEnum = ldapService.modifyPwd(uuid, newpwd);
            if (msgCodeEnum.getCode() != 0) {
                return false;
            }
        }
        //修改本地密码
        return resetLocalPwd(uuid, newpwd);
    }


    private GlobalAuthType getAuthType(int authType) {
        switch (authType) {
            case 0:
                return GlobalAuthType.LOCALAUTH;
            case 1:
                return GlobalAuthType.ADAUTH;
            case 2:
                return GlobalAuthType.JTLERP;
            default:
                return null;
        }
    }


    public boolean resetLocalPwd(String uuid, String newpwd) {
        try {
            UserInfoDomain newuser = new UserInfoDomain();
            newpwd = AES.encryptToBase64(newpwd, AesKey.AES_KEY);
            newuser.setPassword(newpwd);
            newuser.setUserId(uuid);
            String oldPwd = userDAO.getPwd(uuid);
            if (StringUtils.isEmpty(oldPwd)) {
                userDAO.insertPwd(newuser);
            } else {
                userDAO.updateUserPwd(newuser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
