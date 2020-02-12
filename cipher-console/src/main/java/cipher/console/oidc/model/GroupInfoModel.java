package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.util.DateUtil;

import java.util.Date;

/**
 * 表中需要的组信息
 * @author shizhao
 * @since 2018/5/30
 * */
public class GroupInfoModel extends BaseDomain {
    /**
     * 组号
     * */
    int groupId;

    /**
     * 组名
     * */
    String groupName;

    /**
     * 组描述
     * */
    String groupDescription;

    /**
     * 组管理员
     * */
    String accountNumber;

    String companyId;

    /**
     * 组创建时间
     * */
    String createTime;

    /**
     * 组修改时间
     * */
    String modifyTime;

    private String path;

    private  Integer parentGroupId;

    public GroupInfoModel() {
    }

    public GroupInfoModel(String groupName,int groupId) {
        this.groupName = groupName;
        this.groupId = groupId;
        setModifyTime();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setModifyTime() {
        this.modifyTime = DateUtil.getSystemDateString();
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
