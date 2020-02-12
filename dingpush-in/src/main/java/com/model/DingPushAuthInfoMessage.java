package com.model;


import com.jms.base.BaseMessage;

/**
 * 钉钉push认证发送到消息队列的实体
 *
 * @Author: zt
 * @Date: 2019-08-09 09:47
 */
public class DingPushAuthInfoMessage extends BaseMessage {


    /**
     * 主账号的uuid
     */
    private String uuid;

    /**
     * VPN设备的ip
     */
    private String vpnIp;

    /**
     * 当前的时间戳
     */
    private Long currentTimeStamp;

    private String deviceName;


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DingPushAuthInfoMessage() {
    }


    public DingPushAuthInfoMessage(String uuid, String vpnIp, Long currentTimeStamp) {
        this.uuid = uuid;
        this.vpnIp = vpnIp;
        this.currentTimeStamp = currentTimeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVpnIp() {
        return vpnIp;
    }

    public void setVpnIp(String vpnIp) {
        this.vpnIp = vpnIp;
    }

    public Long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(Long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }
}
