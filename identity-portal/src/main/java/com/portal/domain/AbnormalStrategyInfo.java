package com.portal.domain;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/8/30.
 */
public class AbnormalStrategyInfo implements Serializable {

    /**你好**/
    private int id;
    private int type;
    private int status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
