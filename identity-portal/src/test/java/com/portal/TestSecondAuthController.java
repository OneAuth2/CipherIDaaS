package com.portal;

import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.portal.auth.response.GetQrCodeCallBackResp;
import com.portal.auth.response.WeixinScanAuthUser;
import com.portal.commons.CacheKey;
import com.portal.model.DabbyUserInfoModel;
import com.portal.redis.RedisClient;
import com.portal.utils.httpclient.HttpClientUtils;
import com.portal.utils.httpclient.URLBuilder;
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

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestSecondAuthController extends BaseControllerTest {

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
    public void testCipherAuth() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        MvcResult result = getMvcResult(map,"/cipher/login/saiFuQrcode");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        String uuid = jsonObject.getString("uuid");

        GetQrCodeCallBackResp userInfoModel = new GetQrCodeCallBackResp();
        userInfoModel.setCertRes(83);
        userInfoModel.setCertString("uuid");
        userInfoModel.setCertRes(0);
        userInfoModel.setMail("957444517@qq.com");
        userInfoModel.setPhoneNumber("13221071988");
        userInfoModel.setUserName("admin");
        userInfoModel.setUuid(112);
        userInfoModel.setCertToken(uuid);
        redisClient.put(CacheKey.getCacheKeyCipherAuthQrcodeUuid(userInfoModel.getCertToken()),userInfoModel,7*60*1000);

        map.add("uuid",uuid);

        map.add("userId","be22b0466b1411e9b5af00163e00cc6a");


        result = getMvcResult(map,"/cipher/user/saifuSecondPolling");
        content = result.getResponse().getContentAsString();
        jsonObject = parseJsonObject(content);
        Assert.assertEquals(getReturnCode(),0);
    }


    @Test
    public void testBayMaxAuth() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        MvcResult result = getMvcResult(map,"/cipher/login/daBaiQrcode");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

        String uuid = jsonObject.getString("uuid");

        map.clear();
        map.add("companyUUid","123456");
        map.add("uuid",uuid);
        result = getMvcResult(map,"/cipher/user/daBaiSecondPolling");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),126);


        DabbyUserInfoModel userInfoModel =new DabbyUserInfoModel();
        userInfoModel.setCert_mode(83);
        userInfoModel.setCert_res(0);
        userInfoModel.setCert_token(uuid);
        userInfoModel.setFull_name("dongmou");
        userInfoModel.setId_num("121231231");
        redisClient.put(CacheKey.getUserLoginUuid(userInfoModel.getCert_token()),userInfoModel,7*60*1000);


        result = getMvcResult(map,"/cipher/user/daBaiSecondPolling");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }


    @Test
    public void testCipherTotpAuth() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("userId","be2324306b1411e9b5af00163e00cc6a");
        map.add("totpCode","330798");
        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/totpSecondCheck");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }

    @Test
    public void testMailCodeAuth() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();

//        map.add("mail","87998937@qq.com");
//        map.add("companyUUid","123456");
//        MvcResult result = getMvcResult(map,"/cipher/user/sendMailCode");
//        String content = result.getResponse().getContentAsString();
//        JSONObject jsonObject = parseJsonObject(content);
//        Assert.assertEquals(getReturnCode(),0);

        String code = "243440";

        map.clear();
        map.add("mail","957444517@qq.com");
        map.add("code",code);
        map.add("companyUUid","123456");
        map.add("userId","2d4de6ac1d0e42d99574d5c95e371134");

        MvcResult result = getMvcResult(map,"/cipher/user/mailCodeSecondChecked");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }

    @Test
    public void testPhoneCodeAuth() throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("phoneNumber","13221071988");
        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/sendPhoneCode");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject =parseJsonObject(content);
        Assert.assertEquals(getReturnCode(),0);

        String code = "456448";
        map.clear();
        map.add("code",code);
        map.add("phoneNumber","13221071988");
        map.add("companyUUid","123456");
         result = getMvcResult(map,"/cipher/user/phoneCodeSecondChecked");
         content = result.getResponse().getContentAsString();
         jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }


    @Test
    public void testBayMaxScan() throws Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("uuid","8237a65f-bdc4-4f8c-824c-5aaa5065546b");
        map.add("companyUUid","123456");
        MvcResult result = getMvcResult(map,"/cipher/user/daBaiPolling");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject =parseJsonObject(content);
        System.out.println(jsonObject.toJSONString());
    }


    @Test
    public void testSetPhoneCode()throws  Exception{
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_"+"15157265612","123456");
    }


    @Test
    public void testDingPush()throws  Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("userId","6e70ca1bb74d11e9b5af00163e00cc6a");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/dingPushSecondCheck");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }


    @Test
    public void testDingPolling()throws  Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("userId","6e70ca1bb74d11e9b5af00163e00cc6a");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/dingPushPolling");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }



    @Test
    public void testLocal()throws  Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("accountNumber","10000921");
        map.add("pwd","Hy123456");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/pwdLoginChecked");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }



    @Test
    public void testDoOidcAuth()throws  Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("accountNumber","10000921");
        map.add("pwd","Hy123456");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/oidc/doAuthOidcAuthorize");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }



    @Test
    public void testDingTalkLogin()throws  Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("code","8a7ed23ae5593db380574848edeb95a6");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/dingTalkLogin");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }




    @Test
    public void testSendMailCode()throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();

        map.add("mail","liuying@cipherchina.com");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/sendMailCode");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }



    @Test
    public void testCheckOtpDynamicCode()throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();

        map.add("userId","2d4de6ac1d0e42d99574d5c95e371134");

        map.add("companyUUid","123456");

        map.add("otpDynamicCode","505916");

        MvcResult result = getMvcResult(map,"/cipher/user/otpDynamicSecondCheck");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);
    }

    @Test
    public void testGetWeixinConfig()throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/getWeiXinAppId");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);



    }




    @Test
    public void testWeixinScan()throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("code","KMaO13Ze8bvAz-zcZSQhF3tPvPFu0kEzhc_Ar0Gc-3g");

        map.add("companyUUid","123456");

        MvcResult result = getMvcResult(map,"/cipher/user/weixinPolling");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);



    }




    @Test
    public void testSecondWeixinScan()throws  Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("code","itiaQZO98PCPzpru2Um2rLD8P7KjENmDhz-ualijWcQ");

        map.add("companyUUid","123456");

        map.add("userId","2d4de6ac1d0e42d99574d5c95e371134");

        MvcResult result = getMvcResult(map,"/cipher/user/weixinSecondAuth");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);



    }






}
