package com.portal;

import com.alibaba.fastjson.JSONObject;

import com.portal.auth.response.GetQrCodeCallBackResp;
import com.portal.commons.CacheKey;
import com.portal.model.DabbyUserInfoModel;
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

/**
 * 登录流程测试类
 * create by shizhao at 2019/5/20
 *
 * @author shizhao
 * @since  2019/5/20
 * */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestLoginController extends BaseControllerTest{


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RedisClient redisClient;

    @Before
    public void setUp() throws Exception{
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }


    @Test
    public void  testPwdLoginChecked() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        map.add("accountNumber","yuyang");
        map.add("pwd","123123");

        MvcResult result = getMvcResult(map,"/cipher/user/pwdLoginChecked");

        int status = result.getResponse().getStatus();

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }


    @Test
    public void testCipherAuthLoginChecked() throws Exception{

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
        result = getMvcResult(map,"/cipher/user/saiFuPolling");


        content = result.getResponse().getContentAsString();
        JSONObject jsonObject1 = parseJsonObject(content);
        Assert.assertEquals(getReturnCode(),0);
    }


    @Test
    public void testBayMaxLoginChecked() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        MvcResult result = getMvcResult(map,"/cipher/login/daBaiQrcode");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        String uuid = jsonObject.getString("uuid");

        DabbyUserInfoModel userInfoModel =new DabbyUserInfoModel();
        userInfoModel.setCert_mode(83);
        userInfoModel.setCert_res(0);
        userInfoModel.setCert_token(uuid);
        userInfoModel.setFull_name("18267101714");
        userInfoModel.setId_num("222401199512250639");
        redisClient.put(CacheKey.getUserLoginUuid(userInfoModel.getCert_token()),userInfoModel,7*60*1000);
        map.add("uuid",uuid);
        result = getMvcResult(map,"/cipher/user/daBaiPolling");
        content = result.getResponse().getContentAsString();

        jsonObject  = parseJsonObject(content);
        Assert.assertEquals(getReturnCode(),0);
    }


    @Test
    public void testPhoneCodeLoginChecked() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("phoneNumber","13221071988");
        map.add("companyUUid","123456");

//        MvcResult result = getMvcResult(map,"/cipher/user/sendPhoneCode");
//        String content = result.getResponse().getContentAsString();
//
//        JSONObject jsonObject = parseJsonObject(content);

//        Assert.assertEquals(getReturnCode(),0);
//        String code = (String)redisClient.get("KEY_MOBILE_PHONE_SMS_STR_13221071988");

        map.add("code","456448");
        map.add("phone","13221071988");

        MvcResult result = getMvcResult(map,"/cipher/user/phoneCodeLogin");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }

    @Test
    public void testDingTalkChecked() throws Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("code","cd3990726139327dbd7bcba56da87905");

    }



    @Test
    public void testOtpQrcode() throws Exception{
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("userId","15d3ed5bcfa811e9b5af00163e00cc6a");
        map.add("companyUUid","123456");


        MvcResult result = getMvcResult(map,"/cipher/login/otpQrcode");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }




    }
