package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/5/30 17:05
 */
public interface UserGroupService {

    int userAuthCount();


    void insertUserGroup(GroupUserMapDomain groupUserMapDomain);

    List<GroupInfoDomain> selectHaveGroupList(String accountNumber);

    void deleteUserGroupMap(String accountNumber);

    /**
     * 批量插入组合用户的关联关系
     * @param groupUserMapDomainList
     * @throws Exception
     */
    void insertList(@Param("groupUserMapList") List<GroupUserMapDomain> groupUserMapDomainList) throws Exception;


}
