package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.WifiPortalWebInfo;
import cipher.console.oidc.domain.web.WifiPotalPageSettingInfo;

import java.util.Map;

/**
 * Created by 95744 on 2018/9/17.
 */
public interface WifiPortalWebInfoService {
    /**
     * create by 田扛
     * create time 2019年3月11日23:02:22
     * 根据id获取编辑wifi的界面配置
     * @param ip
     * @return
     */
     Map<String,Object> getEditProtal(String ip);

    int insertSelective(WifiPortalWebInfo record);

    WifiPortalWebInfo selectByPrimaryKey(Integer id);

    int CountByPortalNum(Integer id,String portalNum);

    int updateByPrimaryKeySelective(WifiPotalPageSettingInfo record);

    public Map<String,Object> getWifiPortWebInfoPageList(WifiPotalPageSettingInfo form, DataGridModel pageModel);

    int deleteInfo(Integer id);

    WifiPotalPageSettingInfo getWifiPotalPageSettingInfo(String id);

     void    insertWifiPotalPageSettingInfo(WifiPotalPageSettingInfo wifiPotalPageSettingInfo);







}
