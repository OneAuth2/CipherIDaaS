package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.RadiusConfigDomain;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/7/26 10:44
 */
public interface RadiusConfigInfoService {

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
     * 更改radius而配置信息
     * @param radiusConfigDomain
     * @throws Exception
     */
    public void updateRadiusConfig(RadiusConfigDomain radiusConfigDomain) throws Exception;


    /**
     * 查询radius服务的配置
     * @return
     */
    public RadiusConfigDomain queryRadiusConfigById(int id);

    /**
     * 添加radius而配置信息
     * @param wifiPortalSetInfoDomain
     * @throws Exception
     */
    public void insertRadiusConfig(WifiPortalSetInfoDomain wifiPortalSetInfoDomain) ;


    /**
     * 删除radius而配置信息
     * @param id
     * @throws Exception
     */
    public void deleteRadiusConfig(int id) ;


    void updateRadiusInfo(WifiPortalSetInfoDomain form);





}
