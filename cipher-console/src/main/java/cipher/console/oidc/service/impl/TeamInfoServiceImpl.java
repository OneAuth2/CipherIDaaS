package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.TeamApplicationMapMapper;
import cipher.console.oidc.mapper.TeamInfoMapper;
import cipher.console.oidc.mapper.TeamUserMapInfoMapper;
import cipher.console.oidc.service.AppService;
import cipher.console.oidc.service.TeamInfoService;
import cipher.console.oidc.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TeamInfoServiceImpl implements TeamInfoService {

    @Autowired
    private TeamInfoMapper teamInfoMapper;
    @Autowired
    private TeamApplicationMapMapper teamApplicationMapMapper;

    @Autowired
    private TeamUserMapInfoMapper teamUserMapInfoMapper;

    @Autowired
    private AppService appService;


    @Override
    public int insertSelective(TeamInfo record) {
        String dsgTeamId= NumberUtil.getStringRandom(8);
        record.setDsgTeamId(dsgTeamId);
        return teamInfoMapper.insertSelective(record);
    }

    @Override
    public TeamInfo selectByPrimaryKey(Integer id) {
        return teamInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(TeamInfo record) {
        return teamInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Map<String, Object> getTeamAuthApplication(TeamInfo teamInfo) {
        Map<String,Object> map=new HashMap<>();
        List<ApplicationInfoDomain> list=teamInfoMapper.getAllTeamApplicationList(teamInfo);//该安全组的列表
        List<ApplicationInfoDomain> list1=teamInfoMapper.getAllApplication(teamInfo.getCompanyId());//所有应用的列表
        List<TeamInfo> list2=new ArrayList<>();//所有安全组的列表
        List<TeamInfo> list3=new ArrayList<>();//所有应用的列表
        for ( ApplicationInfoDomain applicationInfoDomain : list1){   //应用转换为安全组
            TeamInfo teamInfo1=new TeamInfo(applicationInfoDomain);
            list2.add(teamInfo1);
        }
        //应用转换为安全组
        for ( ApplicationInfoDomain application:list) {
            if (null != application) {
                TeamInfo teamInfo2 = new TeamInfo(application);
                list3.add(teamInfo2);
            }
        }

         List<TeamApplicationChecked> teams= getCheckedTeams(list2,list3);//初始化选中安全组
        map.put("code",0);
        map.put("msg",teams);
        return map;
    }
    /**
     * create by 田扛
     * create time 2019年3月8日10:24:41
     * 获取安全组是否被选中，设置选中的state为checked
     */
    public List<TeamApplicationChecked>  getCheckedTeams(List<TeamInfo> allTeams,List<TeamInfo> checkedTeams){
        List<TeamApplicationChecked> listDemo=new ArrayList<>();
        for (TeamInfo teams:allTeams) {    //循环判断该应用授权的安全组是否在其中，如果是就把state.checked=true
            TeamApplicationChecked teamApplicationMap=new TeamApplicationChecked();
            if (teams!=null){
               teamApplicationMap=new TeamApplicationChecked(teams);
            }

            for (TeamInfo teams1:checkedTeams) {
                if (teams1!=null&&teamApplicationMap.getId()!=null){
                    if (teams.getId().equals(teams1.getId())){
                        teamApplicationMap.setState();
                    }
                }

            }
            listDemo.add(teamApplicationMap);

        }
        return listDemo;

    }

    @Override
    public Map<String, Object> getTeamInfoPageList(TeamInfo form, DataGridModel pageModel) {
        form=form==null?new TeamInfo():form;
        form.setPageData(pageModel);
        Map<String,Object> map=new HashMap<>();
        List<TeamInfo> list=teamInfoMapper.selectTeamInfoList(form);
        String applicationName="";
        for(TeamInfo teamInfo:list){
            List<String> applicationList=new ArrayList<>();
            List<TeamInfo> teamInfoList=teamApplicationMapMapper.selectTeamApplication(teamInfo.getId());
            for(TeamInfo domain:teamInfoList){
                applicationList.add(domain.getApplicationName());
                applicationName= StringUtils.join(applicationList, ",");
                teamInfo.setApplicationName(applicationName);
            }
            int  teamNum=teamUserMapInfoMapper.selectCount(teamInfo.getId());
            teamInfo.setTeamNum(teamNum);

        }
        int total=teamInfoMapper.selectCount(form);

        map.put("rows",list);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> saveTeamApplicationMap(TeamInfo teamInfo) {
        Map<String,Object> map=new HashMap<>();
        try {
            TeamApplicationMap teamApplicationMap=new TeamApplicationMap();
            teamApplicationMap.setTeamId(teamInfo.getId());
            teamApplicationMapMapper.deleteTeamApplicationMap(teamApplicationMap);//删除该安全组绑定的所有应用
            if (StringUtils.isNotEmpty(teamInfo.getApplicationIds())){
                String[] applicationIds=teamInfo.getApplicationIds().split(",");
                for (int i=0;i<applicationIds.length;i++){
                    teamApplicationMap.setTeamId(teamInfo.getId());
                    teamApplicationMap.setApplicationId(Integer.valueOf(applicationIds[i]));
                    teamApplicationMapMapper.insert(teamApplicationMap);
                }
            }

            map.put("code",0);
            map.put("msg","保存成功");

        }catch (Exception e){
            map.put("code",1);
            map.put("msg","内部服务器错误");
            e.printStackTrace();
            return map;
        }

        return map;
    }

    @Override
    public Map<String, Object> cancelAuthration(TeamInfo form) {
        Map<String,Object> map=new HashMap<>();
        try {
            teamApplicationMapMapper.cancelAuthration(form);
           map.put("code",0);
           map.put("msg","取消安全组授权应用成功");

        }catch (Exception e){
           map.put("code",1);
           map.put("msg","内部服务器错误");
           return  map;
        }
        return map;
    }

    @Override
    public Map<String, Object> getTeamApplications(TeamInfo form, DataGridModel pageModel) {
        Map<String,Object> map=new HashMap<>();
        form=form==null?new TeamInfo():form;
        form.setPageData(pageModel);
        try{
            List<ApplicationInfoDomain> list=teamInfoMapper.getTeamApplicationList(form);
            int count =teamInfoMapper.getTeamApplicationListCount(form);
            map.put("code",0);
            map.put("rows",list);
            map.put("total",count);
        }catch (Exception e){
            map.put("code",1);
            map.put("msg","内部服务器错误");
            e.printStackTrace();
            return map;
        }
        return map;
    }

    @Override
    public int deleteInfo(Integer id) {
        return teamInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public TeamInfo selectTeamInfo(TeamInfo form) {
        return teamInfoMapper.selectTeamInfo(form);
    }

    @Override
    public List<TeamInfo> getTeamList(String companyId) {
        return teamInfoMapper.getTeamList(companyId);
    }

    @Override
    public TeamInfo getAppTeamInfo(String userId, int appId) {
        return teamInfoMapper.getAppTeamInfo(userId,appId);
    }
}
