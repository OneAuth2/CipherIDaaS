package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;

/**
 * @Author: TK
 * desc:wife可配置界面相关service
 * @Date: 2019/3/6 20:37
 */
public interface WifiConfigPageService {
    int getCount(String companyId);
    int getProtalType(String companyId,Integer portalType);
    int insertNewConfigPage( WifiPotalPageSettingInfo wifiPotalPageSettingInfo);//插入新的WiFiconfig页面

    WifiPotalPageSettingInfo getWifiPortalInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo);
}
