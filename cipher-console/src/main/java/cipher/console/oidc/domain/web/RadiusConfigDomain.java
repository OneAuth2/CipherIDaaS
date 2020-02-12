package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: zt
 * @Date: 2018/7/26 11:20
 */
public class RadiusConfigDomain implements Serializable {

    private Integer id;

    /**
     * 认证类型
     *
     * 1:代表携带用户名+pwd+totp;
     * 2:用户名+pwd;
     * 3:用户名+totp
     */
    private int authType;

    /**
     * 提供radius认证服务的协议
     */
    private String protocol;

    /**
     * 提供radius服务的服务器地址
     */
    private String ip;

    /**
     * 提供radius服务的端口
     */
    private int port;

    /**
     * radius服务和客户端用户加密传输数据的密钥
     */
    private String sharedKey;

    private Date createTime;

    private int feePort;
    private int  protocolType;

    public int getFeePort() {
        return feePort;
    }

    public void setFeePort(int feePort) {
        this.feePort = feePort;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAuthType() {
        return authType;
    }

    public void setAuthType(int authType) {
        this.authType = authType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getSharedKey() {
        return sharedKey;
    }

    public void setSharedKey(String sharedKey) {
        this.sharedKey = sharedKey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RadiusConfigDomain{" +
                "id=" + id +
                ", authType=" + authType +
                ", protocol='" + protocol + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", sharedKey='" + sharedKey + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
