package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/15.
 */
public class IdentityGroupMapDomain implements Serializable{

    private int identityId;
    private int groupId;
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getIdentityId() {
        return identityId;
    }

    public void setIdentityId(int identityId) {
        this.identityId = identityId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
