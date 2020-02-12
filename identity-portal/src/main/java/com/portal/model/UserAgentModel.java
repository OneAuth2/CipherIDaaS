package com.portal.model;

import com.portal.domain.userAgent.UserAgent;

import java.io.Serializable;

public class UserAgentModel implements Serializable {

    private int id;

    private String accountNumber;

    private String osName;

    private String manufacturer;

    private String browserName;

    private String browserVersion;

    private String browserEngine;

    private String ip;

    private String browserType;

    private String createTime;

    public UserAgentModel(String userAgent, String ip, String accountNumber) {
        this.accountNumber = accountNumber;
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        if (agent != null){
            setOsName(agent.getOperatingSystem().getName());
            setManufacturer(agent.getOperatingSystem().getManufacturer().getName());
            setBrowserName(agent.getBrowser().getName());
            setBrowserVersion(agent.getBrowserVersion().getVersion());
            setBrowserEngine(agent.getBrowser().getRenderingEngine().getName());
            setIp(ip);
            setBrowserType(agent.getBrowser().getBrowserType().getName());
        }
    }

    public UserAgentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getBrowserEngine() {
        return browserEngine;
    }

    public void setBrowserEngine(String browserEngine) {
        this.browserEngine = browserEngine;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserAgentModel{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", osName='" + osName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", browserEngine='" + browserEngine + '\'' +
                ", ip='" + ip + '\'' +
                ", browserType='" + browserType + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
