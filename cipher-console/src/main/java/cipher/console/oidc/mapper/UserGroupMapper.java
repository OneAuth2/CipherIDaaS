package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/5/30 17:02
 */
public interface UserGroupMapper {

    /**
     * 已经在组里的用户统计
     * @return
     */
    public int userAuthCount();

    /**
     * 插入用户和组的关联关系
     * @param groupUserMapDomain
     */
    public void insertUserGroupMap(GroupUserMapDomain groupUserMapDomain);

    List<GroupInfoDomain> selectHaveGroupList(@Param(value = "accountNumber") String accountNumber);

    void deleteUserGroupMap(@Param(value = "uuid") String uuid);

    /**
     * 批量插入组合用户的关联关系
     * @param groupUserMapDomainList
     * @throws Exception
     */
    void insertList(@Param("groupUserMapList") List<GroupUserMapDomain> groupUserMapDomainList) throws Exception;


    List<GroupInfoDomain> selectUserGroupList(GroupUserMapDomain groupUserMapDomain);

    List<Integer> queryGroupByAccount(@Param(value = "uuid") String uuid,
                                      @Param(value = "groupId")Integer groupId);

    public void insertListWithUUid(@Param("groupUserMapList") List<GroupUserMapDomain> groupUserMapDomainList);

    String selectUuidByaccountNumber(@Param(value = "accountNumber") String accountNumber);

    GroupUserMapDomain selectUserGroup(GroupUserMapDomain groupUserMapDomain);


}
