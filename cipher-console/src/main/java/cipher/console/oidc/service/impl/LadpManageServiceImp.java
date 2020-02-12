package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.LadpInfoDomain;
import cipher.console.oidc.mapper.LadpInfoMapper;
import cipher.console.oidc.service.LadpManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 95744 on 2018/9/14.
 */

@Service
public class LadpManageServiceImp implements LadpManageService{

    @Autowired
    private LadpInfoMapper ladpInfoMapper;


    @Override
    public LadpInfoDomain getLadpManageInfo(LadpInfoDomain ladpInfoDomain) {
        return ladpInfoMapper.getLadpManageInfo(ladpInfoDomain);
    }

    @Override
    public int updateLadpManageInfo(LadpInfoDomain ladpInfoDomain) {
        return ladpInfoMapper.updateLadpManageInfo(ladpInfoDomain);
    }

    @Override
    public int insertLadpManageInfo(LadpInfoDomain ladpInfoDomain) {
        return ladpInfoMapper.insertLadpManageInfo(ladpInfoDomain);
    }
}
