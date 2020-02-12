package com.portal.commons;

/**
 * Created by IntelliJ IDEA
 * 被操作的单元
 * @author weiwenjun
 * @date 2018/9/12
 */
public enum OperationUnit {
    /**
     * 被操作的单元
     */
    UNKNOWN("unknown"),
    USER("user");


    private String value;

    OperationUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
