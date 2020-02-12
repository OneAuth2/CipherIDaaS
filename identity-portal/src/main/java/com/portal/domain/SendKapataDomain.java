package com.portal.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by 95744 on 2018/8/30.
 */
public class SendKapataDomain implements Serializable{

    private int id;
    private String username;
    private int type;
    private int sendTime;
    private Date createTime;

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

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SendKapataDomain(String username, int type, int sendTime) {
        this.username = username;
        this.type = type;
        this.sendTime = sendTime;
    }

    public SendKapataDomain() {
    }
}
