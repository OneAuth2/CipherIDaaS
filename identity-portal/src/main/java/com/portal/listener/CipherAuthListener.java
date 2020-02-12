package com.portal.listener;


import com.cipher.china.channel.channels.CipherScanAuthChannel;
import com.cipher.china.channel.pojo.CipherScanAuthUser;

import java.io.Serializable;

public class CipherAuthListener implements CipherScanAuthChannel.CipherScanInfoListener, Serializable {

    private CipherScanAuthUser cipherScanInfo;

    @Override
    public void setCipherScanInfo(CipherScanAuthUser cipherScanInfo) {
        this.cipherScanInfo = cipherScanInfo;
    }

    public CipherScanAuthUser getCipherScanInfo() {
        return cipherScanInfo;
    }
}
