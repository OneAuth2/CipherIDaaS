package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AdUserInfoDomain;
import cipher.console.oidc.mapper.AdUserInfoMapper;
import cipher.console.oidc.service.AdUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/6/8 17:55
 */
@Service
public class AdUserInfoServiceImpl implements AdUserInfoService {

    @Autowired
    private AdUserInfoMapper adUserInfoMapper;

    @Override
    public void insertAdUserInfoList(List<AdUserInfoDomain> list) {
            adUserInfoMapper.insertAdUserInfoList(list);
    }

    @Override
    public List<AdUserInfoDomain> queryRepeatName(List<AdUserInfoDomain> list) {
        return adUserInfoMapper.queryRepeatName(list);
    }
}
