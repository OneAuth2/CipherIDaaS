package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.TeamDomain;
import cipher.console.oidc.domain.web.TeamInfo;
import cipher.console.oidc.domain.web.TeamUserMapInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.UserInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamUserMapInfoMapper {
    int insert(TeamUserMapInfo record);

    int insertSelective(TeamUserMapInfo record);

    List<UserInfoDomain> selectTeamUserMapInfoList(UserInfoDomain form);

    int selectTeamUserMapInfoCount(UserInfoDomain form);

    public int deleteUserMap(TeamUserMapInfo form);

    List<TeamUserMapInfo> selectTeamUserMap(TeamUserMapInfo form);

    public List<TeamInfo> selectTeamUserInfoList(TeamUserMapInfo form);

    List<UserInfoModel> selectUserList(TeamUserMapInfo record);

    int selectCount(@Param(value = "id") int id);

    TeamUserMapInfo selectTeamUserMapInfo(TeamUserMapInfo form);

    TeamDomain selectTeamDomainByid(Integer id);
}