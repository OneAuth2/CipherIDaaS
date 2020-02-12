package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.DingConfigDomain;

import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019-04-29 17:10
 */
public interface DingConfigService {

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
     * 插入或者更新基本配置
     *
     * @param dingConfigDomain
     */
    public Map<String, Object> insertOrUpdateBaseConfig(DingConfigDomain dingConfigDomain);

    /**
     * 根据公司id查询，查询钉钉配置
     *
     * @param companyUUid 公司id
     */
    public DingConfigDomain queryConfig(String companyUUid);

    /**
     * 根据公司id添加钉钉自动同步配置
     * @param companyUUid
     */
    void updateAutoSync(String companyUUid,String autoSyncInfo);

    /**
     * 根据公司id查出钉钉自动同步配置
     *
     */
    String queryAutoSync(String companyUUid);

    /**
     * 根据公司id获取accesstoken
     *
     * @param companyUUid
     * @return
     */
    public String getAccessTokenByCompanyUUid(String companyUUid);

}
