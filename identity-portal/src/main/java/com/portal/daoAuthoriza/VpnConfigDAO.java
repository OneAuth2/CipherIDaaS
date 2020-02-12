package com.portal.daoAuthoriza;

import com.portal.domain.VpnConfigDomain;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zt
 * @Date: 2019-08-08 16:17
 */
public interface VpnConfigDAO {

    public VpnConfigDomain getConfigByIp(@Param("ip") String ip);

}
