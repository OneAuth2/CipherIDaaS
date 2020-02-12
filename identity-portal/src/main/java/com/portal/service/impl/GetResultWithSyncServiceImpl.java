package com.portal.service.impl;

import com.cipher.china.channel.AuthResult;
import com.cipher.china.channel.channels.DingPushAuthChannel;
import com.portal.commons.CacheKey;
import com.portal.commons.ReturnCode;
import com.portal.redis.RedisClient;
import com.portal.service.GetResultWithSyncService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cipher.china.channel.enums.AuthChannelEnum.*;
import static com.cipher.china.channel.enums.AuthResultCode.*;


/**
 * @Author: zt
 * @Date: 2019-08-09 11:33
 */
@Service
public class GetResultWithSyncServiceImpl implements GetResultWithSyncService {


    private static final Logger logger = LoggerFactory.getLogger(GetResultWithSyncServiceImpl.class);
    @Autowired
    private RedisClient<String, Object> redisClient;

    @Override
    public Object getResultWithSync(String key, Long ttl) {
        String cacheKey = key;

        if (ttl == null || ttl <= 0) {
            ttl = 180000L;
        }

        Long start = System.currentTimeMillis();

        Object result = null;

        while (true) {

            if (redisClient.containsKey(cacheKey)) {
                result = redisClient.get(cacheKey);
                logger.info("++++++++dingpush result++++++"+result);
                if (result == null) {
                    return null;
                }
                return result;

            } else {
                try {
                    //暂停500ms
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            Long end = System.currentTimeMillis();


            //大于30s,强行停掉
            if (end - start >= ttl) {

                logger.info("dingpush Time out start time end time"+end +"--start---"+  start);
                return null;
            }

        }
    }

    @Override
    public Object getResultWithDing(String key, Long ttl, String uuid) {
        String cacheKey = key;

        if (ttl == null || ttl <= 0) {
            ttl = 180000L;
        }

        Long start = System.currentTimeMillis();
        Object result = null;

        while (true) {

            if (redisClient.containsKey(cacheKey)) {
                result = redisClient.get(cacheKey);
                logger.info("++++++++dingpush result++++++"+result);
                if (result == null) {
                    return null;
                }
                return result;

            } else {
                try {
                    //暂停500ms
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Long end = System.currentTimeMillis();

            //大于30s,强行停掉
            if (end - start >= ttl) {
                logger.info("dingpush Time out start time end time"+end +"--start---"+  start);
                return null;
            }

        }
    }


}
