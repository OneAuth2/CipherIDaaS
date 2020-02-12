package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.TeamInfo;
import cipher.console.oidc.domain.web.TeamUserMapInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.UserInfoModel;

import java.util.List;
import java.util.Map;

public interface TeamUserMapInfoService {

    public Map<String,Object> getTeamUserInfoMapPageList(UserInfoDomain form, DataGridModel pageModel);

    int deleteUserMap(TeamUserMapInfo form);

    List<TeamUserMapInfo> selectTeamUserMap(TeamUserMapInfo form);

    int insertSelective(TeamUserMapInfo record);


    List<TeamInfo> selectTeamUserInfoList(TeamUserMapInfo form);

    List<UserInfoModel> selectUserList(TeamUserMapInfo record);

    TeamUserMapInfo selectTeamUserMapInfo(TeamUserMapInfo form);





}
