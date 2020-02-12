package com.config;

public class URLConstant {
    /**
     * 钉钉网关gettoken地址
     */
    public static final String URL_GET_TOKKEN = "https://oapi.dingtalk.com/gettoken";

    /**
     *获取用户在企业内userId的接口URL
     */
    public static final String URL_GET_USER_INFO = "https://oapi.dingtalk.com/user/getuserinfo";

    /**
     *获取用户姓名的接口url
     */
    public static final String URL_USER_GET = "https://oapi.dingtalk.com/user/get";
    /**
     * 发送工作链接通知消息的URL
     */
    public static final String URL_SEND_LINK_MESSAGE = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    /**
     * ip获取地址
     */
    public static final String IP_ADDRESS="http://ip.taobao.com/service/getIpInfo.php";
}
