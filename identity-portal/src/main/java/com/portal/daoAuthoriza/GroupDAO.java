package com.portal.daoAuthoriza;



import com.portal.domain.GroupInfoDomain;
import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.UserGroupMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupDAO {
    /**
     * to commit
     * @param form
     * @return
     */
    List<GroupInfoDomain> selectGroupInfoList(GroupInfoDomain form);

    GroupInfoDomain selectGroupInfo(GroupInfoDomain form);

    List<GroupInfoDomain> getAllGroup();

    void insertUserGroupMap(UserGroupMap userGroupMap);


    List<Integer> queryGroupByAccount(String uuid);

    /**
     * 获取全局认证类型
     * @return
     */
    public IdentityAuthTypeDomain queryGlobalAuthType();

    /**
     * 根据用户所在组获取策略
     * @return
     */
    public IdentityAuthTypeDomain queryAuthTypeByGroupIdList(@Param("groupIdList") List<Integer> groupIdList);

    /**
     * 获取默认的认证策略
     * @return
     */
    public IdentityAuthTypeDomain queryDefaultAuthType(@Param("companyUUid")String companyUUid);


    /**
     * 根据公司id和主账号获取用户所在的组
     * @param companyUUid
     * @param accountNumber
     * @return
     */
    List<Integer> queryGroupByCompanyIdAndAccount(@Param(value = "companyUUid") String companyUUid,@Param(value = "accountNumber") String accountNumber);

    /**
     * 根据公司id获取默认策略
     * @param companyUUid
     * @return
     */
    IdentityAuthTypeDomain queryDefaultAuthTypeByCompanyId(String companyUUid);

    /**
     * 根据部门获取用户属于哪个策略
     * @param groupIdList
     * @param companyUUid
     * @return
     */
    IdentityAuthTypeDomain queryAuthTypeByGroupIdListAndCompanyUUid(@Param(value = "groupIdList") List<Integer> groupIdList,@Param(value="companyUUid")String companyUUid);

    IdentityAuthTypeDomain queryAuthTypeByUserId(@Param(value = "userId")String userId,@Param(value = "companyId")String companyId);

    /**
     * create by 安歌
     * create time 2019年7月25日15:14:53
     * 根据uuid获取用户所在部门的集合
     * @param uuid
     * @return
     */
    List<GroupInfoDomain> getGroupsByUuid(String uuid);

    /**
     * create by 安歌
     * create time 2019年7月25日15:50:13
     * 根据部门id获取部门信息
     * @param groupId
     * @return
     */
    GroupInfoDomain getGroupsByGroupId(int groupId);
}
