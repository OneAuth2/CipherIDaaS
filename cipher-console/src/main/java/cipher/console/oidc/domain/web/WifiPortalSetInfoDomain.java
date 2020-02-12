package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

public class WifiPortalSetInfoDomain implements Serializable {

    private String sharedKey;

    private int port;

    private int feePort;

    private int  protocolType;

    private Integer acDeviceId;

    private String ip;

    public String getBaseIp() {
        return baseIp;
    }

    public void setBaseIp(String baseIp) {
        this.baseIp = baseIp;
    }

    public String getBasePort() {
        return basePort;
    }

    public void setBasePort(String basePort) {
        this.basePort = basePort;
    }

    public String getPortalVer() {
        return portalVer;
    }

    public void setPortalVer(String portalVer) {
        this.portalVer = portalVer;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    private String baseIp;

    private String basePort;

    private String portalVer;

    private String authType;

    private String timeOut;

    private String deviceConfig;

    private int radiusId;

    private int acId;

    private int portalId;

    public int getPortalId() {
        return portalId;
    }

    public void setPortalId(int portalId) {
        this.portalId = portalId;
    }

    private String companyId;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public int getRadiusId() {
        return radiusId;
    }

    public void setRadiusId(int radiusId) {
        this.radiusId = radiusId;
    }

    public int getAcId() {
        return acId;
    }

    public void setAcId(int acId) {
        this.acId = acId;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public int getFeePort() {
        return feePort;
    }

    public void setFeePort(int feePort) {
        this.feePort = feePort;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public Integer getAcDeviceId() {
        return acDeviceId;
    }

    public void setAcDeviceId(Integer acDeviceId) {
        this.acDeviceId = acDeviceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(String deviceConfig) {
        this.deviceConfig = deviceConfig;
    }
}
