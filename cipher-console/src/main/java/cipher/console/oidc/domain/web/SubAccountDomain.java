package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/5/31 18:35
 */
public class SubAccountDomain extends BaseDomain {

    private Integer id;

    /**
     * 子账号
     */
    private String subAccount;

    private String password;


    /**
     * 应用唯一id
     */
    private String appClientId;

    private Date createTime;

    private Date modifyTime;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
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
}
