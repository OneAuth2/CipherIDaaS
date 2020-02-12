package com.portal.service;



import com.portal.domain.SmsInfoDomain;

import java.util.Map;

/**
 * Created by 95744 on 2018/9/27.
 */
public interface SmsService {


    public SmsInfoDomain getSmsInfo(SmsInfoDomain smsInfoDomain);


    public String getSmsRedisCodeInfo(String telephone);

    /**
     * 发送邮箱验证码
     * @param companyUuid
     * @param mail
     * @return
     * @throws Exception
     */
    boolean sendMailMsg(String companyUuid, String mail);

    public Boolean sendPhoneMsg(String companyUuid, String telephone);

    public SmsInfoDomain getSmsInfoConfig(String companyUUid);




}
