package cipher.console.oidc.domain;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeGroupAndUserDomain implements Serializable {

    private String text;

    private String href;

    private GroupInfoDomain groupInfoDomain;

    private List<UserInfoDomain> userInfoDomainList;

    private List<TreeGroupAndUserDomain> nodes;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public GroupInfoDomain getGroupInfoDomain() {
        return groupInfoDomain;
    }

    public void setGroupInfoDomain(GroupInfoDomain groupInfoDomain) {
        this.groupInfoDomain = groupInfoDomain;
    }

    public List<UserInfoDomain> getUserInfoDomainList() {
        return userInfoDomainList;
    }

    public void setUserInfoDomainList(List<UserInfoDomain> userInfoDomainList) {
        this.userInfoDomainList = userInfoDomainList;
    }

    public List<TreeGroupAndUserDomain> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeGroupAndUserDomain> nodes) {
        if (nodes == null) {
            nodes = new ArrayList<>();
        }
        this.nodes = nodes;
    }


}
