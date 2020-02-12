package cipher.console.oidc.domain.web;

public class AutoSyncInfo {
    private int isAutoSync;
    private String autoSyncDate;
    private int interval;

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
        return "AutoSyncInfo{" +
                "isAutoSync=" + isAutoSync +
                ", autoSyncDate='" + autoSyncDate + '\'' +
                ", interval=" + interval +
                '}';
    }
}
