package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AutoSyncDingDomain;
import cipher.console.oidc.domain.web.DingConfigDomain;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zt
 * @Date: 2019-04-29 17:26
 */
public interface DingConfigMapper {

    /**
     * 插入基本配置
     *
     * @param dingConfigDomain
     */
    public void insertBaseConfig(DingConfigDomain dingConfigDomain);

    /**
     * 更新基本配置
     *
     * @param dingConfigDomain
     */
    public void updateBaseConfig(DingConfigDomain dingConfigDomain);

    /**
     * 根据公司id查询对应的基本配置信息
     *
     * @param companyUUid
     * @return
     */
    public DingConfigDomain queryConfigByCompanyUUid(String companyUUid);

    String queryAutoByCompanyUUid(String companyUUid);

    void updateAutoSync(@Param(value = "companyUUid") String companyUUid,@Param(value = "autoSyncInfo") String autoSyncInfo);

    AutoSyncDingDomain selectAutoSyncDing(String companyUUid);

}
