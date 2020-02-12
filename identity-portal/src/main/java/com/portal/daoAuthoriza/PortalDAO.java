package com.portal.daoAuthoriza;



import com.portal.auth2.model.SsoClientDetails;
import com.portal.domain.CsApplicationInfo;
import com.portal.domain.IconSettingsDomain;
import com.portal.domain.PortalApplyInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 95744 on 2018/5/21.
 */
public interface PortalDAO {
    public List<PortalApplyInfo> queryPortalApplyList(PortalApplyInfo portalApplyInfo, @Param("userName") String userName);

    public PortalApplyInfo selectPortalInfo(PortalApplyInfo portalApplyInfo);

    public  String getReqUrl(@Param("applyId") String applyId);

    public List<PortalApplyInfo> queryPortalList();

    public List<PortalApplyInfo>  queryPortalByGroup(PortalApplyInfo portalApplyInfo);

    public List<PortalApplyInfo>  queryOidcInfo();

    public  String getRediretLoginUrl(@Param("applyId") String applyId);

    public  String  getApplicationUrl(@Param("applyId")String applyId);

    public IconSettingsDomain getIconSettiingInfo(@Param("companyId") String companyId);

    public CsApplicationInfo getCsApplicationInfo(@Param("applyId") String applyId);

    public String  getSystemType(@Param("id")String id);


    public SsoClientDetails getAuthApplicationInfo(SsoClientDetails ssoClientDetails);



}
