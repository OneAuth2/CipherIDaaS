package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.GroupAuthorizationMapDomain;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import cipher.console.oidc.model.*;

import java.util.List;
import java.util.Map;

/**
 * 组管理的service层
 *
 * @author sizhao
 * 2018/6/6
 */
public interface GroupService {
    /**
     * create by 田扛
     * create time 2019年3月21日14:27:12
     * 初始化部门选中树
     */
    List<TreeNodesDomain> getNodeTree(List<IdentityGroupMapDomain> list, String companyId);

    /**
     * create by 田扛
     * create time 2019年3月22日16:49:49
     * 获取该部门下的上级部门的字符串
     *
     * @param groupTrees
     * @param
     * @return
     */
    List<GroupInfoDomain> getPath(List<TreeNodesDomain> groupTrees, List<GroupInfoDomain> groupInfo);

    /**
     * 获取所有的组信息
     *
     * @return 组信息数据
     */
    public List<GroupInfoDomain> getAllGroup(String companyId);

    /**
     * 根据组号获取组的信息
     *
     * @param groupId 组号
     * @return 组信息
     */
    public GroupInfoDomain getGroupById(int groupId);


    /**
     * 获取所有的组信息
     *
     * @return 组信息数据
     */
    public List<GroupInfoDomain> queryGroupSelect(String companyId);

    /**
     * 根据主账号查询组的信息
     *
     * @param accountNumber 主账号
     * @return 组信息
     */
    public GroupInfoDomain queryGroupByAccountName(String accountNumber);

    /**
     * 查询所有组的基本信息
     *
     * @param groupBaseModel 组的基本信息
     * @param dataGridModel  表的分页信息
     * @return 组的基本信息
     */
    public Map<String, Object> queryAllGroupBaseInfo(GroupBaseModel groupBaseModel, DataGridModel dataGridModel);

    /**
     * 组授权信息
     *
     * @param groupAuthorizationDetailModel 查询条件
     * @param dataGridModel                 分页信息
     * @return 组授权信息
     */
    public Map<String, Object> groupAuthorizedInfo(GroupAuthorizationDetailModel groupAuthorizationDetailModel, DataGridModel dataGridModel);

    /**
     * 查询组的授权信息
     *
     * @param groupId 组号
     * @return 组的授权信息列表
     */
    public List<GroupAuthorizedModel> queryGroupAuth(int groupId);

    /**
     * 取消组与应用的授权关联关系
     *
     * @param groupAuthorizedModel 删除的授权关系的条件
     * @return 删除的结果 1-失败 0-成功
     */
    public int deleteGroupAuth(GroupAuthorizedModel groupAuthorizedModel);

    /**
     * 添加组和应用的授权关系
     *
     * @param groupAuthorizedModel 组和应用的关联内容
     * @return 添加的结果 0-失败 1-成功
     */
    public int insetGroupAuth(GroupAuthorizedModel groupAuthorizedModel);

    /**
     * 查询一组所有成员的信息
     *
     * @param groupMemberAddModel 所查询的组的信息
     * @param dataGridModel       表格的分页数据
     * @return 改组的所有成员的信息
     */
    public Map<String, Object> queryGroupMember(GroupMemberAddModel groupMemberAddModel, DataGridModel dataGridModel);

    /**
     * 创建新的组
     *
     * @param groupInfoModel 创建的组的信息
     * @return 创建组的结果 0-失败 其他-成功
     */
    public int insertNewGroup(GroupInfoModel groupInfoModel);

    /**
     * 查找改组名是否存在
     *
     * @param groupInfoDomain
     * @return
     */
    List<GroupInfoDomain> getGoupInfoByName(GroupInfoDomain groupInfoDomain);

    /**
     * @param groupInfoModel 编辑的组的信息
     */
    void updateGroup(GroupInfoModel groupInfoModel);


    /**
     * 查询组信息
     *
     * @param groupId
     * @return
     */
    GroupInfoModel geteditGroupEcho(Integer groupId);

    /**
     * 更改组的信息
     *
     * @param groupInfoModel 更改后的组信息
     * @return 更改的结果 0-失败  其他-成功
     */
    public int updateGroupInfo(GroupInfoModel groupInfoModel);

    /**
     * 增加组和用户的关联关系
     *
     * @param userInfoModel 新增加用户和组的关系
     * @return 增加的结果 0-失败 其他-成功
     */
    public int insetIntoGroup(UserInfoModel userInfoModel);

    /**
     * 删除组和应用的关联关系
     *
     * @param userInfoModel 要删除的组合用户的关联关系
     * @return 删除掉的结果 0-失败 其他-成功
     */
    public int deleteGroupMap(UserInfoModel userInfoModel);

    /**
     * 根据组名查找组信息
     *
     * @return 组信息
     */
    public GroupInfoDomain queryGroupByGroupName(Map<String, Object> map);

    public Integer checkUserInfoByGroupId(Integer groupId);

    public Integer checkLowerLevelByGroupId(Integer groupId);

    public void deleteGroup(Integer groupId) throws Exception;


    public GroupInfoDomain queryGroupByName(String groupName);

    GroupInfoDomain queryGroupByNameAndcompanyId(String companyId, String groupName);


    public Integer insertGroup(GroupInfoDomain groupInfoDomain);


    /**
     * 查询一组所有成员的信息
     *
     * @param groupMemberAddModel 所查询的组的信息
     * @param dataGridModel       表格的分页数据
     * @return 改组的所有成员的信息
     */
    public Map<String, Object> queryGroupMemberByGroupId(GroupMemberAddModel groupMemberAddModel, DataGridModel dataGridModel);


    List<GroupInfoDomain> selectNoneGroupList(List<Integer> list);


    /**
     * 更新組名
     */
    void updateGroupName(GroupInfoModel groupInfoModel);

    List<GroupInfoDomain> getGroupNamesById(String accountNumber);


    public List<GroupAuthorizedModel> queryGroupAuthByConditon(String groupName);


    int countGroupMemberByGroupId(GroupMemberAddModel groupMemberAddModel);

    Integer countChildGroup(int parentGroupId);

    Integer countGroupMembers(int groupId);

    int getGroupStruct(List<TreeNodesDomain> result, List<TreeNodesDomain> list, int groupId);


    /**
     * 根据组名查询组
     *
     * @param
     * @return
     */
    public List<GroupInfoDomain> queryGoupInfoByName(GroupInfoDomain form);

    public List<GroupInfoDomain> queryGoupName(String accountNumber, Integer groupId);

    public GroupInfoDomain getGroupByParentId(int parentId);

    public List<GroupInfoDomain> selectGroupInfoList(GroupAuthorizationMapDomain form);


    Map<String, Object> getGroupTree(String companyId);

    List<TreeNodesDomain> getGroupTreeList(List<Integer> groupId, String companyId);

    public void updateNewGroup(GroupInfoModel group);


    /**
     * 获取部门路径
     *
     * @param companyId 公司id
     * @param groupId   部门id
     * @return 部门路径信息
     */
    GroupInfoDomain getGroupPath(String companyId, int groupId);


    /**
     * 根据用户以及当前的应用id找到部门信息
     *
     * @param companyId     公司id
     * @param userId        用户唯一id
     * @param applicationId 应用唯一id
     * @return 部门信息
     */
    GroupInfoDomain getGroupPath(String companyId, String userId, int applicationId);
    /**
     * 根据公司Id 和部门上级Id查询部门信息
     * @param companyId
     * @param parateGroupId
     */

    List<TreeNodesDomain> getGroupListByParentId(String companyId,int parateGroupId);


}
