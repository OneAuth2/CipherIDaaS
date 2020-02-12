package com.portal.commons;

public class ConstantType {

    /**
     * 二次认证
     */
    public static final String SECOND_LOGIN="secondLogin";
    /**
     * 二次认证开启
     */
    public static final int SECOND_LOGIN_CODE=0;

    /**
     * 二次认证未开启
     */
    public static final int NOT_SECOND_LOGIN_CODE=1;

    /**
     * 开
     */
    public static final int ON=0;

    /**
     * 本地认证
     */
    public static final  int LOCAL_AUTH=0;

    /**
     * ad认证
     */
    public static final int AD_AUTH=1;

    /**
     * 外部认证
     */
    public static final int OUTSIDE_AUTH=2;
    /**
     * 关
     */
    public static final  int OFF=1;

    /**
     * 认证开启
     */
    public static  final  int AUTH_ON= 1;

    public static final int CONSOLE_ON = 0;

    /**
     * ad认证
     */
    public static  final int AUTH_AD=1;

    /**
     * totp认证
     */
    public static  final int AUTH_AD_TOTP=5;

    /**
     * 数据库操作失败
     * */
    public static final int OPERATE_FAIED = 0;
}
