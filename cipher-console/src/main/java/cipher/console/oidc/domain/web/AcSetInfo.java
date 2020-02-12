package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;

public class AcSetInfo extends BaseDomain {
    private Integer id;

    private Integer acDeviceId;
    private String acDeviceName;

    private String descripetion;

    private String baseIp;

    private String basePort;

    private String portalVer;

    private String authType;

    private String timeOut;

    private String sharedSecret;

    private Date createTime;

    private Date modifyTime;

    private String url;

    private String deviceConfig;


    public String getStaffAuthType() {
        return staffAuthType;
    }

    public void setStaffAuthType(String staffAuthType) {
        this.staffAuthType = staffAuthType;
    }

    private String acIp;

    private String staffAuthType;

    public String getAcIp() {
        return acIp;
    }

    public void setAcIp(String acIp) {
        this.acIp = acIp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAcDeviceName() {
        return acDeviceName;
    }

    public void setAcDeviceName(String acDeviceName) {
        this.acDeviceName = acDeviceName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAcDeviceId() {
        return acDeviceId;
    }

    public void setAcDeviceId(Integer acDeviceId) {
        this.acDeviceId = acDeviceId;
    }

    public String getDescripetion() {
        return descripetion;
    }

    public void setDescripetion(String descripetion) {
        this.descripetion = descripetion == null ? null : descripetion.trim();
    }

    public String getBaseIp() {
        return baseIp;
    }

    public void setBaseIp(String baseIp) {
        this.baseIp = baseIp == null ? null : baseIp.trim();
    }

    public String getBasePort() {
        return basePort;
    }

    public void setBasePort(String basePort) {
        this.basePort = basePort == null ? null : basePort.trim();
    }

    public String getPortalVer() {
        return portalVer;
    }

    public void setPortalVer(String portalVer) {
        this.portalVer = portalVer == null ? null : portalVer.trim();
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType == null ? null : authType.trim();
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut == null ? null : timeOut.trim();
    }

    public String getSharedSecret() {
        return sharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret == null ? null : sharedSecret.trim();
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

    public String getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(String deviceConfig) {
        this.deviceConfig = deviceConfig;
    }
}