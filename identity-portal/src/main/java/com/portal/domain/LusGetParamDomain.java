package com.portal.domain;

import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2019/7/18 20:15
 */
public class LusGetParamDomain implements Serializable {

    private String key;

    private String value;


    public LusGetParamDomain() {
    }

    public LusGetParamDomain(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
