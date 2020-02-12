package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

public class UserSessionDomain implements Serializable {
    private String uuid;
    private Date expireTime;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
