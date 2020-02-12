package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/8/20
 *
 * @author liuying
 * @since 2019/8/20 14:47
 */
public class SystemConfigInfo implements Serializable {

    private int id;
    private String ip;
    private String deviceName;
    private String dsgServerUrl;
    private String consoleUrl;
    private String consoleRedirectUrl;
    private int expireTime;
    private String imageHost;
    private String portalServerUrl;


    public String getPortalServerUrl() {
        return portalServerUrl;
    }

    public void setPortalServerUrl(String portalServerUrl) {
        this.portalServerUrl = portalServerUrl;
    }

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDsgServerUrl() {
        return dsgServerUrl;
    }

    public void setDsgServerUrl(String dsgServerUrl) {
        this.dsgServerUrl = dsgServerUrl;
    }

    public String getConsoleUrl() {
        return consoleUrl;
    }

    public void setConsoleUrl(String consoleUrl) {
        this.consoleUrl = consoleUrl;
    }

    public String getConsoleRedirectUrl() {
        return consoleRedirectUrl;
    }

    public void setConsoleRedirectUrl(String consoleRedirectUrl) {
        this.consoleRedirectUrl = consoleRedirectUrl;
    }
}
