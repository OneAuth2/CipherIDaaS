package com.portal.domain;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/9/17
 *
 * @author liuying
 * @since 2019/9/17 9:39
 */
public class CsApplicationInfo implements Serializable {
    private int type;
    private String path;
    private String username;
    private String password;
    private String server;
    private String data;
    private int pos;




    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }


    public CsApplicationInfo(PortalApplyInfo portalApplyInfo) {
        this.type = portalApplyInfo.getId();
        this.path = portalApplyInfo.getApplyUrl();
        this.server = portalApplyInfo.getServer();
        this.pos = portalApplyInfo.getPos();
        this.data = portalApplyInfo.getData();
    }


}
