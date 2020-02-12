package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.AdMap2LocalDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/22 17:22
 */
public interface AdMap2LocalService {

    /**
     * 查询Ad域映射到本地的配置
     * @return
     */
    public List<AdMap2LocalDomain> queryAdMapLocalConfig();

    /**
     * 插入Ad域字段映射到本地的配置
     * @param adMap2LocalDomainList
     * @throws Exception
     */
    public void insertAdMap2LocalConfig(@Param(value = "adMap2LocalDomainList") List<AdMap2LocalDomain> adMap2LocalDomainList) throws Exception;

    /**
     * 根据localVal更新一条配置
     * @param adMap2LocalDomain
     * @throws Exception
     */
    public void updateAdMapConfig(AdMap2LocalDomain adMap2LocalDomain) throws Exception;



}
