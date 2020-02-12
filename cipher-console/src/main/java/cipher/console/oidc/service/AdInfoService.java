package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdAutoSyncInfo;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.model.AdOuModel;
import com.unboundid.ldap.sdk.LDAPException;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/6/7 17:38
 */
public interface AdInfoService {

    /**
     * create by 田扛
     * create time 2019年3月21日11:17:41
     * 删除单个ad
     *
     * @param adInfoDomain
     * @return
     */
    Map<String, Object> deleteAd(AdInfoDomain adInfoDomain);

    void insertAdInfo(AdInfoDomain form) throws Exception;

    int queryAdCountByIp(AdInfoDomain adInfoDomain);

    /**
     * 查询AD域的配置是否存在
     *
     * @param form
     * @return
     */
    public AdInfoDomain queryAdInfo(AdInfoDomain form);


    /**
     * 更新AD配置信息
     *
     * @param form
     */
    public void updateAdInfo(AdInfoDomain form) throws Exception;


    /**
     * 更新ad自动同步配置
     * @param adAutoSyncInfo 自动同步配置信息
     */
    void updateAdAutSync(Integer id,String companyId,String adAutoSyncInfo) throws Exception;


    public Map<String, Object> list(AdInfoDomain form, DataGridModel dataGridModel) throws Exception;

    /**
     * 根据id查询AD域的一条配置
     *
     * @param id
     * @return
     */
    public AdInfoDomain queryAdInfoById(Integer id);

    /**
     * 根据id查出ad目录中的一条自动同步配置
     * @param id
     * @return
     */
    String queryAdSyncInfoById(Integer id);


    /**
     * 查询AD域的配置是否存在
     *
     * @param form
     * @return
     */
    public AdInfoDomain queryAdInfoNew(AdInfoDomain form);


    /**
     *
     * 获取某个AD的OU树状结构
     *
     * @param adInfoDomain
     * @return
     */
    public List<AdOuModel> queryAdOuTree(AdInfoDomain adInfoDomain,String queryName) throws GeneralSecurityException, LDAPException;


}
