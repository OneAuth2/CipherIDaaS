package cipher.console.oidc.domain.web;

public class AdAutoSyncInfo {
    private Integer syncWay;
    private Integer orgStructure;
    private Integer ouCreateDept;
    private Integer isNoSyncOrg;
    private String syncTarget;
    private int isAutoSync;
    private String autoSyncDate;
    private int interval;

    public AdAutoSyncInfo(){
        this.orgStructure=1;
        this.ouCreateDept=1;
        this.isNoSyncOrg=1;
    }


    public Integer getSyncWay() {
        return syncWay;
    }

    public void setSyncWay(Integer syncWay) {
        this.syncWay = syncWay;
    }

    public Integer getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(Integer orgStructure) {
        this.orgStructure = orgStructure;
    }

    public Integer getOuCreateDept() {
        return ouCreateDept;
    }

    public void setOuCreateDept(Integer ouCreateDept) {
        this.ouCreateDept = ouCreateDept;
    }

    public Integer getIsNoSyncOrg() {
        return isNoSyncOrg;
    }

    public void setIsNoSyncOrg(Integer isNoSyncOrg) {
        this.isNoSyncOrg = isNoSyncOrg;
    }

    public String getSyncTarget() {
        return syncTarget;
    }

    public void setSyncTarget(String syncTarget) {
        this.syncTarget = syncTarget;
    }

    public int getIsAutoSync() {
        return isAutoSync;
    }

    public void setIsAutoSync(int isAutoSync) {
        this.isAutoSync = isAutoSync;
    }

    public String getAutoSyncDate() {
        return autoSyncDate;
    }

    public void setAutoSyncDate(String autoSyncDate) {
        this.autoSyncDate = autoSyncDate;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "AdAutoSyncInfo{" +
                "syncWay=" + syncWay +
                ", orgStructure=" + orgStructure +
                ", ouCreateDept=" + ouCreateDept +
                ", isNoSyncOrg=" + isNoSyncOrg +
                ", syncTarget='" + syncTarget + '\'' +
                ", isAutoSync=" + isAutoSync +
                ", autoSyncDate='" + autoSyncDate + '\'' +
                ", interval=" + interval +
                '}';
    }
}
