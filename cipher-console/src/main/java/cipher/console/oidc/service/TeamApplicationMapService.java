package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.TeamApplicationMap;
import cipher.console.oidc.domain.web.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamApplicationMapService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(TeamApplicationMap record);

    int updateByPrimaryKeySelective(TeamApplicationMap record);

    int deleteTeamApplicationMap(TeamApplicationMap record);


    List<ApplicationInfo> selectAppList(TeamApplicationMap record);

    List<TeamInfo> selectTeamInfoList(TeamApplicationMap record);

    TeamApplicationMap selectTeamApplicationInfo(TeamApplicationMap record);


}