package com.portal.service;

import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019/1/5 18:45
 */
public interface CheckAccountSafeService {

    /**
     * 调用userProvision判断账号的安全性
     * @param whichService 服务索引
     * @param platUserId 平台账号主键
     * @return
     */
    public Map<String, Object> checkAccountLinsence(String whichService,Integer platUserId);



}
