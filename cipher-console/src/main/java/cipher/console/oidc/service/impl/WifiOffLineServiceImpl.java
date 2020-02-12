package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.WifiActionMessage;
import cipher.console.oidc.jms.JMSProducer;
import cipher.console.oidc.jms.JMSType;
import cipher.console.oidc.service.WifiOffLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WifiOffLineServiceImpl implements WifiOffLineService {
    @Autowired
    private JMSProducer jmsProducer;

    @Override
    public void sendWifiOffLineMsg(WifiActionMessage wifiActionMessage) {
        //下线操作
        wifiActionMessage.setAction(0);
        jmsProducer.sendMessage(wifiActionMessage, JMSType.WIFI_OFF_LINE);
    }
}
