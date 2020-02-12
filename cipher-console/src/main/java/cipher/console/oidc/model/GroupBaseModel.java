package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @author shizhao
 * @since 2018/5/30
 * 组基本信息
 * */
public class GroupBaseModel extends BaseDomain {

    /*组编号*/
    private int groupId;
    /*组名*/
    private String groupName;
    /*组成员总数*/
    private int userCount;
    /*组授权应用总数*/
    private int appCount;

    private String parentGroupName;

    private String sord;
    private String sidx;

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getAppCount() {
        return appCount;
    }

    public void setAppCount(int appCount) {
        this.appCount = appCount;
    }

    public String getParentGroupName() {
        return parentGroupName;
    }

    public void setParentGroupName(String parentGroupName) {
        this.parentGroupName = parentGroupName;
    }
}
