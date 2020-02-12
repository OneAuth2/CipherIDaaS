package cipher.console.oidc.domain.web;

public class TeamDomain {

    private Integer id;
    private String teamName;
    private String dsgTeamId;

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
        this.teamName = teamName;
    }

    public String getDsgTeamId() {
        return dsgTeamId;
    }

    public void setDsgTeamId(String dsgTeamId) {
        this.dsgTeamId = dsgTeamId;
    }

    @Override
    public String toString() {
        return "TeamDomain{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", dsgTeamId='" + dsgTeamId + '\'' +
                '}';
    }
}
