package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.UserGroupMapper;
import cipher.console.oidc.service.GroupService;
import cipher.console.oidc.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/5/30 17:05
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public int userAuthCount() {
        return userGroupMapper.userAuthCount();
    }

    @Override
    public void insertUserGroup(GroupUserMapDomain groupUserMapDomain) {
        userGroupMapper.insertUserGroupMap(groupUserMapDomain);
    }

    @Override
    public List<GroupInfoDomain> selectHaveGroupList(String accountNumber) {
        List<GroupInfoDomain> list=userGroupMapper.selectHaveGroupList(accountNumber);
        for(GroupInfoDomain domain:list){
            GroupInfoDomain groupInfo=groupMapper.getGroupById(domain.getGroupId());
            domain.setGroupName(groupInfo.getGroupName());
        }
        return list;
    }

    @Override
    public void deleteUserGroupMap(String uuid) {
        userGroupMapper.deleteUserGroupMap(uuid);

    }

    @Override
    public void insertList(List<GroupUserMapDomain> groupUserMapDomainList) throws Exception {
        userGroupMapper.insertList(groupUserMapDomainList);
    }


}
