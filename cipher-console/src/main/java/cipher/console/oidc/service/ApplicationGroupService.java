package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.GroupAuthorizationMapDomain;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.model.UserInfoModel;

import java.util.List;

public interface ApplicationGroupService {
    List<ApplicationInfoDomain> getApplicationsByGroupId(int groupId);

    List<ApplicationInfoDomain> getApplicationByAccountNumberAndGroupId(UserInfoModel userInfo);

    List<GroupApplicationModel> selectAllGroupApplication(int groupId,String accountNumber,List<ApplicationInfoDomain> showApplications);

    List<GroupApplicationModel> selectUserRoleApplication(String accountNumber);


    List<GroupApplicationModel> selectAllGroupApplicationByAdd(int groupId);

    List<ApplicationInfo> selectAppList(GroupAuthorizationMapDomain form);

    void  deleteGroupAuthorizationMap(GroupAuthorizationMapDomain form);

    void insertGroupAuthorizationMap(GroupAuthorizationMapDomain form);


    public List<GroupApplicationModel>  selectNewApplicationList(String accountNumber,String groupId,String companyId);


    public List<GroupApplicationModel>selectHaveGroupList(int groupId);

    List<GroupApplicationModel> selectNoneGroupList(List<Integer> list);

    GroupAuthorizationMapDomain selectGroupAuthorizationMap(GroupAuthorizationMapDomain form);





}
