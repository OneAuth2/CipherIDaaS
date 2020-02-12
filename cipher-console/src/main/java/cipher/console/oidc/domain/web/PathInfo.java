package cipher.console.oidc.domain.web;

import java.util.List;

public class PathInfo {
    private GroupInfoDomain groupList;
    private TeamInfo teamList;

    public GroupInfoDomain getGroupList() {
        return groupList;
    }

    public void setGroupList(GroupInfoDomain groupList) {
        this.groupList = groupList;
    }

    public TeamInfo getTeamList() {
        return teamList;
    }

    public void setTeamList(TeamInfo teamList) {
        this.teamList = teamList;
    }
}
