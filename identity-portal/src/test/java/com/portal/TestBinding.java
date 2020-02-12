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

/**
 * 绑定流程测试类
 * create by shizhao at 2019/5/20
 *
 * @author shizhao
 * @since 2019/5/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestBinding extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RedisClient redisClient;

    @Before
    public void setUp() throws Exception {
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }

    @Test
    public void getAuthFlow() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid", "123456");

        MvcResult result = getMvcResult(map, "/cipher/user/bindingFlow");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

    }


    @Test
    public void testBayMaxMatch() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("daBaiId", "222401199512250639");
        map.add("companyUUid", "123456");

        MvcResult result = getMvcResult(map, "/cipher/user/daBaiAutoMatch");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        String userId = jsonObject.getString("userId");

        Assert.assertEquals(getReturnCode(), 0);
        Assert.assertEquals(jsonObject.getString("userId"), userId);


        map.clear();
        map.add("userId", userId);
        map.add("companyUUid", "123456");
        map.add("daBaiId", "222401199512250639");

        result = getMvcResult(map, "/cipher/user/daBaiBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

    }


    @Test
    public void testCipherMatch() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid", "123456");
        map.add("saiFuId", "115");
        map.add("phoneNumber", "15157265613");
        map.add("mail", "87998937@qq.com");

        MvcResult result = getMvcResult(map, "/cipher/user/saiFuAutoMatch");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        String userId = jsonObject.getString("userId");

        Assert.assertEquals(getReturnCode(), 0);
        Assert.assertEquals(userId, "be22b0466b1411e9b5af00163e00cc6a");

        map.clear();
        map.add("userId", userId);
        map.add("companyUUid", "123456");
        map.add("saiFuId", "115");

        result = getMvcResult(map, "/cipher/user/cipherBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

    }

    @Test
    public void testDingTalkMatch() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("unionId", "6jgxCN0TZkfOiS1DL0yOiPeAiEiE");
        map.add("companyUUid", "123456");
        map.add("phone", "13221071988");
        map.add("mail", "");

        MvcResult result = getMvcResult(map, "/cipher/user/dingTalkAutoMatch");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        String userId = jsonObject.getString("userId");

        Assert.assertEquals(getReturnCode(), 0);
        Assert.assertEquals(userId, "be22b0466b1411e9b5af00163e00cc6a");


        map.clear();
        map.add("userId", userId);
        map.add("companyUUid", "123456");
        map.add("unionId", "12345678991233457");
        map.add("openId", "1231232133133131");
        map.add("dingTalkUserId", "123123123213");

        result = getMvcResult(map, "/cipher/user/dingTalkBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);
    }


    @Test
    public void testCipherBinding() throws Exception {

        String companyUUid = "123456";

        String mail = "87998937@qq.com";

        String phone = "15157265612";

        String userId = "be231e356b1411e9b5af00163e00cc6a";

        String saiFuId = "1688";

        String daBaiId = "330326199587980033";

        String unionId = "1234567";

        String openId = "!234567";

        String dingTalkUserId = "1688782932";

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid", companyUUid);

        MvcResult result = getMvcResult(map, "/cipher/user/bindingFlow");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);


        redisClient.put("KEY_KATACODE_87998937@qq.com", "144423");
        redisClient.put("KEY_MOBILE_PHONE_SMS_STR_15157265612", "319035");


        String mailCode = (String) redisClient.get("KEY_KATACODE_87998937@qq.com");
        String phoneCode = (String) redisClient.get("KEY_MOBILE_PHONE_SMS_STR_15157265612");

        map = new LinkedMultiValueMap();
        map.add("phoneNumber", phone);
        map.add("code", phoneCode);
        map.add("companyUUid", companyUUid);
        map.add("userId",userId);

        result = getMvcResult(map, "/cipher/user/phoneCodeChecked");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

        map.clear();
        map.add("code", mailCode);
        map.add("mail", mail);
        map.add("companyUUid", companyUUid);
        map.add("userId",userId);

        result = getMvcResult(map, "/cipher/user/mailCodeChecked");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

        map.clear();
        map.add("userId",userId);
        map.add("companyUUid",companyUUid);
        map.add("saiFuId","1688");

        result = getMvcResult(map,"/cipher/user/cipherBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);


        map.clear();
        map.add("userId",userId);
        map.add("companyUUid",companyUUid);
        map.add("daBaiId",daBaiId);

        result = getMvcResult(map,"/cipher/user/daBaiBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);


        map.clear();
        map.add("userId",userId);
        map.add("companyUUid",companyUUid);
        map.add("unionId",unionId);
        map.add("openId",openId);
        map.add("dingTalkUserId",dingTalkUserId);

        result = getMvcResult(map,"/cipher/user/dingTalkBinding");

        content = result.getResponse().getContentAsString();

        jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(), 0);

    }


}
