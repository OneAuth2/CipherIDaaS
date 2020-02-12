package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: zt
 * @Date: 2018/5/31 11:47
 */
public class UserAuthorizationMapDomain extends BaseDomain {

    private Integer id;

    private String accountNumber;

    private String accountAuthorizedMethod;

    /**
     * 每个用户的认证方式的拼接
     */
    private String authTypeCon;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountAuthorizedMethod() {
        return accountAuthorizedMethod;
    }

    public void setAccountAuthorizedMethod(String accountAuthorizedMethod) {
        this.accountAuthorizedMethod = accountAuthorizedMethod;
    }

    public String getAuthTypeCon() {
        return authTypeCon;
    }

    public void setAuthTypeCon(String authTypeCon) {
        this.authTypeCon = authTypeCon;
    }
}
