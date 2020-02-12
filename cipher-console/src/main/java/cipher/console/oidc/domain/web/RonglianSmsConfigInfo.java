package cipher.console.oidc.domain.web;

import java.io.Serializable;


public class RonglianSmsConfigInfo implements Serializable{

    private String templateId;
    private String templateIdOne;
    private String appId;
    private String authToken;
    private String accountSid;

    public String getTemplateIdOne() {
        return templateIdOne;
    }

    public void setTemplateIdOne(String templateIdOne) {
        this.templateIdOne = templateIdOne;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }
}
