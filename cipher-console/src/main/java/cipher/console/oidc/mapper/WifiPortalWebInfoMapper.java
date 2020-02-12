package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.WifiPortalWebInfo;
import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WifiPortalWebInfoMapper {
     WifiPotalPageSettingInfo getEditProtal(String id);
    int deleteByPrimaryKey(Integer id);

    int insert(WifiPortalWebInfo record);

    int insertSelective(WifiPortalWebInfo record);

    WifiPortalWebInfo selectByPrimaryKey(Integer id);

    int selectCountByPortalNum(@Param(value = "id") Integer id, @Param(value = "portalNum") String portalNum);

    int updateByPrimaryKeySelective(WifiPotalPageSettingInfo record);

    int updateByPrimaryKey(WifiPortalWebInfo record);

    List<WifiPotalPageSettingInfo> selectWifiPortalWebInfoList(WifiPotalPageSettingInfo record);

    int selectWifiPortalWebInfoCount(WifiPotalPageSettingInfo record);

    void    insertWifiPotalPageSettingInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo);



}
