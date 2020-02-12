package com.portal.service;




import com.portal.auth2.model.SsoClientDetails;
import com.portal.domain.CsApplicationInfo;
import com.portal.domain.IconSettingsDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.model.GroupApplicationModel;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/5/21.
 */
public interface PortalService {

    public List<PortalApplyInfo> queryPortalApplyList(PortalApplyInfo portalApplyInfo, String userName);


    public Map<String,Object> updateUserPwd(String username, String newpwd,String pwd);


    public PortalApplyInfo selectPortalInfo(PortalApplyInfo portalApplyInfo);

    public List<PortalApplyInfo> queryPortalList();


    public List<PortalApplyInfo>  queryPortalByGroup(PortalApplyInfo portalApplyInfo);

    public List<PortalApplyInfo>  queryOidcInfo();

    public  String getReqUrl(String applyId);

    public  String  getApplicationUrl(String applyId);

    public  String getRediretLoginUrl(@Param("applyId") String applyId);

    public List<GroupApplicationModel> selectAllGroupApplication(int groupId, String accountNumber);

    public List<GroupApplicationModel> selectNewApplicationList(String accountNumber,String queryName);


    public IconSettingsDomain getIconSettiingInfo(@Param("companyId") String companyId);


    public CsApplicationInfo getCsApplicationInfo(@Param("applyId") String applyId);



    public SsoClientDetails getAuthApplicationInfo(SsoClientDetails ssoClientDetails);






}
