package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: yw
 *  身份认证策略管理
 */

public interface IdentityStrategyMapper {

    List<IdentityStrategyDomain> queryIdentityStrategyPageList(IdentityStrategyDomain identityStrategyDomain);

    int queryIdentityStrategyCount(IdentityStrategyDomain identityStrategyDomain);

    void saveIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    int deleteIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    void modifyIdentityStrategy(IdentityStrategyDomain identityStrategyDomain);

    IdentityStrategyDomain queryIdentityStrategyById(IdentityStrategyDomain identityStrategyDomain);


    IdentityStrategyDomain getIdentityStrategyInfo(@Param("groupId") Integer groupId);


    /**
     * 获取默认的认证策略
     * @return
     */
    public IdentityStrategyDomain queryDefaultAuthType(String companyId);

    /**
     * 根据用户所在组获取策略
     * @return
     */
    public IdentityStrategyDomain queryAuthTypeByGroupIdList(@Param("groupIdList") List<Integer> groupIdList);


    int queryCountByStrategyName(@Param(value = "id") Integer id, @Param(value = "strategyName")String strategyName);

    int queryCountByPriority(@Param(value = "id")Integer id, @Param(value = "priority")Integer priority);


}
