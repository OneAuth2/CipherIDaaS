package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.domain.web.SmsChannelInfo;
import cipher.console.oidc.mapper.SmsChannelInfoMapper;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.SmsChannelInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SmsChannelInfoServiceImpl implements SmsChannelInfoService {

    @Autowired
    private SmsChannelInfoMapper smsChannelInfoMapper;



    @Autowired
    private RedisClient<String, Object> redisTemplate   = new RedisClient<String, Object>();


    @Override
    public SmsChannelInfo getSmsChannelInfo(SmsChannelInfo smsChannelInfo) {
        return smsChannelInfoMapper.getSmsChannelInfo(smsChannelInfo);
    }

    @Override
    public int updateSmsChannelInfo(SmsChannelInfo smsChannelInfo) {
        return smsChannelInfoMapper.updateByPrimaryKeySelective(smsChannelInfo);
    }

    @Override
    public int insertSmsChannelInfo(SmsChannelInfo smsChannelInfo) {
        return smsChannelInfoMapper.insertSelective(smsChannelInfo);
    }

    @Override
    public List<SmsChannelInfo> getSmsChannelList(SmsChannelInfo smsChannelInfo) {
        return smsChannelInfoMapper.getSmsChannelList(smsChannelInfo);
    }

    @Override
    public String getSmsCodeInfo(String telephone) {
        Object obj = redisTemplate.get(CacheKey.getMobilePhoneSmsStrCacheKey(telephone));
        if (obj == null) {
            return null;
        }
        return (String) obj;
    }

    @Override
    public SmsChannelInfo selectByPrimaryKey(Integer id) {
        return smsChannelInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public SmsChannelInfo selectSmsChannel(SmsChannelInfo record) {
        return smsChannelInfoMapper.selectSmsChannel(record);
    }

    @Override
    public int updateByKey(SmsChannelInfo record) {
        return smsChannelInfoMapper.updateByKey(record);
    }
}
