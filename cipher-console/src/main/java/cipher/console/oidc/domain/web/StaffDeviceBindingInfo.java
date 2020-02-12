package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.io.Serializable;

public class StaffDeviceBindingInfo extends BaseDomain {

    private  Integer id;
    private String name;
    private String userName;
    private String mac;
    private String deviceInfo;
    private String bindTime;

    private String queryName;

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }

    @Override
    public String toString() {
        return "StaffDeviceBindingInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", mac='" + mac + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", bindTime='" + bindTime + '\'' +
                '}';
    }
}
