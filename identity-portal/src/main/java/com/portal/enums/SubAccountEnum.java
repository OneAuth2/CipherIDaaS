package com.portal.enums;

public enum SubAccountEnum {

    SUCCESS(0,"成功"),
    FAIL(1,"失败"),
    ERROR(2,"服务器错误"),
    NULL(3,"应用从账号为空"),
    PASSWORDERROR(4,"应用账号密码错误"),
    SUBACCOUNT_LOST(5,"应用从账号信息缺失"),
    APPLICATION_TYPE_LOST(6,"应用类型缺失");



    private int code;

    private String message;

    SubAccountEnum(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
