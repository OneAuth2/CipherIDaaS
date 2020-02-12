package com.portal.domain;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95744 on 2018/9/28.
 */
public class TreeNodesDomain implements Serializable {

     private String text;
     private String href;
    public String getGroupId() {
        return groupId;
    }

     public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

     private String groupId;
     private String parentGroupId;
     private String groupName;
     private String accountNumber;
     private String userName;

     private List<TreeNodesDomain> nodes;

    public TreeNodesDomain() {
        nodes = new ArrayList<TreeNodesDomain>();
    }
    public TreeNodesDomain(GroupInfoDomain groupInfoDomain) {
        this.groupId = groupInfoDomain.getGroupId() + "";
        this.text = groupInfoDomain.getGroupName();
        this.groupName = groupInfoDomain.getGroupName();
        this.nodes = new ArrayList<TreeNodesDomain>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(String parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    public void setText() {
        this.text = userName;
    }
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<TreeNodesDomain> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeNodesDomain> nodes) {
        this.nodes = nodes;
    }


    @Override
    public String toString() {
        return "\nTreeNodesDomain{" +
                "text='" + text + '\'' +
                ", href='" + href + '\'' +
                ", groupId='" + groupId + '\'' +
                ", parentGroupId='" + parentGroupId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
