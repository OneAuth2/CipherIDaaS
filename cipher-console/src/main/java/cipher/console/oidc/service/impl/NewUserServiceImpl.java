package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.bto.UserInfoBto;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 95744 on 2018/9/25.
 */

@Service
public class NewUserServiceImpl implements NewUserService {

    @Autowired
    private NewUserMapper newUserMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleInfoDomainMapper roleInfoDomainMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private TeamUserMapInfoMapper teamUserMapInfoMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private SubAccountMapService subAccountMapService;

    @Autowired
    private PasswordAuthorizationService passwordAuthorizationService;

    @Autowired
    private PasswordSettingService passwordSettingService;
    @Autowired
    private RoleUserMapService roleUserMapService;

    @Autowired
    private TeamUserMapInfoService teamUserMapInfoService;


    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private RedisClient<String, Object> redisClient;

    @Autowired
    private RegisterApprovalMapper registerApprovalMapper;

    public static List removeDuplicate(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    @Override
    public Map<String, Object> getNewUserList(NewUserInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new NewUserInfo() : form);
        form.setPageData(pageModel);
        List<NewUserInfo> list = new ArrayList<>();
        int total = 0;
        String groupName = "";
        String teamName = "";
        //正常分页
        total = newUserMapper.selectNewUserCount(form);
        if (null != form && StringUtils.isNotEmpty(form.getStatus()) && form.getStatus().equals("启用") || form.getStatus().equals("停用") || form.getStatus().equals("全部")) {
            list = newUserMapper.selectNewUserInfo(form);
            for (NewUserInfo newUserInfo : list) {
                boolean result = getUserLockStatus(newUserInfo.getUuid());
                if("0".equals(newUserInfo.getGroupId())||newUserInfo.getGroupId()==null||StringUtils.isEmpty(newUserInfo.getGroupId())){
                    newUserInfo.setGroupName("无部门用户");
                }
                if (result == true) {
                    newUserInfo.setStatus("锁定");
                }
            }
            map.put("total", total);
            map.put("rows", list);
        }
        //锁定用户分页
        if (null != form && StringUtils.isNotEmpty(form.getStatus()) && form.getStatus().equals("锁定")) {
            form.setStatus("0");
            list = getLockUserList(form);
            int pages = list.size() / pageModel.getRows();
            if (pages * pageModel.getRows() != list.size()) {
                pages++;
            }
            if (list.size() > 0) {
                if (pageModel.getPage() == pages) {
                    list = list.subList((pageModel.getPage() - 1) * pageModel.getRows(), list.size());
                    map.put("rows", list);
                } else {
                    list = list.subList((pageModel.getPage() - 1) * pageModel.getRows(), (pageModel.getPage()) * pageModel.getRows());
                    map.put("rows", list);
                }
            }
            map.put("total", list.size());
        }

        for (NewUserInfo newUserInfo : list) {
            TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
            teamUserMapInfo.setUserId(newUserInfo.getUuid());
            List<TeamInfo> teamList = teamUserMapInfoMapper.selectTeamUserInfoList(teamUserMapInfo);
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(newUserInfo.getUuid());
            List<String> groupNamelist = new ArrayList<>();
            List<String> teamInfoList = new ArrayList<>();
            for (GroupInfoDomain groupInfoDomain : grouplist) {
                groupNamelist.add(groupInfoDomain.getGroupName());
                groupName = StringUtils.join(groupNamelist, ",");
                newUserInfo.setGroupName(groupName);
            }
            for (TeamInfo teamInfo : teamList) {
                teamInfoList.add(teamInfo.getTeamName());
                teamName = StringUtils.join(teamInfoList, ",");
                newUserInfo.setTeamName(teamName);
            }
        }

        return map;
    }

    @Override
    public void reallyDelete(String uuid) {
        //删除权限组
        roleUserMapService.deleteRoleUserMapByAccountNumber(uuid);
        //上次部门关联
        userGroupService.deleteUserGroupMap(uuid);
        //删除钉钉绑定
        dingUserService.deleteUserMap(uuid);
        //删除密码
        passwordAuthorizationService.deleteAccountPassword(uuid);
        //删除安全组
        TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
        teamUserMapInfo.setUserId(uuid);
        teamUserMapInfoService.deleteUserMap(teamUserMapInfo);
        SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
        subAccountMapDomain.setAccountNumber(uuid);
        //删除子账号关联
        subAccountMapService.deleteSubAccountMap(subAccountMapDomain);
        //删除用户
        newUserMapper.reallyDelete(uuid);
        //删除大白关联
        registerApprovalMapper.deleteDabby(uuid);
        //删除赛赋关联
        registerApprovalMapper.deletePlat(uuid);
        //删除ad关联关系
        registerApprovalMapper.deleteAdBind(uuid);
        //删除用户与应用的关联关系
        registerApprovalMapper.deleteUserApplication(uuid);
    }

    @Override
    public void updateByIsdelete(String uuid, int isdelete) {
        newUserMapper.updateByIsdelete(uuid, isdelete);
    }

    @Override
    public Map<String, Object> getDeleteUserList(NewUserInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new NewUserInfo() : form);
        form.setPageData(pageModel);
        map.put("rows", newUserMapper.selectDeleteUserInfo(form));
        map.put("total", newUserMapper.selectDeleteUserCount(form));
        return map;
    }

    @Override
    public List<NewUserInfo> getUserListByGroupId(NewUserInfo form) {
        return newUserMapper.getUserListByGroupId(form);
    }

    @Override
    public List<TreeNodesDomain> getAllUserList(String companyId) {
        return newUserMapper.getAllUserList(companyId);
    }

    @Override
    public Map<String, Object> getUserListByGroupId(NewUserInfo form, DataGridModel page) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new NewUserInfo() : form);
        List<NewUserInfo> newlist = new ArrayList<>();
        if("0".equals(form.getGroupId())){
            newlist = newUserMapper.getNewUserByGroupId(form);
            for(NewUserInfo newUserInfo:newlist){
                newUserInfo.setGroupName("无部门用户");
            }
        }else{
            if (null != form && StringUtils.isNotEmpty(form.getStatus()) && form.getStatus().equals("锁定")) {
                form.setStatus("全部");
                newlist = getLockUserLista(form);
                form.setStatus("锁定");

            } else {
                newlist = getListByGroupId(Integer.valueOf(form.getGroupId()), form);
            }
        }

        //用户去重
        List<NewUserInfo> list = new ArrayList<NewUserInfo>();
        Set<String> set = new HashSet<String>();
        for (NewUserInfo newUserInfo : newlist) {
            if (newUserInfo == null) {
                continue;
            }
            String userName = newUserInfo.getAccountNumber();
            if (userName != null) {
                if (!set.contains(userName)) { //set中不包含重复的
                    set.add(userName);
                    list.add(newUserInfo);
                } else {
                    continue;
                }
            }
        }
        set.clear();

         //总条数
        map.put("total", list.size());

        //分页
        int pages = list.size() / page.getRows();
        if (pages * page.getRows() != list.size()) {
            pages++;
        }
        if (newlist.size() > 0 && list.size()>0) {
            if (page.getPage() == pages) {
                list = list.subList((page.getPage() - 1) * page.getRows(), list.size());
                map.put("rows", list);
            } else {
                list = list.subList((page.getPage() - 1) * page.getRows(), (page.getPage()) * page.getRows());
                map.put("rows", list);
            }
        }





        for (NewUserInfo newUserInfo : list) {
            String groupName = "";
            String teamName = "";
            List<String> teamInfoList = new ArrayList<>();
            List<String> groupNamelist = new ArrayList<>();
            TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
            teamUserMapInfo.setUserId(newUserInfo.getUuid());
            List<TeamInfo> teamList = teamUserMapInfoMapper.selectTeamUserInfoList(teamUserMapInfo);
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(newUserInfo.getUuid());

            for (GroupInfoDomain groupInfoDomain : grouplist) {
                groupNamelist.add(groupInfoDomain.getGroupName());
                groupName = StringUtils.join(groupNamelist, ",");
                newUserInfo.setGroupName(groupName);
            }
            for (TeamInfo teamInfo : teamList) {
                teamInfoList.add(teamInfo.getTeamName());
                teamName = StringUtils.join(teamInfoList, ",");
                newUserInfo.setTeamName(teamName);
            }
        }

        map.put("return_code", "0");
        return map;

    }

    @Override
    public List<NewUserInfo> queryUserList(NewUserInfo form) {
        return newUserMapper.queryUserList(form);
    }

    @Override
    public NewUserInfo getNewUserInfo(String uuid) {
        return newUserMapper.getNewUserInfo(uuid);
    }

    @Override
    public List<TreeNodesDomain> getUserListByGroupId(String companyId, Integer groupId) {
        return newUserMapper.getNewUserListByGroupId(companyId,groupId);
    }

    public List<NewUserInfo> getListByGroupId(int groupId, NewUserInfo form) {
        List<NewUserInfo> newUserInfoList = new ArrayList<>();
        List<GroupInfoDomain> groupInfoDomainList = groupMapper.selectGroupListByParentId(groupId);
        for (GroupInfoDomain GroupInfoDomain : groupInfoDomainList) {
            List<NewUserInfo> userlist = getNewUserListByGroupId(groupId, form);
            newUserInfoList.addAll(userlist);
            if (GroupInfoDomain.getParentGroupId() == groupId) {
                List<NewUserInfo> list = getListByGroupId(GroupInfoDomain.getGroupId(), form);
                newUserInfoList.addAll(list);
            }
        }

        List<NewUserInfo> userlist = getNewUserListByGroupId(groupId, form);
        newUserInfoList.addAll(userlist);
        return newUserInfoList;

    }

    //获取当前部门下成员
    public List<NewUserInfo> getNewUserListByGroupId(int groupId, NewUserInfo form) {
        form.setGroupId(String.valueOf(groupId));
        List<NewUserInfo> userList = newUserMapper.getUserListByGroupId(form);
        return userList;
    }

    public List<NewUserInfo> getLockUserList(NewUserInfo form) {
        List<NewUserInfo> userInfoList= newUserMapper.getNewAllList(form);
        List<NewUserInfo> newUserInfoList = new ArrayList<>();
        for (NewUserInfo newUserInfo : userInfoList) {
            boolean result = redisTemplate.hasKey(CacheKey.getUserLoginFailedInfoCacheKey(newUserInfo.getUuid()));
            if (result == true) {
                newUserInfo.setStatus("锁定");
                newUserInfoList.add(newUserInfo);
            }
        }
        return newUserInfoList;
    }

    public List<NewUserInfo> getLockUserLista(NewUserInfo form) {
    List<NewUserInfo> userInfoList = getListByGroupId(Integer.valueOf(form.getGroupId()), form);
    List<NewUserInfo> newUserInfoList = new ArrayList<>();
    for (NewUserInfo newUserInfo : userInfoList) {
        boolean result = redisTemplate.hasKey(CacheKey.getUserLoginFailedInfoCacheKey(newUserInfo.getUuid()));
        if (result == true) {
            newUserInfo.setStatus("锁定");
            newUserInfoList.add(newUserInfo);
        }
    }
    return newUserInfoList;
}



    //获取用户锁定状态
    public boolean getUserLockStatus(String uuid) {
        Boolean isfreezd = (Boolean) redisClient.get(CacheKey.getCacheUserIsfreezed(uuid));
        if (null == isfreezd) {
            return false;
        }
        System.out.println("isfreezd--------" + isfreezd);
        return isfreezd;

       /* // 缓存中存放的是Map对象，将Map对象的数据设置到UserLoginFailedInfo对象中
        Map<String, Object> userLoginFailedInfoMap = (HashMap<String, Object>) obj;
        if (userLoginFailedInfoMap.get("account") == null || userLoginFailedInfoMap.get("failedNum") == null || userLoginFailedInfoMap.get("firstFailedTime") == null) {
            return false;
        }
        UserLoginFailedInfo userLoginFailedInfo = new UserLoginFailedInfo();
        userLoginFailedInfo.setFreezed(userLoginFailedInfoMap.get("isFreezed") == null ? false : (Boolean) userLoginFailedInfoMap.get("isFreezed"));
        if ((userLoginFailedInfo.isFreezed())) {// 未被锁定
            return true;
        }
            return false;
        }
*/

    }

}
