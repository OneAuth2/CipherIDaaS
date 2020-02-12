package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.web.SubAccountDomain;
import cipher.console.oidc.domain.web.SubAccountMapDomain;
import cipher.console.oidc.mapper.SubAccountMapMapper;
import cipher.console.oidc.service.SubAccountMapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2018/6/6 14:58
 */
@Service
public class SubAccountMapServiceImpl implements SubAccountMapService {

    private static final Logger logger = LoggerFactory.getLogger(SubAccountMapServiceImpl.class);

    @Autowired
    private SubAccountMapMapper subAccountMapMapper;

    @Override
    public void insertSubAccountMap(SubAccountMapDomain domain) {
            subAccountMapMapper.insertSubAccountMap(domain);
    }

    @Override
    public int querySubAccountMap(SubAccountExcel domain) {
        return subAccountMapMapper.querySubAccountMap(domain);
    }

    @Override
    public Integer selectSubId(SubAccountDomain subAccount) {
        return subAccountMapMapper.selectSubId(subAccount);
    }

    @Override
    public int deleteInfo(String accountNumber) {
        return subAccountMapMapper.deleteInfo(accountNumber);
    }

    @Override
    public Integer insertInfo(SubAccountMapDomain subAccountMapDomain) {
        int flag=0;
        try {
            flag=  subAccountMapMapper.insertInfo(subAccountMapDomain);
        }catch (Exception e){
            logger.error("enter SubAccountMapServiceImpl.insertInfo() but failed, subAccountMapDomain = [{}]",subAccountMapDomain.toString());
            logger.error(e.getMessage(),e);
        }
        return flag;
    }

    @Override
    public SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain) {
        return subAccountMapMapper.querySubMap(subAccountMapDomain);
    }

    @Override
    public int deleteSubAccountMap(SubAccountMapDomain subAccountMapDomain) {
        return subAccountMapMapper.deleteSubAccountMap(subAccountMapDomain);
    }


}
