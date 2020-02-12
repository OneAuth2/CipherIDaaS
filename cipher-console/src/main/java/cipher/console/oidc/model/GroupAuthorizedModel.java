package cipher.console.oidc.model;

import cipher.console.oidc.common.Constants;

/**
 * @author shizhao
 * @since 2018/5/30
 * */
public class GroupAuthorizedModel {
    /*组名*/
    int groupId;

    /*应用Id*/
    int applicationId;

    /*已授权的Id*/
    int authorizedId;

    /*应用名称*/
    String applicationName;

    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(int authorizedId) {
        this.authorizedId = authorizedId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean isExist(int applicationId){
        if (applicationId == this.authorizedId){
            return true;
        }
        return false;
    }

    public boolean toUpdate(int status){
        if (status == Constants.GROUP_UNAUTHORIZED){
            return true;
        }else if (status == Constants.GROUP_AUTHORIZED){
            return false;
        }else {
            return false;
        }
    }


}
