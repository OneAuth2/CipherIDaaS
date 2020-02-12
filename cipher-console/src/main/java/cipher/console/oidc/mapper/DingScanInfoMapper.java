package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.DingConfigDomain;
import cipher.console.oidc.domain.web.DingScanInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-04-29 17:26
 */
public interface DingScanInfoMapper {


    /**
     * 批量插入钉钉扫码信息
     *
     * @param list
     */
    public void insertList(@Param("list") List<DingScanInfoDomain> list);

    /**
     * 批量更新钉钉扫码信息
     *
     * @param dingScanInfoDomain
     */
    public void updateScanAppInfo(DingScanInfoDomain dingScanInfoDomain);

    /**
     * 根据钉钉的基础配置id，查询对应的扫码信息
     *
     * @param configId
     * @return
     */
    public List<DingScanInfoDomain> getScanInfoListByConfigId(String configId);


}
