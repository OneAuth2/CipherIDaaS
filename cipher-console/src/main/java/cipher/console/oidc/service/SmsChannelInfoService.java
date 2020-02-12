package cipher.console.oidc.service;


import cipher.console.oidc.domain.web.SmsChannelInfo;

import java.util.List;

public interface SmsChannelInfoService {


    public SmsChannelInfo getSmsChannelInfo(SmsChannelInfo smsChannelInfo);

    public int updateSmsChannelInfo(SmsChannelInfo smsChannelInfo);


    public int insertSmsChannelInfo(SmsChannelInfo smsChannelInfo);

    List<SmsChannelInfo> getSmsChannelList(SmsChannelInfo smsChannelInfo);


    public String getSmsCodeInfo(String telephone);

    SmsChannelInfo selectByPrimaryKey(Integer id);


    SmsChannelInfo selectSmsChannel(SmsChannelInfo record);

    int updateByKey(SmsChannelInfo record);









}
