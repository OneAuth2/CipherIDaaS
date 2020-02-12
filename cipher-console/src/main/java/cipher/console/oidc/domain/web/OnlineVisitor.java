package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.domain.BaseParamDomain;

import java.util.Date;

public class OnlineVisitor  extends BaseDomain {

    private int id;

    private String username;

    private int type;

    private String ip;

    private String loginTime;

    private Date createTime;

    private String mac;

    private String companyId;

    private String visitorType;

    private String name;

    private String typeName;

    private String description;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }


    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public OnlineVisitor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OnlineVisitor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", type=" + type +
                ", ip='" + ip + '\'' +
                ", loginTime=" + loginTime +
                ", createTime=" + createTime +
                '}';
    }
}
