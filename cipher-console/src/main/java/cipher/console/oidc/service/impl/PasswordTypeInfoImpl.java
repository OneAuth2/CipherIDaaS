package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.PasswordTypeInfoDomain;
import cipher.console.oidc.mapper.PasswordTypeInfoMapper;
import cipher.console.oidc.service.PasswordTypeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordTypeInfoImpl implements PasswordTypeInfoService{

    @Autowired
    PasswordTypeInfoMapper passwordTypeInfoMapper;

    @Override
    public List<PasswordTypeInfoDomain> queryPasswordTypeInfo() {
        return passwordTypeInfoMapper.queryPasswordTypeInfo();
    }


    @Override
    public int updatePasswordTypeInfo(int status, int passwordCode) {
        return passwordTypeInfoMapper.updatePasswordTypeInfo(status,passwordCode);
    }
}
