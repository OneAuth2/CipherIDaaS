package com.service.impl;

import com.config.URLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 对钉钉开放平台服务端SDK的封装类
 * 避免到处写SDK的调用逻辑
 */
@Service("dingOAPIService")
public class DingOAPIServiceImpl {
    private static final Logger bizLogger = LoggerFactory.getLogger(DingOAPIServiceImpl.class);
    private static final Logger mainLogger = LoggerFactory.getLogger(DingOAPIServiceImpl.class);

    /**
     * ISV发送链接通知消息
     *
     * @param agentId     授权企业的agentId
     * @param userIdList  用户列表：测试中只包含一个用户
     * @param url         发送消息包含的url地址
     * @param accessToken 授权企业的accessToken
     */
    public OapiMessageCorpconversationAsyncsendV2Response sendLinkMessage(Long agentId, List<String> userIdList, String url, String accessToken) {
        if (agentId == null || userIdList == null || userIdList.isEmpty() || accessToken == null) {
            return null;
        }

        DingTalkClient client = new DefaultDingTalkClient(URLConstant.URL_SEND_LINK_MESSAGE + "?access_token=" + accessToken);
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();

        request.setUseridList(userIdList.get(0));
        request.setAgentId(agentId);
        request.setToAllUser(false);
        //这个参数提出来。
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle("确认登录");
        msg.getLink().setText("您正在登录众合科技身份认证平台，点击确认登录。");
        msg.getLink().setMessageUrl(url);
        msg.getLink().setPicUrl("http://uia.unittec.com/20190829145443.jpg");
        request.setMsg(msg);
        OapiMessageCorpconversationAsyncsendV2Response response;
        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            bizLogger.info(e.toString(), e);
            return null;
        }
        if (response == null || !response.isSuccess()) {
            return null;
        }
        return response;
    }


}
