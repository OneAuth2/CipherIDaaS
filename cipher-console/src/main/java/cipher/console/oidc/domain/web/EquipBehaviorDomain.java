package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class EquipBehaviorDomain extends BaseDomain {
    private Integer id;
    private String uuid;
    private String vpnId;
    private String companyId;
    private String devicename;
    private int recentlySeven;
    private int recentlyThirty;
    private int status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVpnId() {
        return vpnId;
    }

    public void setVpnId(String vpnId) {
        this.vpnId = vpnId;
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

    public int getRecentlySeven() {
        return recentlySeven;
    }

    public void setRecentlySeven(int recentlySeven) {
        this.recentlySeven = recentlySeven;
    }

    public int getRecentlyThirty() {
        return recentlyThirty;
    }

    public void setRecentlyThirty(int recentlyThirty) {
        this.recentlyThirty = recentlyThirty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EquipBehaviorDomain{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", vpnId='" + vpnId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", devicename='" + devicename + '\'' +
                ", recentlySeven=" + recentlySeven +
                ", recentlyThirty=" + recentlyThirty +
                ", status=" + status +
                '}';
    }
}
