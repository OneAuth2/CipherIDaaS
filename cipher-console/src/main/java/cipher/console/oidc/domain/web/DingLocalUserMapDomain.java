package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

/**
 * 钉钉用户和本地用户的关联关系
 *
 * @Author: zt
 * @Date: 2019-05-15 11:01
 */
public class DingLocalUserMapDomain implements Serializable {

    private Integer id;

    /**
     * 钉钉的用户id
     */
    private String dingUserId;

    /**
     * 钉钉的unionId
     */
    private String dingUnionId;

    /**
     * 本地用户id
     */
    private String userId;

    private Date createTime;

    private Date modifyTime;

    /**
     * 公司id
     */
    private String companyId;

    public DingLocalUserMapDomain() {
    }

    public DingLocalUserMapDomain(String dingUserId, String userId, String dingUnionId, String companyId) {
        this.dingUserId = dingUserId;
        this.userId = userId;
        this.dingUnionId = dingUnionId;
        this.companyId = companyId;
    }

    public String getDingUnionId() {
        return dingUnionId;
    }

    public void setDingUnionId(String dingUnionId) {
        this.dingUnionId = dingUnionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
