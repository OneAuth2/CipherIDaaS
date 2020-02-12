package com.portal.service;


import com.portal.domain.ServiceInfo;
import com.portal.domain.ServiceSwitchInfo;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:10
 */
public interface GlobalServiceInfoService {

    public ServiceInfo queryServiceInfoByIndexId(String indexId);

    /**
     * 判断服务总开关，和公司级别开关是否开启
     *
     * @param serviceIndex
     * @param platUserId
     * @return
     */
    public boolean serviceIsOpen(String serviceIndex, Integer platUserId);


    public ServiceSwitchInfo queryServiceSwitchInfo(String serviceName);








}
