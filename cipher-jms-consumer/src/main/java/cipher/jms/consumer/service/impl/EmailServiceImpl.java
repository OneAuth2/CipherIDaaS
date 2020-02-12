package cipher.jms.consumer.service.impl;

import cipher.jms.consumer.common.CacheKey;
import cipher.jms.consumer.common.Constants;
import cipher.jms.consumer.domain.EmailAccountInfoDomain;
import cipher.jms.consumer.domain.EmailInfoDomain;
import cipher.jms.consumer.domain.EmailSeedKeyInfo;
import cipher.jms.consumer.mapper.EmailInfoMapper;
import cipher.jms.consumer.redis.RedisClient;
import cipher.jms.consumer.service.EmailService;
import cipher.jms.consumer.util.MailUtil;
import cipher.jms.consumer.util.NumberUtil;

import cipher.jms.consumer.util.QuickMarkUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailInfoMapper emailInfoMapper;

    @Autowired
    private RedisClient<String,Object> redisClient;

    @Override
    public String getAndSendEmailStr(String mail,String companyId) {
        String code = NumberUtil.createData(6);
        if(StringUtils.isEmpty(companyId)){
            companyId=Constants.COMPANYID;
        }
        EmailInfoDomain emailInfoDomain=getEmailInfo(companyId);
        MailUtil mailUtil = new MailUtil(mail, code,emailInfoDomain);
        //mailUtil.run();
        try {
                emailInfoDomain.setKapataCode(code);
                Map<String, String> param = new HashMap<String, String>();
                param.put("code", code);
                String newMsg=convertMessage(emailInfoDomain.getDescribes(),param);
                emailInfoDomain.setDescribes(newMsg);
                emailInfoDomain.setEmailAddress(mail);
                mailUtil.sendTenxungEmail(emailInfoDomain);
        } catch (UnsupportedEncodingException e) {
                 e.printStackTrace();
        }
        redisClient.put(CacheKey.getyKataCodeCacheKe(mail), code, emailInfoDomain.getEffectiveTime()*60);
        return code;
    }



    public static String convertMessage(String template, Map<String, String> param) {
        if (template != null && param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }
        return template;
    }



    @Override
    public String getEmailStr(String mail) {
        return redisClient.get(CacheKey.getyKataCodeCacheKe(mail))==null ? null :
                (String) redisClient.get(CacheKey.getyKataCodeCacheKe(mail));
    }

    @Override
    public EmailInfoDomain getEmailInfo(String companyId) {
        Object obj=redisClient.get(CacheKey.getCacheKeyEmailInfo());
        if(null==obj){
            obj=emailInfoMapper.getEmailInfo(companyId);
        }
        return (EmailInfoDomain)obj;
    }

    @Override
    public String getAndSendEmailInformStr(EmailAccountInfoDomain emailAccountInfoDomain) {
        String companyId = emailAccountInfoDomain.getCompanyId();

        String newDescribes = "";
        if(StringUtils.isEmpty(emailAccountInfoDomain.getCompanyId())){
            companyId=Constants.COMPANYID;
        }
        EmailInfoDomain emailInfoDomain=getEmailInfo(companyId);
        MailUtil mailUtil = new MailUtil(emailAccountInfoDomain.getMail(),emailAccountInfoDomain.getAccount(),emailAccountInfoDomain.getPassword(),emailInfoDomain);
        //mailUtil.run();

        try {
            Map<String,String> map = new HashMap<>();
            map.put("account", emailAccountInfoDomain.getAccount());
            map.put("password",emailAccountInfoDomain.getPassword());
            emailInfoDomain.setTitle(emailInfoDomain.getDredgeTitle());
            newDescribes = convertMessage(emailInfoDomain.getDredgeDescribes(), map);
            emailInfoDomain.setDescribes(newDescribes);
            emailInfoDomain.setEmailAddress(emailAccountInfoDomain.getMail());
            mailUtil.sendTenxungEmail(emailInfoDomain);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return newDescribes;
    }

    @Override
    public String sendEmailSeedkey(EmailSeedKeyInfo emailSeedKeyInfo) {
        String companyId = emailSeedKeyInfo.getCompanyId();

        String newDescribes = "";
        String imgPath = System.getProperty("user.dir")+
                File.separator+"src"+File.separator+"main"+
                File.separator+"resources"+File.separator+"static"+File.separator+"image";
        if(StringUtils.isEmpty(emailSeedKeyInfo.getCompanyId())){
            companyId=Constants.COMPANYID;
        }
        EmailInfoDomain emailInfoDomain=getEmailInfo(companyId);
        MailUtil mailUtil = new MailUtil(emailSeedKeyInfo.getMail(),emailSeedKeyInfo.getDynamicPassword(),emailInfoDomain);
        //mailUtil.run();
        try {
            String filename = QuickMarkUtil.encode(emailSeedKeyInfo.getDynamicPassword(), imgPath);
            emailInfoDomain.setImgId(filename);
            emailInfoDomain.setImgPath(imgPath);
            emailInfoDomain.setTitle(emailInfoDomain.getDredgeTitle());
            emailInfoDomain.setEmailAddress(emailSeedKeyInfo.getMail());
            mailUtil.sendImageEmail(emailInfoDomain);
            //邮件发送成功后删除二维码
            File file = new File(imgPath+File.separator+filename);
            if(file.exists()){
                file.delete();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newDescribes;
    }


}
