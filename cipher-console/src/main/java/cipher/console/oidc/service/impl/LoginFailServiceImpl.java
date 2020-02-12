package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.domain.web.LoginFailInfo;
import cipher.console.oidc.mapper.LoginFailMapper;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.LoginFailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * Created by 95744 on 2018/9/1.
 */
@Service
public class LoginFailServiceImpl implements LoginFailService{


    @Autowired
    private LoginFailMapper loginFailMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<String, Object>();

    @Override
    public LoginFailInfo getLoginFailInfo(LoginFailInfo loginFailInfo) {
        return loginFailMapper.getLoginFailInfo(loginFailInfo);
    }

    @Override
    public int updateLoginFailInfo(LoginFailInfo loginFailInfo) {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = valueOperations.get(CacheKey.getLoginFailedConfigCacheKey());
        if(StringUtils.isNotEmpty(key)){
            boolean result = redisTemplate.hasKey(CacheKey.getLoginFailedConfigCacheKey());
            if (result == true) {
                redisTemplate.delete(CacheKey.getLoginFailedConfigCacheKey());
            }
        }
     /*   LoginFailedConfigEntity loginFailedConfigEntity=new LoginFailedConfigEntity();
        loginFailedConfigEntity.setFailCount(loginFailInfo.getFailCount());
        loginFailedConfigEntity.setFreezingTime(loginFailInfo.getFreezingTime());
        loginFailedConfigEntity.setTimeRange(loginFailInfo.getTimeRang());
        loginFailedConfigEntity.setStatus(loginFailInfo.getStatus());
        redisClient.put(CacheKey.getLoginFailedConfigCacheKey(), loginFailedConfigEntity);*/
        return loginFailMapper.updateLoginFailInfo(loginFailInfo);
    }

    @Override
    public int insertLoginFailInfo(LoginFailInfo loginFailInfo) {
        return loginFailMapper.insertLoginFailInfo(loginFailInfo);
    }
}
