package cipher.console.oidc.domain.web;

import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: TK
 * 用户授权树节点
 * @Date: 2019/2/23 14:34
 */
public class AppUserTreeNode {

    private String text;//节点展示内容，如果为用户则展示userName 如果为部门则展示groupName;
    private String groupId;//部门Id
    private String parentGroupId;//父级部门iD父级部门为空则为根节点
    private String groupName;//部门名称
    private String accountNumber;//如果部门为空则该节点为用户
    private String userName;//用户名称
    private static Map<String, Object> stateMap=new HashedMap();
    private Map<String,Object> state;
    static {
        stateMap.put("select",false);
    }
    List<AppUserTreeNode> nodes;
    public AppUserTreeNode(){
        nodes=new ArrayList<>();
    }
    public AppUserTreeNode(GroupInfoDomain groupInfoDomain){
            this.groupName=groupInfoDomain.getGroupName();
            this.groupId=groupInfoDomain.getGroupId()+"";
            this.parentGroupId=groupInfoDomain.getParentGroupId()+"";
            nodes=new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public void setText() {
        this.text = this.userName;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static Map<String, Object> getStateMap() {
        return stateMap;
    }

    public static void setStateMap(Map<String, Object> stateMap) {
        AppUserTreeNode.stateMap = stateMap;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    public List<AppUserTreeNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<AppUserTreeNode> nodes) {
        this.nodes = nodes;
    }
}
