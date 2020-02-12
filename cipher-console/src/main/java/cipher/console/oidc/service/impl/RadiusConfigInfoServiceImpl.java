package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.RadiusConfigDomain;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;
import cipher.console.oidc.mapper.RadiusConfigMapper;
import cipher.console.oidc.service.RadiusConfigInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @Author: zt
 * @Date: 2018/7/26 10:45
 */
@Service("radiusConfigInfoService")
public class RadiusConfigInfoServiceImpl implements RadiusConfigInfoService {

    @Autowired
    private RadiusConfigMapper radiusConfigMapper;

    @Override
    public String querySecretByUserName(String userName) {
        return radiusConfigMapper.querySecretByUserName(userName);
    }

    @Override
    public String queyPwdByUserName(String userName) {
        return radiusConfigMapper.queyPwdByUserName(userName);
    }

    @Override
    public RadiusConfigDomain queryRadiusConfig(String companyId) {
        return radiusConfigMapper.queryRadiusConfig(companyId);
    }

    @Override
    public void updateRadiusConfig(RadiusConfigDomain radiusConfigDomain) throws Exception {
        radiusConfigMapper.updateRadiusConfig(radiusConfigDomain);
    }

    @Override
    public RadiusConfigDomain queryRadiusConfigById(int id) {
        return radiusConfigMapper.queryRadiusConfigById(id);
    }

    @Override
    public void insertRadiusConfig(WifiPortalSetInfoDomain wifiPortalSetInfoDomain) {
        try{
            radiusConfigMapper.insertRadiusConfig(wifiPortalSetInfoDomain);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void deleteRadiusConfig(int id) {
        radiusConfigMapper.deleteRadiusConfig(id);
    }

    @Override
    public void updateRadiusInfo(WifiPortalSetInfoDomain form) {
        try {
            radiusConfigMapper.updateRadiusInfo(form);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
