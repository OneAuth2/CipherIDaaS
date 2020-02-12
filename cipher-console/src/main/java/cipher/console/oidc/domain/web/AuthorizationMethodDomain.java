package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class AuthorizationMethodDomain extends BaseDomain {
    /**
     * 认证方式编号
     * */
    int id;

    /**
     *
     * 认证方式
     * */
    String method;

    /**
     *
     * 认证方式状态
     *
     * 可用 - 禁用
     * */
    String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
