package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.TeamUserMapInfoMapper;
import cipher.console.oidc.model.UserInfoModel;
import cipher.console.oidc.service.TeamUserMapInfoService;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeamUserMapInfoServiceImpl implements TeamUserMapInfoService {


    @Autowired
    private TeamUserMapInfoMapper teamUserMapInfoMapper;
    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Map<String, Object> getTeamUserInfoMapPageList(UserInfoDomain form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new UserInfoDomain() : form);
        form.setPageData(pageModel);
        List<UserInfoDomain> list = teamUserMapInfoMapper.selectTeamUserMapInfoList(form);
        String groupName = "";
        for (UserInfoDomain userInfoDomain : list) {
            List<String> groupNamelist = new ArrayList<>();
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(userInfoDomain.getAccountNumber());
            for (GroupInfoDomain groupInfoDomain : grouplist) {
                groupNamelist.add(groupInfoDomain.getGroupName());
                groupName = StringUtils.join(groupNamelist, ",");
                userInfoDomain.setGroupNames(groupName);
            }

        }
        int total = teamUserMapInfoMapper.selectTeamUserMapInfoCount(form);
        TeamDomain teamDomain = teamUserMapInfoMapper.selectTeamDomainByid(form.getTeamId());
        map.put("teamDomain",teamDomain);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int deleteUserMap(TeamUserMapInfo form) {
        return teamUserMapInfoMapper.deleteUserMap(form);
    }

    @Override
    public List<TeamUserMapInfo> selectTeamUserMap(TeamUserMapInfo form) {
        return teamUserMapInfoMapper.selectTeamUserMap(form);
    }

    @Override
    public int insertSelective(TeamUserMapInfo record) {
        return teamUserMapInfoMapper.insertSelective(record);
    }

    @Override
    public List<TeamInfo> selectTeamUserInfoList(TeamUserMapInfo form) {
        return teamUserMapInfoMapper.selectTeamUserInfoList(form);
    }

    @Override
    public List<UserInfoModel> selectUserList(TeamUserMapInfo record) {
        return teamUserMapInfoMapper.selectUserList(record);
    }

    @Override
    public TeamUserMapInfo selectTeamUserMapInfo(TeamUserMapInfo form) {
        return teamUserMapInfoMapper.selectTeamUserMapInfo(form);
    }
}
