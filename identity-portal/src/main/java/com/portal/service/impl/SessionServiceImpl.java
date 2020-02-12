package com.portal.service.impl;



import com.portal.commons.CacheKey;
import com.portal.domain.NullCacheObject;
import com.portal.domain.SystemConfigInfo;
import com.portal.domain.UserSessionDomain;
import com.portal.redis.RedisClient;
import com.portal.service.ISessionService;
import com.portal.service.SystemConfigInfoService;
import com.portal.service.UserBehaviorService;
import com.portal.utils.JasonUtil;
import edu.hziee.common.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * session服务接口实现
 *
 * @author Jason
 *
 * */
@Service
public class SessionServiceImpl implements ISessionService {

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<String, Object>();

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final Logger logger = LoggerFactory.getLogger(SessionServiceImpl.class);

    @Autowired
    private UserBehaviorService userBehaviorService;

    @Autowired
    private SystemConfigInfoService systemConfigInfoService;


   // private int                         ttl        = 18000;  // 单位秒

    @Override
    public boolean isSessionExpire(String userName) {
        String userSessionJason = this.getUserSessionDomain(userName);
        if (StringUtil.isBlank(userSessionJason)) {
            return  false;
        }

        return true;
    }

    @Override
    public void updateSession(String requestIp, String uuid, String companyUUid) {
       /* try{
            UserBehaviorInfo userBehaviorInfo=
                    new UserBehaviorInfo(
                            userName,
                            null,
                            ConstantsCMP.UserBehaviorConstant.APPLICATION_VISIT,
                            requestIp,
                            "用户登录成功",
                            "用户登录成功",
                            new Date(),companyUUid
                    );
            userBehaviorService.insertUserBehaviorInfo(userBehaviorInfo);
        }catch (Exception e){
            e.printStackTrace();
        }*/
        this.setUserSessionDomain(uuid);
    }

    @Override
    //@Async("asyncServiceExecutor")
    public void updateSessionExpireTime(String requestIp, String companyUUid,String uuid) {
        updateSession(requestIp,uuid,companyUUid);
        //来自cas-client,且不需要二次认证
//        if (!StringUtils.isEmpty(serviceUrl) && !needSecondAuth) {
//            //将认证成功的重定向地址放入redis中 key值为
//            redisClient.put(CacheKey.getCacheKeyCipherCasServiceUrl(uuid), uuid, CAS_REDIS_TTL);
//        }
      //  redisClient.remove(CacheKey.getCacheKeyUserAuth(uuid));
    }

    @Override
    public void removeSession(String userName) {
        String key = CacheKey.getUserSessionCacheKey(userName);
        redisTemplate.delete(key);
        logger.info("清除redis中的key:" + key);
        Object obj = redisClient.get(key);
        logger.info("清除后再获取:" + obj);
        boolean obj1 = redisClient.containsKey(key);
        logger.info("清除后再判断:" + obj1);
        redisClient.remove(CacheKey.getCacheUserConsoleCookieInfo(userName));
        logger.info(CacheKey.getUserSessionCacheKey(userName));
        redisClient.remove(CacheKey.getUserSessionCacheKey(userName));
        redisClient.remove(CacheKey.getCacheKeyCipherPortalCookieInfo(userName));

    }

    @Override
    public String getSession(String userName) {
        String userSessionJason = this.getUserSessionDomain(userName);
        return userSessionJason;
    }


    public int getExpireTime(){
        SystemConfigInfo systemConfigInfo=systemConfigInfoService.getSystemConfigInfo();
        return systemConfigInfo.getExpireTime();
    }

    /**
     * 获取session信息
     *
     * */
    private String getUserSessionDomain(String userName) {
        Object obj = redisClient.get(CacheKey.getUserSessionCacheKey(userName));
        if (obj == null) {
            redisClient.put(CacheKey.getUserSessionCacheKey(userName), obj,  getExpireTime());
        }
        if (obj == null || obj instanceof NullCacheObject) {
           return null;
        }

        return (String) obj;
    }

    /**
     * 设置session信息
     *
     * */
    private void setUserSessionDomain(String userName) {
        String userSessionJason = this.getUserSessionDomain(userName);
        if (StringUtil.isBlank(userSessionJason)) {
            UserSessionDomain userSessionDomain = new UserSessionDomain();
            userSessionDomain.setUserName(userName);
            userSessionDomain.setExpireTime(new Date());
            userSessionJason = JasonUtil.object2JSONString(userSessionDomain);
        }
        redisClient.put(CacheKey.getUserSessionCacheKey(userName), userSessionJason,  getExpireTime());
    }


}
