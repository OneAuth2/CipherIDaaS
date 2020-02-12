package cipher.jms.consumer.service.impl;

import cipher.jms.consumer.common.CacheKey;
import cipher.jms.consumer.common.Constants;
import cipher.jms.consumer.domain.SmsAccountInfoDomain;
import cipher.jms.consumer.domain.SmsChannelInfo;
import cipher.jms.consumer.redis.RedisClient;
import cipher.jms.consumer.service.AliyunSmsService;
import cipher.jms.consumer.service.RonglianSmsService;
import cipher.jms.consumer.service.SmsChannelInfoService;
import cipher.jms.consumer.service.SmsSendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SmsSendServiceImpl implements SmsSendService {

    @Autowired
    private RedisClient<String, Object> redisTemplate = new RedisClient<String, Object>();

    @Autowired
    private RonglianSmsService ronglianSmsService;

    @Autowired
    private AliyunSmsService aliyunSmsService;

    @Autowired
    private SmsChannelInfoService smsChannelInfoService;

    @Override
    public String getSmsCodeInfo(String telephone,String companyId) {
        SmsChannelInfo record = new SmsChannelInfo();
        if(org.apache.commons.lang.StringUtils.isEmpty(companyId)){
            companyId= Constants.COMPANYID;
        }
        record.setCompanyId(companyId);
        SmsChannelInfo smsChannelInfo = smsChannelInfoService.getSmsChannelInfo(record);
        String code = "";
        if (null != smsChannelInfo && StringUtils.isNotEmpty(String.valueOf(smsChannelInfo.getType()))) {
            Integer smsType = smsChannelInfo.getType();
            switch (smsType) {
                case 1:
                    code = ronglianSmsService.getAndSendMobilePhoneSmsStr(telephone,companyId);
                    break;
                case 2:
                    code = aliyunSmsService.getAliyunSendMobilePhoneSmsStr(telephone,companyId);
                    break;
                default:
                    break;
            }
        }
        return code;
    }

    @Override
    public String getSmdInform(SmsAccountInfoDomain smsAccountInfoDomain) {
        SmsChannelInfo record = new SmsChannelInfo();
        record.setCompanyId(smsAccountInfoDomain.getCompanyId());
        SmsChannelInfo smsChannelInfo = smsChannelInfoService.getSmsChannelInfo(record);
        String code = "";
        if (null != smsChannelInfo && StringUtils.isNotEmpty(String.valueOf(smsChannelInfo.getType()))) {
            Integer smsType = smsChannelInfo.getType();
            switch (smsType) {
                case 1:
                    code = ronglianSmsService.getAndSendMobilePhoneSmsInformStr(smsAccountInfoDomain);
                    break;
                case 2:
                    code = aliyunSmsService.getAliyunSendMobilePhoneSmsInformStr(smsAccountInfoDomain);
                    break;
                default:
                    break;
            }
        }
        return code;
    }


}
