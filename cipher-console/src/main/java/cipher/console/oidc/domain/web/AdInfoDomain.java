package cipher.console.oidc.domain.web;

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/6/7 17:23
 * modify by 田扛
 * modify time 2019年3月11日15:06:32
 */
public class AdInfoDomain extends BaseDomain {


    private Integer id;

    /**
     * Ad域还是openLdap
     */
    private String type;

    private String queryName;

    /**
     * 导入时指定的节点信息
     */
    private String userDn;

    /**
     * 连接AD域时的ip或者域名
     */
    private String ip;

    /**
     * 连接AD域时的端口,默认389
     */
    private int port;


    private String userName;

    private String password;

    /**
     * 连接AD域时的节点信息
     */
    private String ouDc;

    /**
     * 连接Ad域时的对象信息
     */
    private String objectClass;

    /**
     * 搜索域 OBJECT_SCOPE：0；ONELEVEL_SCOPE：1；SUBTREE_SCOPE：2
     */
    private String searchScope;


    private Date createTime;

    private Date modifyTime;

    /**
     * 1-同步OU,2-不同步OU
     */
    private Integer syncOu;


    /**
     * AD域中数据和本地数据库之间的映射关系(json数据)。
     */
    private String kvConfig;

    /**
     * 指定的ou列表(json数据)
     */
    private String ous;


    private List<AdMap2LocalConfigModel> adMap2LocalDomainList;

    /*
    * agent地址
    * */
    private String address;

    /*
    * 连接方式
    * */
    private int connectionType;

    /*
    * 连接agent的秘钥
    * */

    private String secret;

    private String autoConfig;

    /**
     * ad自动同步配置
     */
    private AdAutoSyncInfo adAutoSyncInfo;

    public AdAutoSyncInfo getAdAutoSyncInfo() {
        return adAutoSyncInfo;
    }

    public void setAdAutoSyncInfo(AdAutoSyncInfo adAutoSyncInfo) {
        this.adAutoSyncInfo = adAutoSyncInfo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(int connectionType) {
        this.connectionType = connectionType;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserDn() {
        return userDn;
    }

    public void setUserDn(String userDn) {
        this.userDn = userDn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOuDc() {
        return ouDc;
    }

    public void setOuDc(String ouDc) {
        this.ouDc = ouDc;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public String getSearchScope() {
        return searchScope;
    }

    public void setSearchScope(String searchScope) {
        this.searchScope = searchScope;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSyncOu() {
        return syncOu;
    }

    public void setSyncOu(Integer syncOu) {
        this.syncOu = syncOu;
    }

    public String getKvConfig() {
        return kvConfig;
    }

    public void setKvConfig(String kvConfig) {
        this.kvConfig = kvConfig;
    }

    public List<AdMap2LocalConfigModel> getAdMap2LocalDomainList() {
        return adMap2LocalDomainList;
    }

    public void setAdMap2LocalDomainList(List<AdMap2LocalConfigModel> adMap2LocalDomainList) {
        this.adMap2LocalDomainList = adMap2LocalDomainList;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    public String getOus() {
        return ous;
    }

    public void setOus(String ous) {
        this.ous = ous;
    }

    public String getAutoConfig() {
        return autoConfig;
    }

    public void setAutoConfig(String autoConfig) {
        this.autoConfig = autoConfig;
    }

    @Override
    public String toString() {
        return "AdInfoDomain{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", queryName='" + queryName + '\'' +
                ", userDn='" + userDn + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", ouDc='" + ouDc + '\'' +
                ", objectClass='" + objectClass + '\'' +
                ", searchScope='" + searchScope + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", syncOu=" + syncOu +
                ", kvConfig='" + kvConfig + '\'' +
                ", ous='" + ous + '\'' +
                ", adMap2LocalDomainList=" + adMap2LocalDomainList +
                ", address='" + address + '\'' +
                ", connectionType=" + connectionType +
                ", secret='" + secret + '\'' +
                ", autoConfig='" + autoConfig + '\'' +
                ", adAutoSyncInfo=" + adAutoSyncInfo +
                '}';
    }
}
