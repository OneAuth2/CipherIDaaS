package com.portal.service;
import com.cipher.china.channel.channels.BayMaxScanAuthChannel;
import com.cipher.china.channel.channels.CipherScanAuthChannel;
import com.cipher.china.channel.channels.DingTalkScanAuthChannel;
import com.cipher.china.channel.channels.WeixinScanAuthChannel;
import com.cipher.china.channel.factory.AuthMachine;
import com.cipher.china.channel.pojo.AuthInfo;
import com.cipher.china.channel.pojo.AuthStrategy;

public interface PortalAuthChannelService {

    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId);


    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                     BayMaxScanAuthChannel.BayMaxScanInfoListener listener);

    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                     CipherScanAuthChannel.CipherScanInfoListener listener);

    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                     DingTalkScanAuthChannel.DingTalkScanInfoListener listener);

    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                     WeixinScanAuthChannel.WeixinScanInfoListener listener);

    AuthMachine auth(AuthStrategy authStrategy, AuthInfo authInfo, String companyId,
                     BayMaxScanAuthChannel.BayMaxScanInfoListener bayMaxListener,
                     CipherScanAuthChannel.CipherScanInfoListener cipherAuthListener,
                     DingTalkScanAuthChannel.DingTalkScanInfoListener dingTalkListener,
                     WeixinScanAuthChannel.WeixinScanInfoListener weixinlistener);



}
