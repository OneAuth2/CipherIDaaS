package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.DingConfigDomain;
import cipher.console.oidc.domain.web.WxConfigDomain;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author lqgzj
 * @date 2019-10-11
 */
public interface WxConfigService {
    String getAccessTokenByCompanyUUid(String companyUUid);
    /*
    根据公司id查询企业微信配置信息
     */
    public WxConfigDomain queryConfig(String companyUUid);

    Map<String, Object> insertOrUpdateBaseConfig(WxConfigDomain form);

    void updateAutoSync(String companyUUid,String autoSyncInfo);

    String queryAutoSync(String companyId);
}
