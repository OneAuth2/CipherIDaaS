package com.portal.service;


import com.portal.ctid.request.BaseRequest;
import com.portal.domain.BaseReq;

/**
 * @Author: zt
 * @Date: 2019/1/5 14:45
 */
public interface SendTcpMsgService {

    /**
     * 发送TCP 消息，返回的为json数据
     *
     * @param baseRequest 消息格式
     * @param host        接收消息的ip
     * @param port        接收消息的端口
     * @return json数据
     */
    public String sendMsg(String host, int port, BaseReq baseRequest);

}
