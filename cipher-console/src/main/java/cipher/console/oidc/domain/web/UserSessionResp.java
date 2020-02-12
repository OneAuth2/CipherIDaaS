package cipher.console.oidc.domain.web;

import java.io.Serializable;

public class UserSessionResp implements Serializable {
    private String cookie;
    private String ticket;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
