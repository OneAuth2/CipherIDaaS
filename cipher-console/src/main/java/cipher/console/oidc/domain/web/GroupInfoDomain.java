package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

public class GroupInfoDomain  extends BaseDomain {
    private String path;//上级groupid
    private String  parentPathName;//所有父节点的名字拼接
    private Integer groupId;

    /**
     * 从ad域导过来的组的名称
     */
    private String ou;

    /**
     * 组名
     */
    private String groupName;

    /**
     * 上级组织的id,根组为空
     */
    private Integer parentGroupId;

    /**
     * 组描述
     */
    private String groupDescription;

    /**
     * 创建人的账号
     */
    private String accountNumber;

    private Date createTime;

    private Date modifyTime;

    private String text;
    private String href;
    private List<GroupInfoDomain> nodes;

    private List<TreeNodesDomain> userList;

    private String queryName;

    private String source;

    private Integer userNum;

    private int applicationId;

    /**
     * 从钉钉导入的group的钉钉id
     */
    private Long dingId;

    /*父级部门*/
    private GroupInfoDomain parentGroup;

    /*当前子部门*/
    private GroupInfoDomain childGroup;


    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public List<TreeNodesDomain> getUserList() {
        return userList;
    }

    public void setUserList(List<TreeNodesDomain> userList) {
        this.userList = userList;
    }

    public List<GroupInfoDomain> getNodes() {
        return nodes;
    }

    public void setNodes(List<GroupInfoDomain> nodes) {
        this.nodes = nodes;
    }

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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription == null ? null : groupDescription.trim();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    @Override
    public String toString() {
        return "GroupInfoDomain{" +
                "path='" + path + '\'' +
                ", parentPathName='" + parentPathName + '\'' +
                ", groupId=" + groupId +
                ", ou='" + ou + '\'' +
                ", groupName='" + groupName + '\'' +
                ", parentGroupId=" + parentGroupId +
                ", groupDescription='" + groupDescription + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", text='" + text + '\'' +
                ", href='" + href + '\'' +
                ", nodes=" + nodes +
                ", userList=" + userList +
                ", queryName='" + queryName + '\'' +
                ", source='" + source + '\'' +
                ", userNum=" + userNum +
                ", applicationId=" + applicationId +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParentPathName() {
        return parentPathName;
    }

    public void setParentPathName(String parentPathName) {
        this.parentPathName = parentPathName;
    }


    public Long getDingId() {
        return dingId;
    }

    public void setDingId(Long dingId) {
        this.dingId = dingId;
    }

    public GroupInfoDomain getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(GroupInfoDomain parentGroup) {
        this.parentGroup = parentGroup;
    }

    public GroupInfoDomain getChildGroup() {
        return childGroup;
    }

    public void setChildGroup(GroupInfoDomain childGroup) {
        this.childGroup = childGroup;
    }
}
