package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: TK
 * @Date: 2019/3/6 20:40
 */
public interface WifiConfigPageMapper {
      int getCount(String companyId);//获取WiFi配置页面的总数量
     int getProtalType(@Param(value = "companyId") String companyId, @Param(value = "protalType") Integer protalType);//获取数据库中是否已有的protalType
     int  insertNewConfigPage(WifiPotalPageSettingInfo wifiPotalPageSettingInfo);
     WifiPotalPageSettingInfo getWifiPortalInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo);


}
