package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AcSetInfo;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;

import java.util.Map;

/**
 * Created by 95744 on 2018/9/17.
 */
public interface AcSetInfoService  {
     Map<String,Object> deleteAc( AcSetInfo acSetInfo);

    int insertSelective(WifiPortalSetInfoDomain record);

    AcSetInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AcSetInfo record);

    public Map<String,Object> getAcSetPageList(AcSetInfo form, DataGridModel pageModel);

    int deleteAcInfo(Integer id);

    int insertWifiPortalSetInfo(AcSetInfo record);

    void updateAcSetInfo(WifiPortalSetInfoDomain form);

}
