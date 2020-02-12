package com.portal.service.impl;



import com.portal.commons.CacheKey;
import com.portal.daoAuthoriza.AuthUserDAO;
import com.portal.domain.UserInfoDomain;
import com.portal.redis.RedisClient;
import com.portal.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2018/8/27 9:24
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {

    @Autowired
    private AuthUserDAO authUserDAO;

    @Autowired
    private RedisClient<String, Object> redisClient;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserInfoDomain queryUserByName(String userName) {
        UserInfoDomain userInfoDomain=new UserInfoDomain();
        boolean result = redisTemplate.hasKey(CacheKey.getUserByNameCacheKey(userName));
        if (result == false) {
            userInfoDomain = authUserDAO.queryUserAuthInfo(userName);
        }
        return userInfoDomain;
    }
}
