package com.portal.listener;


import com.cipher.china.channel.channels.BayMaxScanAuthChannel;
import com.cipher.china.channel.pojo.BayMaxScanAuthUser;

import java.io.Serializable;

public class BayMaxAuthListener implements BayMaxScanAuthChannel.BayMaxScanInfoListener,Serializable {

    private BayMaxScanAuthUser bayMaxUser;

    @Override
    public void setBayMaxScanInfo(BayMaxScanAuthUser cipherUser) {
        this.bayMaxUser = cipherUser;
    }

    public BayMaxScanAuthUser getBayMaxUser() {
        return bayMaxUser;
    }
}
