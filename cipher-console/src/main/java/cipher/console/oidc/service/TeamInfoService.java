package cipher.console.oidc.service;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TeamInfoService {


    int insertSelective(TeamInfo record);

    TeamInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamInfo record);
     Map<String,Object> getTeamAuthApplication(TeamInfo teamInfo);
    public Map<String, Object> getTeamInfoPageList(TeamInfo form, DataGridModel pageModel);

    /**
     * create by 田扛
     * create time 2019年3月21日10:31:08
     * 保存安全组授权应用的关联
     * @param teamInfo
     * @return
     */
    Map<String,Object>   saveTeamApplicationMap( TeamInfo teamInfo);
    /**
     * create by 田扛
     * create time 2019年3月20日19:04:05
     * 取消安全组下的某个应用的权限
     * @param form
     * @return
     */
    Map<String, Object> cancelAuthration(TeamInfo form);

    /**
     * create by 田扛
     * create time 2019年3月20日19:04:40
     * 获取某个安全组下的应用
     * @param form
     * @return
     */
    Map<String, Object> getTeamApplications(TeamInfo form, DataGridModel pageModel);

    int deleteInfo(Integer id);

    TeamInfo selectTeamInfo(TeamInfo form);

    List<TeamInfo> getTeamList(String companyId);


    TeamInfo getAppTeamInfo(String userId,int appId);
}
