package com.service.impl;/**
 * @author lqgzj
 * @date 2019/8/9
 */

import com.mapper.CipherDingConfigInfoMapper;
import com.service.CipherDingConfigInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author qiaoxi
 * @Date 2019/8/911:57
 **/
@Service("CipherDingConfigInfoService")
public class CipherDingConfigInfoServiceImpl implements CipherDingConfigInfoService {

    @Resource
    private CipherDingConfigInfoMapper cipherDingConfigInfoMapper;


    @Override
    public String getCorpIdByCompanyId(String companyId) {
        return cipherDingConfigInfoMapper.getCorpIdByCompanyId(companyId);
    }
}
