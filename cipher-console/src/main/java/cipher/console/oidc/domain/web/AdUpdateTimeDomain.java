package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2019-04-15 15:45
 */
public class AdUpdateTimeDomain implements Serializable {

    private Integer id;

    /**
     * 哪一个AD域
     */
    private String ip;

    /**
     * 上次更新到的时间戳
     */
    private String latestTime;

    public AdUpdateTimeDomain() {
    }

    public AdUpdateTimeDomain(String ip, String latestTime) {
        this.ip = ip;
        this.latestTime = latestTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }
}
