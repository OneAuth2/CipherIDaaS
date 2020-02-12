package cipher.console.oidc.domain.web;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/3/8 10:17
 */
public class TeamApplicationChecked  {
    private Integer id;
    private String ruleValue;


    private String teamName;
    private String dsgTeamId;
    private static Map<String, Object> stateMap = new HashMap<String, Object>();
    private Map<String, Object> state;
    private boolean  selected=false;//搜索用户时应设置该字段
    static {
        stateMap.put("checked", true);
    }

    public TeamApplicationChecked(TeamInfo teamInfo) {
       this.id= teamInfo.getId();
       this.ruleValue=teamInfo.getRuleValue();
       this.teamName=teamInfo.getTeamName();
       this.dsgTeamId=teamInfo.getDsgTeamId();
    }

    public TeamApplicationChecked() {
    }

    public String getDsgTeamId() {
        return dsgTeamId;
    }

    public void setDsgTeamId(String dsgTeamId) {
        this.dsgTeamId = dsgTeamId;
    }

    @Override
    public String toString() {
        return "TeamApplicationChecked{" +
                "id=" + id +
                ", ruleValue='" + ruleValue + '\'' +
                ", teamName='" + teamName + '\'' +
                ", state=" + state +
                ", selected=" + selected +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState() {
        this.state = stateMap;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
