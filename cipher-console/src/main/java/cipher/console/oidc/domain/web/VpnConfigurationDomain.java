package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.DataGridModel;

public class VpnConfigurationDomain {
    private int id;
    private String uuid;
    private String companyId;
    private String devicename;
    private String equipAddr;
    private String describee;
    private int status;
    private int authOrigin;
    private int authType;
    private int towAuthWay;
    private String radiusServer;
    private int authPort;
    private String shareKey;
    private DataGridModel pageModel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEquipAddr() {
        return equipAddr;
    }

    public void setEquipAddr(String equipAddr) {
        this.equipAddr = equipAddr;
    }

    public String getDescribee() {
        return describee;
    }

    public void setDescribee(String describee) {
        this.describee = describee;
    }

    public int getAuthOrigin() {
        return authOrigin;
    }

    public void setAuthOrigin(int authOrigin) {
        this.authOrigin = authOrigin;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public int getTowAuthWay() {
        return towAuthWay;
    }

    public void setTowAuthWay(int towAuthWay) {
        this.towAuthWay = towAuthWay;
    }

    public String getRadiusServer() {
        return radiusServer;
    }

    public void setRadiusServer(String radiusServer) {
        this.radiusServer = radiusServer;
    }

    public int getAuthPort() {
        return authPort;
    }

    public void setAuthPort(int authPort) {
        this.authPort = authPort;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public DataGridModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(DataGridModel pageModel) {
        this.pageModel = pageModel;
    }

    @Override
    public String toString() {
        return "VpnConfigurationDomain{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", companyId='" + companyId + '\'' +
                ", devicename='" + devicename + '\'' +
                ", equipAddr='" + equipAddr + '\'' +
                ", describee='" + describee + '\'' +
                ", status=" + status +
                ", authOrigin=" + authOrigin +
                ", towAuthWay=" + towAuthWay +
                ", radiusServer='" + radiusServer + '\'' +
                ", authPort='" + authPort + '\'' +
                ", shareKey='" + shareKey + '\'' +
                '}';
    }
}
