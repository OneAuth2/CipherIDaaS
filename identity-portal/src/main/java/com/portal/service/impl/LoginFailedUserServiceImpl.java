package com.portal.service.impl;


import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.portal.commons.CacheKey;
import com.portal.commons.ConstantsCMP;
import com.portal.daoAuthoriza.LoginFailedConfigDAO;
import com.portal.daoAuthoriza.UserBehaviorInfoDAO;
import com.portal.domain.*;
import com.portal.enums.LoginFailedValidateEnum;
import com.portal.enums.TimeTypeEnum;
import com.portal.redis.RedisClient;
import com.portal.service.LoginFailedUserService;
import com.portal.service.UserInfoService;
import com.portal.utils.DateUtil;
import com.portal.utils.JasonUtil;
import com.portal.utils.MapUtil;
import edu.hziee.common.lang.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * IUserService的实现层
 * modified by sizhao 2018/6/13
 *
 * @author Jason、sizhao
 *
 * @see {@link  }
 */
@Service
public class LoginFailedUserServiceImpl implements LoginFailedUserService {
    private final Logger logger= LoggerFactory.getLogger(LoginFailedUserServiceImpl.class);


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private LoginFailedConfigDAO loginFailedConfigDAO;

    @Autowired
    private UserBehaviorInfoDAO userBehaviorInfoDAO;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<String, Object>();

    @Override
    public LoginFailedConfigEntity getLoginFailedConfig() {
        Object obj = redisClient.get(CacheKey.getLoginFailedConfigCacheKey());
        if (obj == null) {
            obj = loginFailedConfigDAO.queryLoginFailedUser();
            if (obj == null) {
                obj = new NullCacheObject();
            } else {
                obj = JasonUtil.object2JSONString(obj);
            }
            redisClient.put(CacheKey.getLoginFailedConfigCacheKey(), obj);
        }

        if (obj == null || obj instanceof NullCacheObject) {
            return null;
        }

        obj = JasonUtil.jasonString2Object((String) obj, LoginFailedConfigEntity.class);

        return (LoginFailedConfigEntity) obj;
    }

    @Override
    public UserLoginFailedInfo getUserLoginFailedInfo(String account) {
        Object obj = redisClient.get(CacheKey.getUserLoginFailedInfoCacheKey(account));
        if (obj == null || obj instanceof NullCacheObject) {
            return null;
        }

        // 缓存中存放的是Map对象，将Map对象的数据设置到UserLoginFailedInfo对象中
        Map<String, Object> userLoginFailedInfoMap = (HashMap<String, Object>) obj;
        UserLoginFailedInfo userLoginFailedInfo = new UserLoginFailedInfo();
        userLoginFailedInfo.setAccount((String) userLoginFailedInfoMap.get("account"));
        userLoginFailedInfo.setFailedNum((Integer) userLoginFailedInfoMap.get("failedNum"));
        userLoginFailedInfo.setFirstFailedTime((Date) userLoginFailedInfoMap.get("firstFailedTime"));
        userLoginFailedInfo.setFreezed(userLoginFailedInfoMap.get("isFreezed") == null ? false : (Boolean) userLoginFailedInfoMap.get("isFreezed"));

        return userLoginFailedInfo;
    }



    @Override
    public void updateUserLoginFailedInfo(UserLoginFailedInfo userLoginFailedInfo, String account) {
        if (userLoginFailedInfo == null) {// 第一次登录失败
            userLoginFailedInfo = new UserLoginFailedInfo();
            userLoginFailedInfo.setAccount(account);
            userLoginFailedInfo.setFirstFailedTime(new Date());
            userLoginFailedInfo.setFailedNum(1); // 初始化1次
        }

        // 转成Map, 避免缓存对象序列化的问题
        redisClient.put(CacheKey.getUserLoginFailedInfoCacheKey(account), MapUtil.objectToMap(userLoginFailedInfo));
    }

    /**
     * 判断用户是否被锁定 true-锁定，false-未锁定
     */
    @Override
    public boolean isUserFreezed(String account) {
        if (StringUtil.isBlank(account)) {
            logger.warn("Enter UserServiceImpl.isUserFreezed(), but the account is null..==");
            return false;
        }

        // 1、获取系统配置参数
        LoginFailedConfigEntity loginFailedConfig = this.getLoginFailedConfig();
        if (loginFailedConfig == null) {
            logger.warn("Enter UserServiceImpl.isUserFreezed(), but the loginFailedConfig is null..==");
            return false;
        }

        // 2、获取系统功能开关
        int status = loginFailedConfig.getStatus();
        if (status == LoginFailedValidateEnum.CLOSE.getType()) {// 功能不需要判断
            return false;
        }

        // 3、获取用户登录失败信息
        UserLoginFailedInfo userLoginFailedInfo = this.getUserLoginFailedInfo(account);
        if (userLoginFailedInfo == null) {
            logger.warn("Enter UserServiceImpl.isUserFreezed(), but the userLoginFailedInfo is null, account=[{}]..==");
            return false;
        }

        if (!(userLoginFailedInfo.isFreezed())) {// 未被锁定
            return false;
        }

        return true;
    }

    @Override
    public void loginFailedValidate(String account,String ip) {
        if (StringUtil.isBlank(account)) {
            logger.warn("Enter UserServiceImpl.loginFailedValidate(), but the account is null..==");
            return;
        }

        // 1、获取系统配置参数
        LoginFailedConfigEntity loginFailedConfig = this.getLoginFailedConfig();
        if (loginFailedConfig == null) {
            logger.warn("Enter UserServiceImpl.loginFailedValidate(), but the loginFailedConfig is null..==");
            return;
        }

        // 2、获取系统功能开关
        int status = loginFailedConfig.getStatus();
        if (status == LoginFailedValidateEnum.CLOSE.getType()) {// 功能关闭不需要处理
            return;
        }

        // 3、获取用户登录失败次数信息
        UserLoginFailedInfo userLoginFailedInfo = this.getUserLoginFailedInfo(account);
        if (userLoginFailedInfo == null) {// 第一次登录失败的用户
            // 更新数据
            this.updateUserLoginFailedInfo(userLoginFailedInfo, account);
            return;
        }

        // 4、获取第一次登录失败时间，为空就初始化
        Date firstFailedTime = userLoginFailedInfo.getFirstFailedTime();
        if (firstFailedTime == null) {
            firstFailedTime = new Date();
        }

        Date currentTime = new Date();
        double timeInterval = DateUtil.calTimeInterval(firstFailedTime, currentTime, TimeTypeEnum.MINUTE);
        int freezingTime = loginFailedConfig.getFreezingTime(); // 冻结时间

        if (userLoginFailedInfo.isFreezed()) {// 冻结的用户
            if (timeInterval > freezingTime) {// 冻结的用户，超过冻结时间就归零
                userLoginFailedInfo = new UserLoginFailedInfo();
                userLoginFailedInfo.setAccount(account);
                userLoginFailedInfo.setFirstFailedTime(new Date());
                userLoginFailedInfo.setFailedNum(1); // 初始化1次
            }
        } else {// 非冻结用户，在时间范围内，计算登录失败次数，超出时间范围就归零
            int timeRange = loginFailedConfig.getTimeRange(); // 时间范围判断
            if (timeInterval > timeRange) {// 超出时间范围，登录次数变成1
                userLoginFailedInfo = new UserLoginFailedInfo();
                userLoginFailedInfo.setAccount(account);
                userLoginFailedInfo.setFirstFailedTime(new Date());
                userLoginFailedInfo.setFailedNum(1); // 初始化1次
            } else {// 没有超出时间范围的，计算并更新登录失败次数
                userLoginFailedInfo.setAccount(account);
                userLoginFailedInfo.setFailedNum(userLoginFailedInfo.getFailedNum() + 1); // 失败次数+1，暂时不用考虑并发情况
                if (userLoginFailedInfo.getFailedNum() >= loginFailedConfig.getFailCount()) {// 失败次数大于设定就冻结用户
                    // 冻结用户
                    redisClient.put(CacheKey.getCacheUserIsfreezed(account),true);
                    userLoginFailedInfo.setFreezed(true);
                }

            }
        }

        // 更新数据
        this.updateUserLoginFailedInfo(userLoginFailedInfo, account);
    }

    @Override
    public void clearUserLoginFailedInfo(String account) {
        // 获取用户登录失败信息
        UserLoginFailedInfo userLoginFailedInfo = this.getUserLoginFailedInfo(account);
        if (userLoginFailedInfo != null) {
            redisClient.remove(CacheKey.getUserLoginFailedInfoCacheKey(account)); // 清除登录失败数据
        }
    }


}
