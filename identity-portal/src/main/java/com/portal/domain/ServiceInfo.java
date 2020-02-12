package com.portal.domain;


import java.util.Date;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:10
 */
public class ServiceInfo {

    private Integer id;

    private String indexId;

    private String name;

    /**
     * 0-开启，1-关闭
     */
    private Integer defaultStatus;

    /**
     * 总控制开关，0-开启，1-关闭
     */
    private Integer totalControStatus;


    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDefaultStatus() {
        return defaultStatus;
    }

    public void setDefaultStatus(Integer defaultStatus) {
        this.defaultStatus = defaultStatus;
    }

    public Integer getTotalControStatus() {
        return totalControStatus;
    }

    public void setTotalControStatus(Integer totalControStatus) {
        this.totalControStatus = totalControStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
