package cipher.console.oidc.service.ldap;

import cipher.console.oidc.domain.web.AdUpdateTimeDomain;

/**
 * AD增量更新时间戳维护
 *
 * @Author: zt
 * @Date: 2019-04-15 15:44
 */
public interface AdUpdateTimeService {


    public void insert(AdUpdateTimeDomain adUpdateTimeDomain);

    public String queryByIp(String ip);

    public void update(AdUpdateTimeDomain adUpdateTimeDomain);

    public void updateOrInsert(AdUpdateTimeDomain adUpdateTimeDomain);

}
