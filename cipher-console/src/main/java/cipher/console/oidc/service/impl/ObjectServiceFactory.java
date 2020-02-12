package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.*;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: TK
 * @Date: 2019/4/16 16:00
 */
public class ObjectServiceFactory {

    /**
     * 获取转换后的对象list
     * @param groupList
     * @return
     */
    public static List<IdentityGroupMapDomain> getIdentityGroupMapDomain(List<GroupInfoDomain> groupList){
        //入参校验
        if (groupList == null){
            return null;
        }

        //判断是否为空
        List<IdentityGroupMapDomain> identityGroupMapDomains=new ArrayList<>();
        if (groupList.size()>0){

            //循环转换groupList下的对象转换为IdentityGroupList
            for (GroupInfoDomain groupInfoDomain: groupList
            ) {
                IdentityGroupMapDomain identityGroupMapDomain=new IdentityGroupMapDomain();
                identityGroupMapDomain.setGroupId(groupInfoDomain.getGroupId());
                identityGroupMapDomains.add(identityGroupMapDomain);

            }
        }

        return identityGroupMapDomains;
    }


    /**
     * 获取安全组对象
     * @param accountNumber
     * @return
     */
    public  static TeamUserMapInfo  getTeamUserMapInfo(String accountNumber){
        //入参构造
        if (StringUtils.isBlank(accountNumber)){
            return null;
        }

        //新建对象并返回
        TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
        teamUserMapInfo.setUserId(accountNumber);

        return teamUserMapInfo;
    }

    /**
     * 初始化安全组选中信息
     * @param allTeams
     * @param checkedTeams
     * @return
     */
    public static List<TeamApplicationChecked>  getCheckedTeams(List<TeamInfo> allTeams, List<TeamInfo> checkedTeams){

        //入参校验
        if (allTeams==null || checkedTeams==null){
            return null;
        }

        //初始化安全组选中对象
        List<TeamApplicationChecked> list=new ArrayList<>();

        //循环判断该应用授权的安全组是否在其中，如果是就把state.checked=true
        for (TeamInfo teamInfo:allTeams) {
            //新建TeamApplicationChecked对象 并并判断两个安全组中的重复元素将其设置状态
            TeamApplicationChecked teamApplicationMap=new TeamApplicationChecked(teamInfo);
            for (TeamInfo teaminfo1:checkedTeams) {
                if (teamInfo.getId().equals(teaminfo1.getId())){
                    teamApplicationMap.setState();
                }
            }
            list.add(teamApplicationMap);
        }
        return list;
    }
}
