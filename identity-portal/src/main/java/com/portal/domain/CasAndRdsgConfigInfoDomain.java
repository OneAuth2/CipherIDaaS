package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: 安歌
 * @Date: 2019/8/21 11:14
 */
public class CasAndRdsgConfigInfoDomain implements Serializable {

    /** cas服务器跳转认证页面的地址 **/
    private String casServerAuthUrl;

    /** Rdsg跳转认证页面的地址 **/
    private String rdsgServerAuthUrl;

    /** Rdsg接收ticket的地址 **/
    private String rdsgServerUrl;

    /** 账号绑定的地址 **/
    private String bindingAccountUrl;

    public String getCasServerAuthUrl() {
        return casServerAuthUrl;
    }

    public void setCasServerAuthUrl(String casServerAuthUrl) {
        this.casServerAuthUrl = casServerAuthUrl;
    }

    public String getRdsgServerAuthUrl() {
        return rdsgServerAuthUrl;
    }

    public void setRdsgServerAuthUrl(String rdsgServerAuthUrl) {
        this.rdsgServerAuthUrl = rdsgServerAuthUrl;
    }

    public String getRdsgServerUrl() {
        return rdsgServerUrl;
    }

    public void setRdsgServerUrl(String rdsgServerUrl) {
        this.rdsgServerUrl = rdsgServerUrl;
    }

    public String getBindingAccountUrl() {
        return bindingAccountUrl;
    }

    public void setBindingAccountUrl(String bindingAccountUrl) {
        this.bindingAccountUrl = bindingAccountUrl;
    }

    @Override
    public String toString() {
        return "CasAndRdsgConfigInfoDomain{" +
                "casServerAuthUrl='" + casServerAuthUrl + '\'' +
                ", rdsgServerAuthUrl='" + rdsgServerAuthUrl + '\'' +
                ", rdsgServerUrl='" + rdsgServerUrl + '\'' +
                ", bindingAccountUrl='" + bindingAccountUrl + '\'' +
                '}';
    }
}
