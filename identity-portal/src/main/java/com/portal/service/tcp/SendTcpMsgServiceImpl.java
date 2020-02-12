package com.portal.service.tcp;

import com.portal.domain.BaseReq;
import com.portal.service.SendTcpMsgService;
import com.portal.service.tcp.client.SockerClient;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2019/1/5 14:46
 */

@Service
public class SendTcpMsgServiceImpl implements SendTcpMsgService {

    @Override
    public String sendMsg(String host, int port, BaseReq baseRequest) {
        return new SockerClient().sendMessage(host, port, baseRequest);
    }

}
