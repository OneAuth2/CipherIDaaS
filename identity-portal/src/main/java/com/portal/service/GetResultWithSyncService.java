package com.portal.service;

import com.cipher.china.channel.AuthResult;

/**
 * @Author: zt
 * @Date: 2019-08-09 11:30
 */
public interface GetResultWithSyncService {

    /**
     * 从redis中阻塞的获取结果，若未取到，则一直尝试
     * 直到超时,默认超时时间30s
     *
     * @param key 要取的key
     * @param ttl 超时时间,毫秒值
     * @return
     */
    public Object getResultWithSync(String key, Long ttl);



    /**
     * 从redis中获取结果
     *
     * @param key 要取的key
     * @param ttl 超时时间,毫秒值
     * @return
     */


    public Object getResultWithDing(String key, Long ttl,String uuid);





}
