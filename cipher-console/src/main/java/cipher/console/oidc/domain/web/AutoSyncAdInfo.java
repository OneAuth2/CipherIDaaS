package cipher.console.oidc.domain.web;

public class AutoSyncAdInfo {
    private int syncMode;
    private int Structure;
    private int syncTarget;
    private int isAutoSync;
    private String autoSyncDate;
    private int interval;

    public int getSyncMode() {
        return syncMode;
    }

    public void setSyncMode(int syncMode) {
        this.syncMode = syncMode;
    }

    public int getStructure() {
        return Structure;
    }

    public void setStructure(int structure) {
        Structure = structure;
    }

    public int getSyncTarget() {
        return syncTarget;
    }

    public void setSyncTarget(int syncTarget) {
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
        return "AutoSyncAdInfo{" +
                "syncMode=" + syncMode +
                ", Structure=" + Structure +
                ", syncTarget=" + syncTarget +
                ", isAutoSync=" + isAutoSync +
                ", autoSyncDate='" + autoSyncDate + '\'' +
                ", interval=" + interval +
                '}';
    }
}
