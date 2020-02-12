package com.portal.domain;

/**
 * ERP信息实体类
 * create by shizhao at 2019/5/7
 *
 * @author shizhao
 * @since  2019/5/7
 * */
public class ErpInfoDomain {

    //主键ID
    private int id;

    //名称
    private String name;

    //地址
    private String src;

    //公司ID
    private String companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "ErpInfoDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", src='" + src + '\'' +
                ", companyId='" + companyId + '\'' +
                '}';
    }
}
