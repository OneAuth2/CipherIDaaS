package cipher.console.oidc.domain.web;


import java.io.Serializable;

public class LoginFailedConfigEntity implements Serializable {

    private int timeRange;
    private int failCount;
    private int freezingTime;
    private int status;

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(int timeRange) {
        this.timeRange = timeRange;
    }

    public int getFreezingTime() {
        return freezingTime;
    }

    public void setFreezingTime(int freezingTime) {
        this.freezingTime = freezingTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
