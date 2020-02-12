package cipher.console.oidc.domain.web;

public class GroupAuthorizationMapDomain {
    private Integer id;

    private Integer groupId;

    private String applicationId;

    private String appList;


    public String getAppList() {
        return appList;
    }

    public void setAppList(String appList) {
        this.appList = appList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    @Override
    public String toString() {
        return "GroupAuthorizationMapDomain{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", applicationId='" + applicationId + '\'' +
                ", appList='" + appList + '\'' +
                '}';
    }
}