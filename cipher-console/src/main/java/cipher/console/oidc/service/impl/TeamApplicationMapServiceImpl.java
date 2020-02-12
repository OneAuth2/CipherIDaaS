package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.TeamApplicationMap;
import cipher.console.oidc.domain.web.TeamInfo;
import cipher.console.oidc.mapper.TeamApplicationMapMapper;
import cipher.console.oidc.service.TeamApplicationMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamApplicationMapServiceImpl implements TeamApplicationMapService {

    @Autowired
    private TeamApplicationMapMapper teamApplicationMapMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return teamApplicationMapMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(TeamApplicationMap record) {
        return teamApplicationMapMapper.insertSelective(record);
    }

    @Override
    public int updateByPrimaryKeySelective(TeamApplicationMap record) {
        return teamApplicationMapMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int deleteTeamApplicationMap(TeamApplicationMap record) {
        return teamApplicationMapMapper.deleteTeamApplicationMap(record);
    }

    @Override
    public List<ApplicationInfo> selectAppList(TeamApplicationMap record) {
        return teamApplicationMapMapper.selectAppList(record);
    }

    @Override
    public List<TeamInfo> selectTeamInfoList(TeamApplicationMap record) {
        return teamApplicationMapMapper.selectTeamList(record);
    }

    @Override
    public TeamApplicationMap selectTeamApplicationInfo(TeamApplicationMap record) {
        return teamApplicationMapMapper.selectTeamApplicationInfo(record);
    }
}
