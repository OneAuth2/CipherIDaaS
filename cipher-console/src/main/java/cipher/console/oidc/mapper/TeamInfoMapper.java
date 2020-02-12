package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.QueryInfoDomain;
import cipher.console.oidc.domain.web.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamInfoMapper {
      List<ApplicationInfoDomain> getAllApplication(String companyId);
     List<ApplicationInfoDomain> getAllTeamApplicationList(TeamInfo teamInfo);
      List<ApplicationInfoDomain> getTeamApplicationList(TeamInfo teamInfo);
      int getTeamApplicationListCount(TeamInfo teamInfo);
    int deleteByPrimaryKey(Integer id);

    int insert(TeamInfo record);

    int insertSelective(TeamInfo record);

    TeamInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeamInfo record);

    int updateByPrimaryKey(TeamInfo record);

    TeamInfo selectTeamInfo(TeamInfo form);

    List<TeamInfo> selectTeamInfoList(TeamInfo form);

    int selectCount(TeamInfo form);

    List<TeamInfo> getTeamList(String companyId);

    List<TeamInfo> selectTeamPageList(@Param(value = "startPageNum") Integer startPageNum, @Param(value="pageSize") Integer pageSize,
                                     @Param(value = "teamInfo") TeamInfo form,@Param(value = "list") List<String>list);


    int selectPageCount(@Param(value = "teamInfo") TeamInfo form,@Param(value = "list") List<String>list);


    List<TeamInfo> selectApplicationTeamPage(QueryInfoDomain form);

    int selectApplicationTeamCount(QueryInfoDomain form);


    TeamInfo getAppTeamInfo(@Param("userId") String userId,
                            @Param("appId") int appId);

}
