package com.portal.enums;

public enum UserStateEnum {

    IS_EXIT(1201, "账号不存在"),
    IS_STOP(1202, "账号已停用"),
    IS_LOCK(1203, "账号已锁定");


    private int code;
    private String desc;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    UserStateEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
