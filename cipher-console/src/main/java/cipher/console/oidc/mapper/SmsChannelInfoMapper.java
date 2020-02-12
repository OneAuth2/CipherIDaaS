package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.SmsChannelInfo;

import java.util.List;

public interface SmsChannelInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SmsChannelInfo record);

    int insertSelective(SmsChannelInfo record);

    SmsChannelInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SmsChannelInfo record);

    int updateByPrimaryKey(SmsChannelInfo record);


    SmsChannelInfo getSmsChannelInfo(SmsChannelInfo record);

    List<SmsChannelInfo> getSmsChannelList(SmsChannelInfo smsChannelInfo);


    SmsChannelInfo selectSmsChannel(SmsChannelInfo record);

    int updateByKey(SmsChannelInfo record);
}