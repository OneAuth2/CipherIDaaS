package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AcSetInfo;
import cipher.console.oidc.domain.web.WifiPortalSetInfoDomain;

import java.util.List;

public interface AcSetInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AcSetInfo record);

    int insertSelective(WifiPortalSetInfoDomain record);

    AcSetInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AcSetInfo record);

    int updateByPrimaryKey(AcSetInfo record);


    List<AcSetInfo> selectAcSetInfoList(AcSetInfo record);

    int selectAcSetInfoCount(AcSetInfo record);


    int insertWifiPortalSetInfo(AcSetInfo record);

    int updateAcSetInfo(WifiPortalSetInfoDomain record);

}