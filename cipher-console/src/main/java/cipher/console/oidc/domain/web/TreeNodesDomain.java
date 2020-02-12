package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseParamDomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/28.
 */
public class TreeNodesDomain extends BaseParamDomain {
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
     private  String parentGroupName;//上级部门直到根节点的部门名称
     private String path;
     private String idPath;
     private String rootNode;//根节点


     private List<TreeNodesDomain> nodes;
     private String icon;
     private static Map<String, Object> stateMap = new HashMap<String, Object>();
    private Map<String, Object> state;
     private boolean  selected=false;//搜索用户时应设置该字段

    public String getRootNode() {
        return rootNode;
    }

    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }

    static {
        stateMap.put("checked", true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * 用于角色管理--编辑--回显（根据逻辑判定是否需要默认选中，如需要则调用此方法赋值）
     */

    public void setState() {
        this.state = stateMap;
    }



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
        setIcon("fa fa-users");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        setIcon("fa fa-user");
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
                ", icon='" + icon + '\'' +
                ", state=" + state +
                ", selected=" + selected +
                '}';
    }

    public String getParentGroupName() {
        return parentGroupName;
    }

    public void setParentGroupName(String parentGroupName) {
        this.parentGroupName = parentGroupName;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }
}
