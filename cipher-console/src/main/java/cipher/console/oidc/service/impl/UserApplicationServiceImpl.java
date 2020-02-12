package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.UserApplicationMapInfo;
import cipher.console.oidc.mapper.UserApplicationMapInfoMapper;
import cipher.console.oidc.service.UserApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserApplicationServiceImpl implements UserApplicationService {

     @Autowired
    private UserApplicationMapInfoMapper userApplicationMapInfoMapper;

    @Override
    public int deleteUserAuth(Integer applicationId) {
        return userApplicationMapInfoMapper.deleteUserAuth(applicationId);
    }

    @Override
    public UserApplicationMapInfo selectUserApplicationInfo(UserApplicationMapInfo record) {
        return userApplicationMapInfoMapper.selectUserApplicationInfo(record);
    }

    @Override
    public int insertSelective(UserApplicationMapInfo record) {
        return userApplicationMapInfoMapper.insertSelective(record);
    }
}
