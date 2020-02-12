package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-12
 */

import java.io.Serializable;
import java.util.Date;

/**
 * @Author qiaoxi
 * @Date 2019-10-1217:35
 **/
public class WxLocalUserMapDomain implements Serializable {
    private Integer id;


    private String wxUserId;

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

    public WxLocalUserMapDomain(String wxUserId, String userId, String companyId) {
        this.wxUserId = wxUserId;
        this.userId = userId;
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxUserId() {
        return wxUserId;
    }

    public void setWxUserId(String wxUserId) {
        this.wxUserId = wxUserId;
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
