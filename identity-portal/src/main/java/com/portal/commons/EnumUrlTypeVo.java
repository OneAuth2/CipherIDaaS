package com.portal.commons;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/10.
 */
public class EnumUrlTypeVo implements Serializable {
    private String desc;  //类型描述
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
