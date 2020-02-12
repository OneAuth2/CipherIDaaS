package cipher.console.oidc.service.impl.ldap;

import cipher.console.oidc.domain.web.AdUpdateTimeDomain;
import cipher.console.oidc.mapper.AdUpdateTimeMapper;
import cipher.console.oidc.service.ldap.AdUpdateTimeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2019-04-15 15:48
 */
@Service
public class AdUpdateTimeServiceImpl implements AdUpdateTimeService {

    @Autowired
    private AdUpdateTimeMapper adUpdateTimeMapper;

    @Override
    public void insert(AdUpdateTimeDomain adUpdateTimeDomain) {
        adUpdateTimeMapper.insert(adUpdateTimeDomain);
    }

    @Override
    public String queryByIp(String ip) {
        return adUpdateTimeMapper.queryByIp(ip);
    }

    @Override
    public void update(AdUpdateTimeDomain adUpdateTimeDomain) {
        adUpdateTimeMapper.update(adUpdateTimeDomain);
    }

    @Override
    public void updateOrInsert(AdUpdateTimeDomain adUpdateTimeDomain) {
        String timeStamp = adUpdateTimeMapper.queryByIp(adUpdateTimeDomain.getIp());
        if (StringUtils.isEmpty(timeStamp)) {
            adUpdateTimeMapper.insert(adUpdateTimeDomain);
        } else {
            adUpdateTimeMapper.update(adUpdateTimeDomain);
        }
    }


}
