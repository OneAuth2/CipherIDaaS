package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.EmailInfoDomain;
import cipher.console.oidc.mapper.EmailInfoMapper;
import cipher.console.oidc.service.EmailService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by 95744 on 2018/8/27.
 */

@Service
public class EmailServiceImpl  implements EmailService {

    @Autowired
    private EmailInfoMapper emailInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    @Override
    public EmailInfoDomain getEmailInfo(EmailInfoDomain emailInfoDomain) {
        return emailInfoMapper.getEmailInfo(emailInfoDomain);
    }

    //Redis模糊匹配批量删除操作
    public void deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys("email:*");
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }


    @Override
    public int updateEmailInfo(EmailInfoDomain emailInfoDomain) {
        deleteByPrex("*email*");
        return emailInfoMapper.updateEmailInfo(emailInfoDomain);
    }

    @Override
    public int insertEmailInfo(EmailInfoDomain emailInfoDomain) {
        return emailInfoMapper.insertEmailInfo(emailInfoDomain);
    }
}
