package com.portal.daoAuthoriza;

import com.portal.domain.ServiceInfo;
import com.portal.domain.ServiceSwitchInfo;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:14
 */
public interface GlobalServiceInfoDAO {

    public ServiceInfo queryServiceInfoByIndexId(String indexId);

    public ServiceSwitchInfo queryServiceSwitchInfo(String serviceName);





}
