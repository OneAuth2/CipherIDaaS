package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.domain.web.TeamInfo;
import cipher.console.oidc.domain.web.TeamUserMapInfo;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.NewUserMapper;
import cipher.console.oidc.mapper.TeamUserMapInfoMapper;
import cipher.console.oidc.service.WelcomeUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WelcomeUserServiceImpl implements WelcomeUserService {

    @Autowired
    private NewUserMapper newUserMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TeamUserMapInfoMapper teamUserMapInfoMapper;




    @Override
    public Map<String, Object> getNoGroupUserList(NewUserInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new NewUserInfo() : form);
        form.setPageData(pageModel);
        List<NewUserInfo> list = newUserMapper.getNoGroupUserList(form);
        list= getGroupList(list);
        list= getTeamList(list);
        int count= newUserMapper.getNoGroupUserCount(form);
        map.put("rows", list);
        map.put("total", count);
        map.put("records", count);
        return map;
    }

    @Override
    public Map<String, Object> getLockUserList(NewUserInfo form,DataGridModel page) {
        List<NewUserInfo> list = newUserMapper.getAllList(form);
        Map<String, Object> map = new HashMap<>();
        List<NewUserInfo> newList = new ArrayList<>();
        for(NewUserInfo newUserInfo:list) {
            boolean result = redisTemplate.hasKey(CacheKey.getUserLoginFailedInfoCacheKey(newUserInfo.getUuid()));
            if (result == true) {
                newUserInfo.setStatus("锁定");
                newList.add(newUserInfo);
            }
        }

        /**
         * modigy by cozi
         * @date 2019-08-16
         * 增加部门和安全组
         */
        String groupName = "";
        String teamName = "";

        for (NewUserInfo newUserInfo : newList) {
            TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
            teamUserMapInfo.setUserId(newUserInfo.getUuid());
            List<TeamInfo> teamList = teamUserMapInfoMapper.selectTeamUserInfoList(teamUserMapInfo);
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(newUserInfo.getUuid());
            List<String> roleNameList = new ArrayList<>();
            List<String> teamInfoList = new ArrayList<>();
            List<String> groupNamelist = new ArrayList<>();

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

        int pages = newList.size() / page.getRows();
        if (pages * page.getRows() != newList.size()) {
            pages++;
        }
            newList= getGroupList(newList);
            newList= getTeamList(newList);
            if(newList.size()>0){
            if (page.getPage() == pages) {
                newList = newList.subList((page.getPage() - 1) * page.getRows(), newList.size());
                map.put("rows", newList);
            } else {
                newList = newList.subList((page.getPage() - 1) * page.getRows(), (page.getPage()) * page.getRows());
                map.put("rows", newList);
            }
            }

        map.put("total", pages);
        map.put("records", newList.size());
        return map;
    }








    @Override
    public Map<String, Object> getNoLoginUserList(NewUserInfo form,DataGridModel page) {
            Map<String, Object> map = new HashMap<>();
        form = (form == null ? new NewUserInfo() : form);
        form.setPageData(page);
        List<String> newList=newUserMapper.getNoLoginUser(form);
        List<NewUserInfo> userList=newUserMapper.getUserListByCondition(newList,form);
        userList= getGroupList(userList);
        userList= getTeamList(userList);
        int count= newUserMapper.getUserListByConditionCount(newList,form);
        map.put("rows", userList);
        map.put("total", count);
        map.put("records", count);
        return map;
    }




    public List<NewUserInfo> getGroupList(List<NewUserInfo> userInfoList){
        if(null!=userInfoList&&userInfoList.size()>0){
            String groupName="";
            for(NewUserInfo newUserInfo:userInfoList){
                List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(newUserInfo.getAccountNumber());
                List<String> groupNamelist = new ArrayList<>();
                for (GroupInfoDomain groupInfoDomain : grouplist) {
                    groupNamelist.add(groupInfoDomain.getGroupName());
                    groupName = StringUtils.join(groupNamelist, ",");
                    newUserInfo.setGroupName(groupName);
                }
            }
        }
        return userInfoList;
    }



    public List<NewUserInfo> getTeamList(List<NewUserInfo> userInfoList){
        if(null!=userInfoList&&userInfoList.size()>0) {
            String teamName = "";
            for (NewUserInfo newUserInfo : userInfoList) {
                List<String> teamInfoList = new ArrayList<>();
                TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
                teamUserMapInfo.setUserId(newUserInfo.getAccountNumber());
                List<TeamInfo> teamList = teamUserMapInfoMapper.selectTeamUserInfoList(teamUserMapInfo);
                for (TeamInfo teamInfo : teamList) {
                    teamInfoList.add(teamInfo.getTeamName());
                    teamName = StringUtils.join(teamInfoList, ",");
                    newUserInfo.setTeamName(teamName);
                }
            }
        }
        return userInfoList;
    }
}
