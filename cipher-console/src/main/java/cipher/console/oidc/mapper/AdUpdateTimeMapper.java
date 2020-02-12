package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdUpdateTimeDomain;

/**
 * @Author: zt
 * @Date: 2019-04-15 15:49
 */
public interface AdUpdateTimeMapper {



    public void insert(AdUpdateTimeDomain adUpdateTimeDomain);

    public String queryByIp(String ip);

    public void update(AdUpdateTimeDomain adUpdateTimeDomain);


}
