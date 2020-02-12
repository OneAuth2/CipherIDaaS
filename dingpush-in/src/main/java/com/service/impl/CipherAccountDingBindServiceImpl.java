package com.service.impl;/**
 * @author lqgzj
 * @date 2019/8/9
 */

import com.mapper.CipherAccountDingBindMapper;
import com.service.CipherAccountDingBindService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author qiaoxi
 * @Date 2019/8/910:24
 **/

@Service("CipherAccountDingBindService")
public class CipherAccountDingBindServiceImpl implements CipherAccountDingBindService {

    @Resource
    private CipherAccountDingBindMapper cipherAccountDingBindMapper;

    @Override
    public String getUuidByUserId(String dingUserId) {
        return cipherAccountDingBindMapper.getUuidByUserId(dingUserId);
    }

    @Override
    public String getCompanyIdByUserId(String uuid) {
        return cipherAccountDingBindMapper.getCompanyIdByUserId(uuid);
    }

    @Override
    public String getDingIdByUserId(String uuid) {
        return cipherAccountDingBindMapper.getDingIdByUserId(uuid);
    }
}
