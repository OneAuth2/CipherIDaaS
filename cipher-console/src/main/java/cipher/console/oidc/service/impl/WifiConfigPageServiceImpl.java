package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;
import cipher.console.oidc.mapper.WifiConfigPageMapper;
import cipher.console.oidc.service.WifiConfigPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: TK
 * desc:wifi可配置界面的service实现类
 * @Date: 2019/3/6 20:38
 */
@Service
public class WifiConfigPageServiceImpl implements WifiConfigPageService {
    @Autowired
    private WifiConfigPageMapper wifiConfigPageMapper;

    /**
     * 获取总的配置界面
     * @return
     */
    @Override
    public int getCount(String companyId) {
        return wifiConfigPageMapper.getCount(companyId);
    }

    /**
     * 获取指定protype是否存在
     * @param portalType
     * @return
     */
    @Override
    public int getProtalType(String companyId,Integer portalType) {

        return wifiConfigPageMapper.getProtalType(companyId,portalType);
    }

    /**
     * 插入新的portalConfig页面
     * @param wifiPotalPageSettingInfo
     * @return
     */
    @Override
    public int insertNewConfigPage(WifiPotalPageSettingInfo wifiPotalPageSettingInfo) {
        return wifiConfigPageMapper.insertNewConfigPage(wifiPotalPageSettingInfo);
    }

    @Override
    public WifiPotalPageSettingInfo getWifiPortalInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo) {
        return wifiConfigPageMapper.getWifiPortalInfo(wifiPotalPageSettingInfo);
    }
}
