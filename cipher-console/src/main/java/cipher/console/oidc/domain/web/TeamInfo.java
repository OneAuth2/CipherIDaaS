package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * modify by 田扛
 * modify time 2019年3月23日14:37:15
 * 增加dsgTeamId字段
 */
public class TeamInfo extends BaseDomain{
    private Integer id;

    private String queryName;
    private String ruleValue;

   private Boolean selected=false;
    private String teamName;

    private Date createTime;

    private Date modifyTime;

    private String applicationName;

    private int teamNum;

    private String ApplicationIds;

    private int type;
    private String dsgTeamId;//dsg安全组id

    private int applicationId;

    private String companyId;


    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public TeamInfo(ApplicationInfoDomain applicationInfoDomain) {
        this.id=applicationInfoDomain.getId();
        this.teamName=applicationInfoDomain.getApplicationName();
    }

    public TeamInfo() {
    }

    private static Map<String, Object> stateMap = new HashMap<String, Object>();
    private Map<String, Object> state;


    static {
        stateMap.put("checked", true);
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setState() {
        this.state = stateMap;
    }


    public Map<String, Object> getState() {
        return state;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public void setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getApplicationIds() {
        return ApplicationIds;
    }

    public void setApplicationIds(String applicationIds) {
        ApplicationIds = applicationIds;
    }

    public String getDsgTeamId() {
        return dsgTeamId;
    }

    public void setDsgTeamId(String dsgTeamId) {
        this.dsgTeamId = dsgTeamId;
    }
}
