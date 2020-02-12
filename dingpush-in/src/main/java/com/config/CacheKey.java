package com.config;

/**
 * @Author: zt
 * @Date: 2019-08-09 14:02
 */
public class CacheKey {
    /**
     * 钉钉push认证结果的key
     */
    private static final String DING_PUSH_AUTH_RESULT_KEY = "DING_PUSH_AUTH_RESULT_KEY_";


    public static String getDingPushAuthResultKey(String key,Long timeStamp) {

        return DING_PUSH_AUTH_RESULT_KEY + key+timeStamp;
    }


}
