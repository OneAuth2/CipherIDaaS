package com.portal.domain;/**
 * @author lqgzj
 * @date 2019-09-04
 */

import java.util.Date;

/**
 * @Author qiaoxi
 * @Date 2019-09-0411:06
 **/
public class DynamicPasswordInfo {


    private Integer id;
    private String uuid;
    private String userId;
    private String companyId;
    private String dynamicPassword;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDynamicPassword() {
        return dynamicPassword;
    }

    public void setDynamicPassword(String dynamicPassword) {
        this.dynamicPassword = dynamicPassword;
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

    @Override
    public String toString() {
        return "DynamicPasswordInfo{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", userId='" + userId + '\'' +
                ", companyId='" + companyId + '\'' +
                ", dynamicPassword='" + dynamicPassword + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
