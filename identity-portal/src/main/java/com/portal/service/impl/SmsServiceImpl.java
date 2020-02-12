package com.portal.service.impl;



import com.portal.commons.CacheKey;
import com.portal.commons.MsgType;
import com.portal.daoAuthoriza.SendKapataDAO;
import com.portal.daoAuthoriza.SmsInfoDAO;
import com.portal.domain.EmailCodeInfoDomain;
import com.portal.domain.SmsCodeInfoDomain;
import com.portal.domain.SmsInfoDomain;
import com.portal.jms.JMSProducer;
import com.portal.jms.JMSType;
import com.portal.redis.RedisClient;

import com.portal.service.SmsService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.MsgType.PHONE_CODE_TYPE;


/**
 * Created by 95744 on 2018/9/27.
 */

@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private SmsInfoDAO smsInfoDAO;


    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<String, Object>();


    @Override
    public SmsInfoDomain getSmsInfo(SmsInfoDomain smsInfoDomain) {
        return smsInfoDAO.getSmsInfo(smsInfoDomain);
    }




    @Override
    public String getSmsRedisCodeInfo(String telephone) {
        Object obj = redisClient.get(CacheKey.getMobilePhoneSmsStrCacheKey(telephone));
        if (obj == null) {
            return null;
        }
        return (String) obj;
    }

    @Override
    public boolean sendMailMsg(String companyUuid, String mail) {
        Map<String ,Object> map=new HashMap<>();
        //入参校验
        if (StringUtils.isBlank(companyUuid) || StringUtils.isBlank(mail)) {

            logger.warn(" enter SmsserviceImpl.sendMailMsg  Error As The companyUUid =[{}] mail=[{}]", new Object[]{companyUuid, mail});
            return false;
        }

        //新建email发送邮件对象
        EmailCodeInfoDomain emailCodeInfoDomain = new EmailCodeInfoDomain();
        emailCodeInfoDomain.setMail(mail);
        emailCodeInfoDomain.setType(MsgType.EMAL_CODE_TYPE);
        emailCodeInfoDomain.setCompanyUuid(companyUuid);

        //将email对象发送到消息队列中
        try {
            jmsProducer.sendMessage(emailCodeInfoDomain, JMSType.EMAIL_SEND_SERVICE);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @Override
    public Boolean sendPhoneMsg(String companyUuid, String telephone) {

        //入参校验
        if (StringUtils.isBlank(companyUuid) || StringUtils.isBlank(telephone)) {
            logger.warn(" enter UserBinding.sendMsg  Error As The companyUUid =[{}]", new Object[]{companyUuid});
            return false;
        }

        //新建短信发送实体类
        SmsCodeInfoDomain smsCodeInfoDomain = new SmsCodeInfoDomain(telephone);
        smsCodeInfoDomain.setCompanyUuid(companyUuid);
        smsCodeInfoDomain.setDescType(PHONE_CODE_TYPE);

        //发送实体类到消息队列
        try {
            jmsProducer.sendMessage(smsCodeInfoDomain, JMSType.SMS_SEND_SERVICE);
        }catch (Exception e){
            return false;
        }

        return true;


    }

    @Override
    public SmsInfoDomain getSmsInfoConfig(String companyUUid) {
        return smsInfoDAO.getSmsConfig(companyUUid);
    }
}
