package cipher.jms.consumer.service.impl;

import cipher.jms.consumer.common.CacheKey;
import cipher.jms.consumer.common.Constants;
import cipher.jms.consumer.domain.RonglianSmsConfigInfo;
import cipher.jms.consumer.domain.SmsAccountInfoDomain;
import cipher.jms.consumer.domain.SmsChannelInfo;
import cipher.jms.consumer.mapper.SmsChannelInfoMapper;
import cipher.jms.consumer.redis.RedisClient;
import cipher.jms.consumer.service.RonglianSmsService;
import cipher.jms.consumer.util.AccountValidatorUtil;
import cipher.jms.consumer.util.ronglian.CCPRestSDK;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RonglianSmsServiceImpl implements RonglianSmsService {

    private static final Logger logger = LoggerFactory.getLogger(RonglianSmsServiceImpl.class);

    @Autowired
    private RedisClient<String, Object> redisTemplate = new RedisClient<String, Object>();

    @Autowired
    private SmsChannelInfoMapper smsChannelInfoMapper;


    private int ttl = 600;                                                  // 单位秒

    private static final String accountSid = "8a216da865e6d03701660f8c38160f5a";

    private static final String authToken = "fdc6b08c3f9b48bdae7829047e6c8c45";

    private static final String appId = "8a216da865e6d03701660f8c38640f60";

    private static final String templateId = "380021";

    @Override
    public String getAndSendMobilePhoneSmsStr(String phone,String companyId) {
        if (StringUtils.isBlank(phone)) {
            return null;
        }
        if (!AccountValidatorUtil.isMobile(phone)) {
            return null;
        }
        RonglianSmsConfigInfo ronglianSmsConfigInfo = (RonglianSmsConfigInfo) redisTemplate.get(CacheKey.getCacheKeyRongLianInfo());
        if (null == ronglianSmsConfigInfo) {
            ronglianSmsConfigInfo = getRonglianSmsConfigInfo(companyId);
            redisTemplate.put(CacheKey.getCacheKeyRongLianInfo(), ronglianSmsConfigInfo);
        }
        int smsCode = (int) (Math.random() * 1000000);
        String smsCodeStr = String.format("%06d", smsCode);
        // 放入缓存，5分钟失效
        //

        HashMap<String, Object> result = null;
        CCPRestSDK restAPI = new CCPRestSDK();
        restAPI.init("app.cloopen.com", "8883");
        // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
        restAPI.setAccount(ronglianSmsConfigInfo.getAccountSid(), ronglianSmsConfigInfo.getAuthToken());
        // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
        restAPI.setAppId(ronglianSmsConfigInfo.getAppId());
        // 请使用管理控制台中已创建应用的APPID。
        result = restAPI.sendTemplateSMS(phone, ronglianSmsConfigInfo.getTemplateId(), new String[]{smsCodeStr, "10分钟"});
        logger.error("getRonglianyunSendMobilePhoneSmsStr info===..", "phone=" + phone + "smsCodeStr= " + smsCodeStr);
        if (!"000000".equals(result.get("statusCode"))) {
            // 异常返回输出错误码和错误信息
            logger.error("getRonglianyunSendMobilePhoneSmsStr error===..", "错误码=" + result.get("statusCode") + " 错误信息= " + result.get("statusMsg"));
            return null;
        }
        // 放入缓存，10分钟失效
        redisTemplate.put(CacheKey.getMobilePhoneSmsStrCacheKey(phone), smsCodeStr, ttl);
        return smsCodeStr;
    }

    @Override
    public String getAndSendMobilePhoneSmsInformStr(SmsAccountInfoDomain smsAccountInfoDomain) {
        if(smsAccountInfoDomain!=null){
            //通过redis缓存取，如果取不到，则去数据库查询并放入redis
            RonglianSmsConfigInfo ronglianSmsConfigInfo = (RonglianSmsConfigInfo) redisTemplate.get(CacheKey.getCacheKeyRongLianInfo());
            if (null == ronglianSmsConfigInfo) {
                ronglianSmsConfigInfo = getRonglianSmsConfigInfo(smsAccountInfoDomain.getCompanyId());
                redisTemplate.put(CacheKey.getCacheKeyRongLianInfo(), ronglianSmsConfigInfo);
            }
            HashMap<String, Object> result = null;
            CCPRestSDK restAPI = new CCPRestSDK();
            restAPI.init("app.cloopen.com", "8883");
            // 初始化服务器地址和端口，生产环境配置成app.cloopen.com，端口是8883.
            restAPI.setAccount(ronglianSmsConfigInfo.getAccountSid(), ronglianSmsConfigInfo.getAuthToken());
            // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在控制首页中看到开发者主账号ACCOUNT SID和主账号令牌AUTH TOKEN。
            restAPI.setAppId(ronglianSmsConfigInfo.getAppId());
            // 请使用管理控制台中已创建应用的APPID。
            result = restAPI.sendTemplateSMS(smsAccountInfoDomain.getTelephone(), ronglianSmsConfigInfo.getTemplateIdOne(), new String[]{smsAccountInfoDomain.getAccount(),smsAccountInfoDomain.getPassword()});
            logger.error("enter RonglianSmsServiceImpl.getAndSendMobilePhoneSmsInformStr info===..", "telephone=" + smsAccountInfoDomain.getAccount() + "account= " + smsAccountInfoDomain.getAccount()+"password="+smsAccountInfoDomain.getPassword());
        }
        //返回账号密码
        return "account= " + smsAccountInfoDomain.getAccount()+",password="+smsAccountInfoDomain.getPassword();
    }


    public RonglianSmsConfigInfo getRonglianSmsConfigInfo(String companyId) {
        SmsChannelInfo record = new SmsChannelInfo();
        record.setCompanyId(companyId);
        SmsChannelInfo smsChannelInfo = smsChannelInfoMapper.getSmsChannelInfo(record);
        RonglianSmsConfigInfo ronglianSmsConfigInfo = new RonglianSmsConfigInfo();
        if (null != smsChannelInfo && StringUtils.isNotEmpty(String.valueOf(smsChannelInfo.getParameter()))) {
            JSONObject jsonObject = JSONObject.fromObject(smsChannelInfo.getParameter());
            ronglianSmsConfigInfo = (RonglianSmsConfigInfo) JSONObject.toBean(jsonObject, RonglianSmsConfigInfo.class);
        }
        return ronglianSmsConfigInfo;
    }


    public static void convertObject() {
        RonglianSmsConfigInfo ronglianSmsConfigInfo = new RonglianSmsConfigInfo();
        ronglianSmsConfigInfo.setAccountSid(accountSid);
        ronglianSmsConfigInfo.setAppId(appId);
        ronglianSmsConfigInfo.setAuthToken(authToken);
        ronglianSmsConfigInfo.setTemplateId(templateId);
        //1、使用JSONObject
        JSONObject json = JSONObject.fromObject(ronglianSmsConfigInfo);
        //2、使用JSONArray
        JSONArray array = JSONArray.fromObject(ronglianSmsConfigInfo);
        String strJson = json.toString();
        String strArray = array.toString();
        System.out.println("strJson:" + strJson);
        System.out.println("strArray:" + strArray);
    }

    public static void main(String arg[]) {
        convertObject();
    }
}
