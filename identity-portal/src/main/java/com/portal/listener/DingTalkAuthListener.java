package com.portal.listener;

import com.cipher.china.channel.channels.DingTalkScanAuthChannel;
import com.cipher.china.channel.pojo.DingTalkScanAuthUser;

import java.io.Serializable;

public class DingTalkAuthListener implements DingTalkScanAuthChannel.DingTalkScanInfoListener,Serializable {

    private DingTalkScanAuthUser dingTalkScanAuthUser;

    @Override
    public void setDingTalkScanInfo(DingTalkScanAuthUser dingTalkScanAuthUser) {
        this.dingTalkScanAuthUser = dingTalkScanAuthUser;
    }

    public DingTalkScanAuthUser getDingTalkScanAuthUser() {
        return dingTalkScanAuthUser;
    }
}
