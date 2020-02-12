package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-18
 */

import java.io.Serializable;
import java.util.Date;

/**
 * @Author qiaoxi
 * @Date 2019-10-1811:25
 **/
public class WxScanInfoDomain implements Serializable {
    private String id;

    /**
     * 公司id
     */
    private String companyId;


    /**
     * 对应的应用
     */
    private String application;

    /**
     * 对应的应用名称
     */
    private String applicationName;
    /**
     * 对应的基础配置id(cipher_ding_config_info的主键id)
     */
    private String configId;
    /**
     * 扫码用到的appId
     */
    private String scanAppId;
    /**
     * 扫码用到的appSecret
     */
    private String scanAppSecret;
    /**
     * 扫码回调地址
     */
    private String callBackUrl;

    private String authUrl;

    private Date createTime;

    private Date modifyTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getScanAppId() {
        return scanAppId;
    }

    public void setScanAppId(String scanAppId) {
        this.scanAppId = scanAppId;
    }

    public String getScanAppSecret() {
        return scanAppSecret;
    }

    public void setScanAppSecret(String scanAppSecret) {
        this.scanAppSecret = scanAppSecret;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
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


    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
