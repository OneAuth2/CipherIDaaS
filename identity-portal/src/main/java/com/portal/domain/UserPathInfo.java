package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/9/23
 *
 * @author liuying
 * @since 2019/9/23 9:56
 */
public class UserPathInfo implements Serializable {

    private int id;
    private String userId;
    private String appId;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserPathInfo() {
    }

    public UserPathInfo(String userId, String appId, String path) {
        this.userId = userId;
        this.appId = appId;
        this.path = path;
    }
}
