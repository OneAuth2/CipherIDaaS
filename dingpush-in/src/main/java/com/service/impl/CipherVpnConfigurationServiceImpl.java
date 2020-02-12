package com.service.impl;/**
 * @author lqgzj
 * @date 2019/8/9
 */

import com.mapper.CipherVpnConfigurationMapper;
import com.service.CipherVpnConfigurationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author qiaoxi
 * @Date 2019/8/917:59
 **/

@Service("CipherVpnConfigurationService")
public class CipherVpnConfigurationServiceImpl implements CipherVpnConfigurationService {

    @Resource
    private CipherVpnConfigurationMapper cipherVpnConfigurationMapper;

    @Override
    public String getDeviceNameByIp(String ip) {
        return cipherVpnConfigurationMapper.getDeviceNameByIp(ip);
    }
}
