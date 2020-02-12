package com.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author lqgzj
 * @date 2019/8/9
 */
@Mapper
public interface CipherVpnConfigurationMapper {

    String getDeviceNameByIp(String ip);
}
