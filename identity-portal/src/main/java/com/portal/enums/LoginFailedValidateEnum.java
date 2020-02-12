package com.portal.enums;

/**
 * 登录状态检测开关：1-打开 0-关闭
 * */
public enum LoginFailedValidateEnum {

    OPEN(1, "打开"), CLOSE(0, "关闭");

    private int    type;
    private String desc;

    private LoginFailedValidateEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
