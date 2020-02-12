package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zt
 * @Date: 2019-06-10 16:16
 */
public class AccountAdBindingDomain implements Serializable {

    private Integer id;

    /**
     * 本地用户的Id
     */
    private String userId;

    /**
     * 用于AD用户去AD认证的标识
     */
    private String upn;

    /**
     * 公司AD
     */
    private String companyId;

    /**
     * 该用户所在AD的ip
     */
    private String ip;

    private Date createTime;

    public AccountAdBindingDomain() {
    }

    public AccountAdBindingDomain(String userId, String upn, String companyId, String ip) {
        this.userId = userId;
        this.upn = upn;
        this.companyId = companyId;
        this.ip = ip;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpn() {
        return upn;
    }

    public void setUpn(String upn) {
        this.upn = upn;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
