package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.RadiusConfigDomain;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/7/26 10:41
 */
public interface RadiusConfigMapper {

    /**
     * 根据用户名查询某个用户的totpKey
     * @param userName
     * @return
     */
    public String querySecretByUserName(String userName);

    /**
     * 根据用户名查询某个用户的密码
     * @param userName
     * @return
     */
    public String queyPwdByUserName(String userName);

    /**
     * 查询radius服务的配置
     * @return
     */
    public RadiusConfigDomain queryRadiusConfig(String companyId);

    /**
     * 更改radius配置信息
     * @param radiusConfigDomain
     * @throws Exception
     */
    public void updateRadiusConfig(RadiusConfigDomain radiusConfigDomain) throws Exception;

    public RadiusConfigDomain queryRadiusConfigById(Integer id);

    public void insertRadiusConfig(WifiPortalSetInfoDomain wifiPortalSetInfoDomain) throws Exception;

    public void updateRadiusInfo(WifiPortalSetInfoDomain wifiPortalSetInfoDomain) throws Exception;


    public  void  deleteRadiusConfig(int id);

}
