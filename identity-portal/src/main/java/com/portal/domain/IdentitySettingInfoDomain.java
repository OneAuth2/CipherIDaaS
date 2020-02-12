package com.portal.domain;


import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/4/28 11:11
 */
public class IdentitySettingInfoDomain  implements Serializable {
    private Integer id;
    private String companyUuid;
    private Integer type;
    private String authMode;
    private String bindingFlow;
    private String registFlow;
    private String forgetFlow;

    public IdentitySettingInfoDomain(Integer id, String companyUuid, Integer type, String authMode, String bindingFlow, String registFlow, String forgetFlow) {
        this.id = id;
        this.companyUuid = companyUuid;
        this.type = type;
        this.authMode = authMode;
        this.bindingFlow = bindingFlow;
        this.registFlow = registFlow;
        this.forgetFlow = forgetFlow;
    }

    public IdentitySettingInfoDomain() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAuthMode() {
        return authMode;
    }

    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    public String getBindingFlow() {
        return bindingFlow;
    }

    public void setBindingFlow(String bindingFlow) {
        this.bindingFlow = bindingFlow;
    }

    public String getRegistFlow() {
        return registFlow;
    }

    public void setRegistFlow(String registFlow) {
        this.registFlow = registFlow;
    }

    public String getForgetFlow() {
        return forgetFlow;
    }

    public void setForgetFlow(String forgetFlow) {
        this.forgetFlow = forgetFlow;
    }

    @Override
    public String toString() {
        return "IdentitySettingInfoDomain{" +
                "id=" + id +
                ", companyUuid='" + companyUuid + '\'' +
                ", type=" + type +
                ", authMode='" + authMode + '\'' +
                ", bindingFlow='" + bindingFlow + '\'' +
                ", registFlow='" + registFlow + '\'' +
                ", forgetFlow='" + forgetFlow + '\'' +
                '}';
    }
}
