package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.BaseParamDomain;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/1.
 */
public class LoginFailInfo extends BaseParamDomain {

    private int id;
    private int timeRang;
    private int failCount;
    private int freezingTime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeRang() {
        return timeRang;
    }

    public void setTimeRang(int timeRang) {
        this.timeRang = timeRang;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
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
