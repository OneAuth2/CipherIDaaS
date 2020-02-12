package com.portal.commons;

public enum PasswordType {

    EASY(1,"需配置6位及6位以上密码"),
    MEDIUM(2,"需配置8位及8位以上密码，至少包含数字+字母"),
    COMPLEX(3,"需配置8位及8位以上密码，至少包含数字+大写字母+小写字母");



    private int code;

    private String type;

    PasswordType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }




}
