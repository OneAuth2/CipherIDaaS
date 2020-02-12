package cipher.console.oidc.service.impl;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.service.ApplicationAuthService;
import cipher.console.oidc.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationAuthServiceImpl implements ApplicationAuthService{

    @Autowired
    private TeamInfoMapper  teamInfoMapper;
    @Autowired
    private TeamUserMapInfoMapper teamUserMapInfoMapper;
    @Autowired
    private TeamApplicationMapMapper teamApplicationMapMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private ApplicationGroupMapper applicationGroupMapper;
    @Autowired
    private ApplicationUserAuthMapper applicationUserAuthMapper;

    @Override
    public TeamPageInfoDomain queryTeamPageList(String currentPage,TeamInfo teamInfo) {
        TeamApplicationMap record=new TeamApplicationMap();
        record.setApplicationId(teamInfo.getApplicationId());
        List<TeamInfo> teamIds=teamApplicationMapMapper.selectTeamList(record);
        List<String> list=new ArrayList<>();
        for(TeamInfo team:teamIds){
            list.add(String.valueOf(team.getId()));
        }
        int total=teamInfoMapper.selectPageCount(teamInfo,list);
        PageUtils pageUtils=new PageUtils();
        if (currentPage!=null){
            pageUtils.setCurrentPage(Integer.parseInt(currentPage));
        }
        pageUtils.setAllRowsAmount(total);
        Integer startPageNum=pageUtils.getCurrentPageStartRow()-1;
        Integer pageSize=pageUtils.getPageSize();
        List<TeamInfo> teamList=teamInfoMapper.selectTeamPageList(startPageNum,pageSize,teamInfo,list);
        for(TeamInfo teamInfo1:teamList){
            int  teamNum=teamUserMapInfoMapper.selectCount(teamInfo1.getId());
            teamInfo1.setTeamNum(teamNum);
        }

        pageUtils.calculatePage();
        TeamPageInfoDomain teamPageInfoDomain=new TeamPageInfoDomain();
        teamPageInfoDomain.setPrevPage(pageUtils.getPrevPage());
        teamPageInfoDomain.setNextPage(pageUtils.getNextPage());
        teamPageInfoDomain.setShowUsers(teamList);
        teamPageInfoDomain.setShowPageNums(pageUtils.getShowPageNums());
        teamPageInfoDomain.setCurrentPage(pageUtils.getCurrentPage());
       // System.out.println(teamPageInfoDomain);
        return teamPageInfoDomain;
    }

    @Override
    public GroupPageInfoDomain queryGroupPageList(String currentPage, GroupInfoDomain groupInfoDomain) {
        List<String> list=new ArrayList<>();
        List<GroupApplicationModel> groupInfoList=applicationGroupMapper.selectApplicationList(groupInfoDomain.getApplicationId());
        for(GroupApplicationModel groupApplicationModel:groupInfoList){
            list.add(String.valueOf(groupApplicationModel.getGroupId()));
        }
        int total=groupMapper.selectPageGroupCount(groupInfoDomain,list);
        PageUtils pageUtils=new PageUtils();
        if (currentPage!=null){
            pageUtils.setCurrentPage(Integer.parseInt(currentPage));
        }
        pageUtils.setAllRowsAmount(total);
        pageUtils.calculatePage();
        Integer startPageNum=pageUtils.getCurrentPageStartRow()-1;
        Integer pageSize=pageUtils.getPageSize();
       // System.out.println("currentPage"+currentPage+" startPageNumber===================>"+startPageNum+" pagesize=============>"+pageSize);
        List<GroupInfoDomain> groupList=groupMapper.selectGroupPageList(startPageNum,pageSize,groupInfoDomain,list);
        for(GroupInfoDomain groupInfo:groupList){
            GroupUserMapDomain groupUserMapDomain=new GroupUserMapDomain();
            groupUserMapDomain.setGroupId(groupInfo.getGroupId());
            List<GroupInfoDomain> userList=userGroupMapper.selectUserGroupList(groupUserMapDomain);
            groupInfo.setUserNum(userList.size());
        }

        GroupPageInfoDomain groupPageInfoDomain=new GroupPageInfoDomain();
        groupPageInfoDomain.setPrevPage(pageUtils.getPrevPage());
        groupPageInfoDomain.setNextPage(pageUtils.getNextPage());
        groupPageInfoDomain.setShowUsers(groupList);
        groupPageInfoDomain.setShowPageNums(pageUtils.getShowPageNums());
        groupPageInfoDomain.setCurrentPage(pageUtils.getCurrentPage());
       // System.out.println(groupPageInfoDomain);
        return groupPageInfoDomain;
    }

    @Override
    public Map<String, Object> queryApplicationTeamPage(QueryInfoDomain form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new QueryInfoDomain() : form);
        form.setPageData(pageModel);
        List<TeamInfo> list = teamInfoMapper.selectApplicationTeamPage(form);
        if (null != list && list.size() > 0) {
            for (TeamInfo teamInfo : list) {
                int  teamNum=teamUserMapInfoMapper.selectCount(teamInfo.getId());
                teamInfo.setTeamNum(teamNum);
            }
        }
        int total = teamInfoMapper.selectApplicationTeamCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public Map<String, Object> queryApplicationGroupPage(QueryInfoDomain form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new QueryInfoDomain() : form);
        form.setPageData(pageModel);
        List<GroupInfoDomain> list = groupMapper.selectApplicationGroupPage(form);
        if (null != list && list.size() > 0) {
            for (GroupInfoDomain groupInfoDomain : list) {
                GroupUserMapDomain groupUserMapDomain=new GroupUserMapDomain();
                groupUserMapDomain.setGroupId(groupInfoDomain.getGroupId());
                List<GroupInfoDomain> userList=userGroupMapper.selectUserGroupList(groupUserMapDomain);
                groupInfoDomain.setUserNum(userList.size());
            }
        }
        int total = groupMapper.selectApplicationGroupCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }




    @Override
    public void deleteUserAuth(String applicationId, String uuid) {
        applicationUserAuthMapper.deleteUserAuth(applicationId,uuid);
    }

    @Override
    public List<GroupInfoDomain> getDepatment(String applicationId, String uuid) {
        return applicationUserAuthMapper.getDepatment(applicationId,uuid );
    }

    @Override
    public List<TeamInfo> getTeam(String applicationId, String uuid) {
        return applicationUserAuthMapper.getTeam(applicationId,uuid );
    }
}
