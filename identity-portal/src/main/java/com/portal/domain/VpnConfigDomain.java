package com.portal.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * vpn设备配置
 *
 * @Author: zt
 * @Date: 2019-08-08 15:40
 */
public class VpnConfigDomain implements Serializable {


    //设备状态启用
    public static final Integer OPEN = 0;

    //设备状态停用
    public static final Integer CLOSE = 1;


    public static final Integer AD_AUTH=1;

    public static final Integer LOCAL_AUTH=0;


    public static final Integer USER_PWD_TOTP = 1;

    public static final Integer USER_PWD = 2;

    public static final Integer USER_TOTP = 3;

    private Integer id;

    private String uuid;

    private String companyId;

    /**
     * vpn设备名称
     */
    private String devicename;

    /**
     * vpn设备ip地址
     */
    private String equipAddr;

    /**
     * vpn设备描述
     */
    private String describee;

    /**
     * 状态
     * 0-启用，1-停用
     */
    private Integer status;

    /**
     * 认证源
     * 0-本地认证
     * 1-AD认证
     * 2-JTL
     */
    private Integer authOrigin;


    /**
     * 是否开启二次push认证
     * 0-开启
     * 1-关闭
     */
    private Integer towAuthWay;

    /**
     * radius服务器ip地址
     */
    private String radiusServer;

    /**
     * radius服务器认证地址
     */
    private Integer authPort;

    /**
     * Radius秘钥
     */
    private String shareKey;

    /**
     * 1.用户名+密码+totp
     * 2.用户名+密码
     * 3.用户名+totp
     */
    private Integer authType;

    private Date createTime;

    private Date modifyTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public String getEquipAddr() {
        return equipAddr;
    }

    public void setEquipAddr(String equipAddr) {
        this.equipAddr = equipAddr;
    }

    public String getDescribee() {
        return describee;
    }

    public void setDescribee(String describee) {
        this.describee = describee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuthOrigin() {
        return authOrigin;
    }

    public void setAuthOrigin(Integer authOrigin) {
        this.authOrigin = authOrigin;
    }

    public Integer getTowAuthWay() {
        return towAuthWay;
    }

    public void setTowAuthWay(Integer towAuthWay) {
        this.towAuthWay = towAuthWay;
    }

    public String getRadiusServer() {
        return radiusServer;
    }

    public void setRadiusServer(String radiusServer) {
        this.radiusServer = radiusServer;
    }

    public Integer getAuthPort() {
        return authPort;
    }

    public void setAuthPort(Integer authPort) {
        this.authPort = authPort;
    }

    public String getShareKey() {
        return shareKey;
    }

    public void setShareKey(String shareKey) {
        this.shareKey = shareKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getAuthType() {
        return authType;
    }

    public void setAuthType(Integer authType) {
        this.authType = authType;
    }

}
