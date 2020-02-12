package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AdAutoSyncInfo;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AutoSyncAdDomain;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author: zt
 * AD域目前只维护一条记录，且id为1
 * @Date: 2018/6/7 17:31
 */
public interface AdInfoMapper {


    /**
     * 根据id删除单个ad
     * @param adInfoDomain
     * @return
     */
     int deleteAd(AdInfoDomain adInfoDomain);
    /**
     * AD域配置列表
     * @param adInfoDomain
     * @return
     */
    public List<AdInfoDomain> queryList(AdInfoDomain adInfoDomain);


    /**
     * 查询记录总条数
     * @param form
     * @return
     */
    public int count(AdInfoDomain form);



    /**
     * 插入一条AD域的配置
     *
     * @param form
     */
    public Integer insertAdInfo(AdInfoDomain form);


    public int queryByIpAndCompanyId(AdInfoDomain form);


    public AdInfoDomain queryAdByIpAndCompanyId(AdInfoDomain form);

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
     * @param id
     * @param adAutoSyncInfo
     */
    void updateAdAutSync(@Param(value = "id") Integer id, @Param(value = "adAutoSyncInfo") String adAutoSyncInfo)throws Exception;

    /**
     * 根据id查询AD域的一条配置
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
     * 根据ip获取各个AD域最后一次同步时间
     * @param ip
     * @return
     */
    public Date lastSyncTimeByIp(String ip);

    /**
     * 查询AD域的配置是否存在
     *
     * @param form
     * @return
     */
    public AdInfoDomain queryAdInfoNew(AdInfoDomain form);

    /**
     * 通过id查找自动配置信息
     * @param id
     * @return
     */
    AutoSyncAdDomain selectAutoSync(Integer id);



}
