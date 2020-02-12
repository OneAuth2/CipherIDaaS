package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;
import cipher.console.oidc.mapper.GoldMantisMapper;
import cipher.console.oidc.service.GoldMantisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: zt
 * @Date: 2018/9/17 17:36
 */
@Service
public class GoldMantisServiceImpl implements GoldMantisService {

    @Autowired
    private GoldMantisMapper goldMantisMapper;

    @Override
    public void insertGoldMantisUser(GoldMantisUser goldMantisUser) throws Exception {
        goldMantisMapper.insertGoldMantisUser(goldMantisUser);
    }

    @Override
    public void updateGoldMantisUser(GoldMantisUser goldMantisUser) throws Exception {
        goldMantisMapper.updateGoldMantisUser(goldMantisUser);
    }

    @Override
    public GoldMantisUser queryGoldMantisUserByMobile(GoldMantisUser goldMantisUser) throws Exception {
        return goldMantisMapper.queryGoldMantisUserByMobile(goldMantisUser);
    }

    @Override
    public void insertGoldMantisLog(GoldMantisUser goldMantisUser) throws Exception {
        goldMantisMapper.insertGoldMantisLog(goldMantisUser);
    }
}
