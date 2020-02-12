package com.portal.listener;


import com.cipher.china.channel.channels.WeixinScanAuthChannel;
import com.cipher.china.channel.pojo.WeixinScanAuthUser;

import java.io.Serializable;

public class WeixinAuthListener implements WeixinScanAuthChannel.WeixinScanInfoListener,Serializable {

    private WeixinScanAuthUser weixinScanAuthUser;


    public WeixinScanAuthUser getWeixinScanAuthUser() {
        return weixinScanAuthUser;
    }

    @Override
    public void setWeixinScanAuthUser(WeixinScanAuthUser weixinScanAuthUser) {
        this.weixinScanAuthUser = weixinScanAuthUser;
    }
}
