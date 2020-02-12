package cipher.console.oidc.mapper;

        import cipher.console.oidc.domain.web.ApplicationInfo;
        import cipher.console.oidc.domain.web.ApplicationInfoDomain;
        import cipher.console.oidc.domain.web.GroupAuthorizationMapDomain;
        import cipher.console.oidc.model.GroupApplicationModel;
        import cipher.console.oidc.model.UserInfoModel;
        import org.apache.ibatis.annotations.Param;

        import java.util.List;

public interface ApplicationGroupMapper {
    public List<ApplicationInfoDomain> getApplicationsByGroupId(int groupId);

    List<ApplicationInfoDomain> selectApplication(@Param(value = "accountNumber") String accountNumber);

    List<ApplicationInfoDomain> getApplicationByAccountNumberAndGroupId(UserInfoModel userInfo);

    List<GroupApplicationModel> selectAllGroupApplication();

    List<GroupApplicationModel> selectUserRoleApplication(String accountNumber);


    void  deleteGroupAuthorizationMap(GroupAuthorizationMapDomain form);

    void insertGroupAuthorizationMap(GroupAuthorizationMapDomain form);

    List<ApplicationInfo> selectAppList(GroupAuthorizationMapDomain form);

    public List<GroupApplicationModel>  selectNewApplicationList(@Param(value = "uuid") String uuid);

    public List<GroupApplicationModel>selectHaveApplicationList(@Param(value = "groupId")int groupId);

    List<GroupApplicationModel> selectNoneApplicationList(List<Integer> list);

    public List<GroupApplicationModel>selectApplicationList(@Param(value = "applicationId")int applicationId);

    GroupAuthorizationMapDomain selectGroupAuthorizationMap(GroupAuthorizationMapDomain form);



}
