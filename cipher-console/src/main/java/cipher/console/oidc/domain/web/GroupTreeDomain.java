package cipher.console.oidc.domain.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门的树状结构，只包含部门
 *
 * @Author: zt
 * @Date: 2019-05-11 15:42
 */
public class GroupTreeDomain {

    /**
     * 部门名称
     */
    private String groupName;

    /**
     * 部门Id
     */
    private Integer groupId;

    /**
     * 父级部门Id
     */
    private Integer parentGroupId;

    /**
     * 递归
     */
    private List<GroupTreeDomain> nodes;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public List<GroupTreeDomain> getNodes() {

        if (nodes == null) {
            nodes = new ArrayList<>();
        }

        return nodes;
    }

    public void setNodes(List<GroupTreeDomain> nodes) {
        this.nodes = nodes;
    }
}
