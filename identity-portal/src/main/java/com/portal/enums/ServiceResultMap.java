package com.portal.enums;

/**
 * @Author: TK
 * @Date: 2019/7/19 10:55
 */
public enum ServiceResultMap {

    NO_APPLICATION(6000, "应用不存在"),

    NO_APPLICATION_RULE(6001,"应用规则不存在"),

    NO_APPLICATION_SUB(6002,"应用从账号信息缺失，请联系管理员"),

    NO_PARAMS_ERROR(6003,"参数错误"),

    APPLICATION_SUB_NULL(6004,"应用账号或密码为空"),
    
    SYSTEM_ERROR(6005,"内部服务器错误")
    ;

    /**
     * 枚举的key
     */
    private int code;

    /**
     * message枚举的内容
     */
    private String message;


    ServiceResultMap(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
