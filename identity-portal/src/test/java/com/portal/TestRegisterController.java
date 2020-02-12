package com.portal;

import com.alibaba.fastjson.JSONObject;

import com.portal.redis.RedisClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static com.portal.commons.Constants.EXPIRES;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestRegisterController extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RedisClient<String,Object> redisClient;

    @Before
    public void setUp() throws Exception{
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void  getMailAndSmsCode()throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("phoneNumber","15157265612");
        map.add("mail","87998937@qq.com");
        map.add("companyUUid","123456");


        MvcResult result = getMvcResult(map,"/cipher/user/sendPhoneCode");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

        result = getMvcResult(map,"/cipher/user/sendMailCode");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }

    @Test
    public void testRegister() throws Exception {

        String mail = "87998937@qq.com";

        String phone = "15157265612";

        String companyUUid = "123456";

        redisClient.put("KEY_KATACODE_87998937@qq.com","144423");
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_15157265612","319035");


        String mailCode = (String)redisClient.get("KEY_KATACODE_87998937@qq.com");
        String phoneCode = (String)redisClient.get("KEY_MOBILE_PHONE_SMS_STR_15157265612");

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("phoneNumber",phone);
        map.add("code",phoneCode);
        map.add("companyUUid",companyUUid);

        MvcResult result = getMvcResult(map,"/cipher/user/phoneCodeChecked");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

        map.clear();
        map.add("code",mailCode);
        map.add("mail",mail);
        map.add("companyUUid",companyUUid);

        result = getMvcResult(map,"/cipher/user/mailCodeChecked");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

        map.clear();
        map.add("companyUUid",companyUUid);
        map.add("userName","dongmou");
        map.add("phoneNumber",phone);
        map.add("mail",mail);

        result = getMvcResult(map,"/cipher/user/regist");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);



    }

    @Test
    public void testPhoneCodePut(){
        String mail = "yang.yu@cipherchina.com";
        String mail1 = "18267101714@163.com";
        String mail2 = "87998937@qq.com";


        String phone = "13957610089";
        String phone1 = "18868811135";
        String phone2 = "18600010704";
        String phone3 = "18267101714";
        String phone4 = "15157265612";

        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+phone,"123456",EXPIRES);
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+phone1,"123456",EXPIRES);
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+phone2,"123456",EXPIRES);
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+phone3,"123456",EXPIRES);
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+phone4,"123456",EXPIRES);



        redisClient.put("KEY_KATACODE_"+mail,"123456",EXPIRES);
        redisClient.put("KEY_KATACODE_"+mail1,"123456",EXPIRES);
        redisClient.put("KEY_KATACODE_"+mail2,"123456",EXPIRES);




        String phoneCode = (String)redisClient.get("KEY_MOBILE_PHONE_SMS_STR_"+phone);
        System.out.println(phoneCode);
    }

}
