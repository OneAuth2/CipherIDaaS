package com.portal.domain;


import java.util.Date;

/**
 * AD配置信息的实体类
 * create by shizhao at 2019/5/7
 *
 * @author shizhao
 * @since  2019/5/7
 */
public class AdInfoDomain {


    private Integer id;


    /**
     * Ad域还是openLdap
     */
    private String type;


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

    /**
     * 公司的ID
     * */
    private String companyId;


    private Date createTime;

    private Date modifyTime;

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

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
