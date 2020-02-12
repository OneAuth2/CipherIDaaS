package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AdMap2LocalDomain;
import cipher.console.oidc.mapper.AdMap2LocalMapper;
import cipher.console.oidc.service.AdMap2LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/22 17:23
 */
@Service
public class AdMap2LocalServiceImpl implements AdMap2LocalService {

    @Autowired
    private AdMap2LocalMapper adMap2LocalMapper;

    @Override
    public List<AdMap2LocalDomain> queryAdMapLocalConfig() {
        return adMap2LocalMapper.queryAdMapLocalConfig();
    }

    @Override
    public void insertAdMap2LocalConfig(List<AdMap2LocalDomain> adMap2LocalDomainList) throws Exception {
        adMap2LocalMapper.insertAdMap2LocalConfig(adMap2LocalDomainList);
    }

    @Override
    public void updateAdMapConfig(AdMap2LocalDomain adMap2LocalDomain) throws Exception {
        adMap2LocalMapper.updateAdMapConfig(adMap2LocalDomain);
    }
}
