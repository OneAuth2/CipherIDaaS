package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.TeamApplicationMap;
import cipher.console.oidc.domain.web.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamApplicationMapMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeamApplicationMap record);

    int insertSelective(TeamApplicationMap record);

    TeamApplicationMap selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamApplicationMap record);

    int updateByPrimaryKey(TeamApplicationMap record);

    List<TeamInfo> selectTeamApplication(@Param(value = "id") int id);

    int cancelAuthration(TeamInfo form);
    int selectCount(@Param(value = "id") int id);

    public int deleteTeamApplicationMap(TeamApplicationMap record);

    List<ApplicationInfo> selectAppList(TeamApplicationMap record);

    List<TeamInfo> selectTeamList(TeamApplicationMap record);

    TeamApplicationMap selectTeamApplicationInfo(TeamApplicationMap record);
}
