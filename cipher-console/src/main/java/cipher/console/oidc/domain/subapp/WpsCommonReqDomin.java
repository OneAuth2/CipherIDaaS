package cipher.console.oidc.domain.subapp;

import java.io.Serializable;

/**
 * wps应用的每个接口的公共参数
 * @Author: zt
 * @Date: 2018/12/11 15:25
 */
public class WpsCommonReqDomin implements Serializable {

    /**
     * 接口前缀
     */
    private String apiPre;

    /**
     * 创建session的接口前缀
     */
    private String ssoApiPre;

    /**
     * 金山云accessId
     */
    private String accessId;


    /**
     * 金山云秘钥
     */
    private String secret;

    /**
     * 金山云企业标识
     */
    private String appId;

    /**
     * 公司id
     */
    private String companyId;

    public String getApiPre() {
        return apiPre;
    }

    public void setApiPre(String apiPre) {
        this.apiPre = apiPre;
    }

    public String getSsoApiPre() {
        return ssoApiPre;
    }

    public void setSsoApiPre(String ssoApiPre) {
        this.ssoApiPre = ssoApiPre;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
