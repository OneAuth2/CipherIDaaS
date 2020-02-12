package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 钉钉部门信息
 *
 * @Author: zt
 * @Date: 2019-05-11 17:15
 */
public class DingGroupDomain extends BaseDomain implements Serializable {

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 钉钉部门id
     */
    private Long id;

    /**
     * 是否同步创建一个关联此部门的企业群，true表示是，false表示不是
     */
    private boolean createDeptGroup;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门id，根部门为1
     */
    private Long parentid;

    /**
     * 当群已经创建后，是否有新人加入部门会自动加入该群, true表示是，false表示不是
     */
    private boolean autoAddUser;

    /**
     * 全路径
     */
    private String path = "";

    private Date createTime;

    private Date modifyTime;

    /**
     * 状态，新增/编辑
     */
    private String status;

    /**
     * 本地的父级部门id
     */
    private Integer parentGroupId;



    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public boolean isAutoAddUser() {
        return autoAddUser;
    }

    public void setAutoAddUser(boolean autoAddUser) {
        this.autoAddUser = autoAddUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void constructPath(String tmpPath) {
        if (StringUtils.isEmpty(tmpPath)) {
            return;
        }
        if (StringUtils.isEmpty(this.path)) {
            this.path = tmpPath;
        } else {
            this.path = tmpPath + "/" + this.path;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getParentGroupId() {
        return parentGroupId;
    }

    public void setParentGroupId(Integer parentGroupId) {
        this.parentGroupId = parentGroupId;
    }
}
