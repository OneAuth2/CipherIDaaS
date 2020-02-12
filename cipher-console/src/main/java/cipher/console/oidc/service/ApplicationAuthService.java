package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;

import java.util.List;
import java.util.Map;

public interface ApplicationAuthService {

    public TeamPageInfoDomain queryTeamPageList(String currentPage, TeamInfo teamInfo);


    public GroupPageInfoDomain queryGroupPageList(String currentPage, GroupInfoDomain groupInfoDomain);


    public Map<String,Object> queryApplicationTeamPage(QueryInfoDomain form, DataGridModel pageModel);;

    public Map<String,Object> queryApplicationGroupPage(QueryInfoDomain form, DataGridModel pageModel);;

    void deleteUserAuth(String applicationId,String accountNumber);//取消用户与应用的授权

     List<GroupInfoDomain> getDepatment(String applicationId, String accountNumber);//查看該用戶在應用下的授權部門
     List<TeamInfo>    getTeam(String applicationId, String accountNumber);//查看該用戶在應用下的授權部門
}
