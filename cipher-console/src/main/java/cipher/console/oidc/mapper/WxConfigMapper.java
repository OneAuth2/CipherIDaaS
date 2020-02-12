package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AutoSyncWxDomain;
import cipher.console.oidc.domain.web.WxConfigDomain;
import org.apache.ibatis.annotations.Param;

/**
 * @author lqgzj
 * @date 2019-10-11
 */
public interface WxConfigMapper {
    WxConfigDomain queryConfigByCompanyUUid(String companyUUid);

    void insertBaseConfig(WxConfigDomain wxConfigDomain);

    void updateBaseConfig(WxConfigDomain wxConfigDomain);

    String queryAutoByCompanyUUid(String companyId);

    AutoSyncWxDomain selectAutoSyncWx(String companyUUid);

    void updateAutoSync(@Param(value = "companyUUid") String companyUUid, @Param(value = "autoSyncInfo") String autoSyncInfo);
}
