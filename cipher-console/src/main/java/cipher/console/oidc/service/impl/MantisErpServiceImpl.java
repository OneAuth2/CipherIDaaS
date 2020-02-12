package cipher.console.oidc.service.impl;


import cipher.console.oidc.domain.web.ErpConfigInfo;
import cipher.console.oidc.mapper.MantisErpMapper;
import cipher.console.oidc.service.MantisErpService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MantisErpServiceImpl implements MantisErpService {

    @Autowired
    private MantisErpMapper erpMapper;

    @Override
    public ErpConfigInfo selectTheConfig(String companyId) {
        ErpConfigInfo erpConfigInfo = erpMapper.selectTheConfig(companyId);
        return erpConfigInfo;
    }

    @Override
    public void updateTheConfig(ErpConfigInfo erpConfigInfo) {
        erpMapper.updateTheConfig(erpConfigInfo);
    }


}
