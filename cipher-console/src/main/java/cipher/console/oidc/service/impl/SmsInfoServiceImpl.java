package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.SmsInfoDomain;
import cipher.console.oidc.mapper.SmsInfoMapper;
import cipher.console.oidc.service.SmsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 95744 on 2018/8/23.
 */
@Service
public class SmsInfoServiceImpl implements SmsInfoService {
    @Autowired
    private SmsInfoMapper smsInfoMapper;

    @Override
    public SmsInfoDomain getSmsInfo(SmsInfoDomain smsInfoDomai) {

        return smsInfoMapper.getSmsInfo(smsInfoDomai);
    }


    @Override
    public int updateSmsInfo(SmsInfoDomain smsInfoDomain) {
        return smsInfoMapper.updateSmsInfo(smsInfoDomain);
    }

    @Override
    public int insertSmsInfo(SmsInfoDomain smsInfoDomain) {
        return smsInfoMapper.insertSmsInfo(smsInfoDomain);
    }
}
