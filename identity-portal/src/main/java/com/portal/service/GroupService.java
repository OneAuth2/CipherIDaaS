package com.portal.service;



import com.portal.domain.GroupInfoDomain;
import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.TreeNodesDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupService {

    List<GroupInfoDomain> getGroupInfoList(GroupInfoDomain form);

    GroupInfoDomain getGroupInfoInfo(GroupInfoDomain form);


    public int getGroupStruct(List<TreeNodesDomain> result, List<TreeNodesDomain> list, int groupId);

    public List<TreeNodesDomain> getGroupList();




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
    public IdentityAuthTypeDomain queryDefaultAuthType(String companyUUid);


    List<Integer> queryGroupByCompanyUuidAndAccount(String companyUUid, String accountNumber);

    /**
     * 获取默认策略
     * @param companyUUid
     * @return
     */
    IdentityAuthTypeDomain queryDefaultAuthTypeByCompanyId(String companyUUid);

    IdentityAuthTypeDomain queryAuthTypeByGroupIdListAndCompanyUUid(List<Integer> groupIdList, String companyUUid);

    IdentityAuthTypeDomain queryAuthTypeByUserId(String userId,String companyId);

    /**
     * create by 安歌
     * create time 2019年7月25日15:08:00
     * 根据uuid获取用户所在的部门下字符串的拼接多个部门以“，”拼接
     * @param uuid
     * @return
     */
    String getGroupsStringByUuid(String uuid);
}
