package cipher.jms.consumer.service.impl;

import cipher.jms.consumer.domain.SmsChannelInfo;
import cipher.jms.consumer.mapper.SmsChannelInfoMapper;
import cipher.jms.consumer.service.SmsChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsChannelInfoServiceImpl implements SmsChannelInfoService {

    @Autowired
    private SmsChannelInfoMapper smsChannelInfoMapper;

    @Override
    public SmsChannelInfo getSmsChannelInfo(SmsChannelInfo smsChannelInfo) {
      return   smsChannelInfoMapper.getSmsChannelInfo(smsChannelInfo);
    }
}
