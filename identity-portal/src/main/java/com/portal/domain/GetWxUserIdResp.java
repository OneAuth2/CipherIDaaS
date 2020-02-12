package com.portal.domain;

import java.io.Serializable;

/**
 * TODO:
 * create by liuying at 2019/10/18
 *
 * @author liuying
 * @since 2019/10/18 11:25
 */
public class GetWxUserIdResp implements Serializable {

    private String UserId;
    private String DeviceId;
    private int errcode;
    private String errmsg;


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
