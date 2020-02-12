package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GroupMapper {


    public List<GroupInfoDomain> getAllGroup(String companyId);

    /**
     * add by zt
     * 查询部门最简单的信息，以组装成一个轻量级的树状结构
     *
     * @param companyId
     * @return
     */
    public List<GroupTreeDomain> queryAllGroupToTree(String companyId);

    /**
     * add by zt
     * 将钉钉的组织结构导入自己用户体系
     *
     * @param groupInfoDomain
     * @return
     */
    public Integer insert(GroupInfoDomain groupInfoDomain);

    /**
     * add by zt
     * 根据钉钉id查询对应的主键id
     *
     * @param dingId
     * @return
     */
    public Integer queryGroupIdBYDingId(Long dingId);

    /**
     * add by zt
     * 将钉钉的组织结构插入cipher_group_info
     *
     * @param dingGroupDomain
     */
    public void insertGroupByDingGroup(DingGroupDomain dingGroupDomain);

    public GroupInfoDomain getGroupById(int groupId);

    /**
     * 查询所有组信息用于下拉选择框
     *
     * @return
     */
    public List<GroupInfoDomain> queryGroupSelect(String companyId);

    public GroupInfoDomain queryGroupByAccountName(String accountNumber);

    public List<GroupBaseModel> queryAllGroupBaseInfo(GroupBaseModel groupBaseModel);

    public int countGroup();

    /**
     * 查询组详细信息
     *
     * @return
     */
    public List<GroupAuthorizationDetailModel> groupAuthorizedInfo(GroupAuthorizationDetailModel groupAuthorizationDetailModel);

    /**
     * 查询组详细信息条数
     *
     * @return
     */
    public int countGroupTable(GroupAuthorizationDetailModel groupAuthorizationDetailModel);

    /**
     * 查询组授权
     *
     * @return
     */
    public List<GroupAuthorizedModel> queryGroupAuth(int groupId);


    /**
     * 删除组的授权
     *
     * @return
     */
    public int deleteGroupAuth(GroupAuthorizedModel groupAuthorizedModel);


    /**
     * 增加组的授权
     *
     * @return
     */
    public int insetGroupAuth(GroupAuthorizedModel groupAuthorizedModel);

    /**
     * 增加组成员
     *
     * @return
     */
    List<GroupMemberAddModel> queryGroupMember(GroupMemberAddModel groupMemberAddModel);


    /**
     * 查询组成员数据总数
     *
     * @return
     */
    int countGroupMember(GroupMemberAddModel groupMemberAddModel);

    /**
     * 查询新的组
     *
     * @return
     */
    public int insertNewGroup(GroupInfoModel groupInfoModel);

    /**
     * 编辑部门
     * @param groupInfoModel
     */
    void updateGroup(GroupInfoModel groupInfoModel);

    List<GroupInfoDomain> selectGoupInfoByName(GroupInfoDomain groupInfoDomain);

    GroupInfoModel selectEditGroupEcho(Integer groupId);

    /**
     * 更新组信息
     *
     * @return
     */
    public int updateGroupInfo(GroupInfoModel groupInfoModel);

    /**
     * 插入新的组
     *
     * @return
     */
    public int insetIntoGroup(UserInfoModel userInfoModel);

    /**
     * 删除组关联
     *
     * @return
     */
    public int deleteGroupMap(UserInfoModel userInfoModel);

    public GroupInfoDomain queryGroupByGroupName(Map<String, Object> map);

    public Integer checkUserInfoByGroupId(Integer groupId);

    public Integer checkLowerLevelByGroupId(Integer groupId);

    public void deleteGroup(Integer groupId);

    public void deleteGroupUser(Integer groupId);


    /**
     * 根据组名查询组
     *
     * @param groupName
     * @return
     */
    public GroupInfoDomain queryGoupByName(@Param(value = "groupName") String groupName);

    /**
     * 根据公司id ，组名查询组
     *
     * @param companyId
     * @param groupName
     * @return
     */
    GroupInfoDomain queryGroupByNameAndcompanyId(@Param(value = "companyId") String companyId, @Param(value = "groupName") String groupName);


    /**
     * 插入一个新的组
     *
     * @param groupInfoDomain
     */
    public Integer insertGroup(GroupInfoDomain groupInfoDomain);


    /**
     * 增加组成员
     *
     * @return
     */
    List<GroupMemberAddModel> queryGroupMemberByGroupId(GroupMemberAddModel groupMemberAddModel);


    /**
     * 查询组成员数据总数
     *
     * @return
     */
    int countGroupMemberByGroupId(GroupMemberAddModel groupMemberAddModel);

    List<GroupInfoDomain> selectNoneGroupList(List<Integer> list);


    List<GroupInfoDomain> selectGroupNameList(String uuid);


    List<GroupInfoDomain> selectGroupList(Integer groupId);


    /**
     * 更新組名
     */
    void updateGroupName(GroupInfoModel groupInfoModel);

    List<GroupInfoDomain> selectGroupNameListBycondition(String accountNumber, int groupId);


    List<GroupAuthorizedModel> queryGroupAuthByConditon(String groupName);

    GroupInfoDomain queryparentGroupList(int groupId);


    Integer countChildGroup(int parentGroupId);

    Integer countGroupMembers(int groupId);


    /**
     * 根据组名查询组
     *
     * @param
     * @return
     */
    public List<GroupInfoDomain> queryGoupInfoByName(GroupInfoDomain form);


    public List<GroupInfoDomain> queryGoupName(@Param(value = "accountNumber") String accountNumber, @Param(value = "groupId") Integer groupId);

    public GroupInfoDomain getGroupByParentId(@Param(value = "parentId") Integer parentId);

    List<GroupInfoDomain> selectGroupListByParentId(@Param(value = "groupId") Integer groupId);

    List<GroupInfoDomain> selectGroupInfoList(GroupAuthorizationMapDomain form);


    public int selectGroupCount(GroupInfoDomain groupInfoDomain);

    List<GroupInfoDomain> selectGroupPageList(@Param(value = "startPageNum") Integer startPageNum, @Param(value = "pageSize") Integer pageSize,
                                              @Param(value = "group") GroupInfoDomain form, @Param(value = "list") List<String> list);


    int selectPageGroupCount(@Param(value = "group") GroupInfoDomain form, @Param(value = "list") List<String> list);


    List<GroupInfoDomain> selectApplicationGroupPage(QueryInfoDomain form);

    int selectApplicationGroupCount(QueryInfoDomain form);


    GroupInfoDomain getGroupInfo(@Param(value = "accountNumber") String accountNumber, @Param(value = "groupId") Integer groupId);

    /**
     * 根据用户的钉钉id和公司id获取对应的本地id
     *
     * @param dingGroupId
     * @return
     */
    Integer getGroupIdByDingGroupId(
            @Param("dingGroupId") String dingGroupId,
            @Param(value = "companyId") String companyId);

    public void updateNewGroup(GroupInfoModel group);

    /**
     * 根据用户的id和appId查询用户组的信息
     * @param userId 用户id
     * @param appId 应用id
     * @return 部门信息
     */
    GroupInfoDomain selectAppGroup(@Param("userId") String userId,@Param("appId") int appId );
    /**
     * 根据公司Id 和部门上级Id查询部门信息
     * @param companyId
     * @param parentId
     */

    List<TreeNodesDomain> getGroupListByParentId(@Param("companyId") String companyId,@Param("parentId") int parentId);


    void insertGroupByWxGroup(WxGroupDomain wxGroupDomain);

    Integer queryGroupIdBYWxId(Long wxId);

    Integer getGroupIdByWxGroupId(
            @Param(value = "wxGroupId") String wxGroupId,
            @Param(value = "companyId") String companyId);
}
