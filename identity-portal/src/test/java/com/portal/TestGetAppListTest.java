package com.portal;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.portal.redis.RedisClient;
import com.portal.utils.rsa.HengyiRsaKey;
import mx4j.tools.config.DefaultConfigurationBuilder;
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

import java.net.URLEncoder;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestGetAppListTest extends BaseControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private RedisClient<String, DefaultConfigurationBuilder.Object> redisClient = new RedisClient<>();

    @Before
    public void setUp() throws Exception {
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }


    @Test
    public void testMailCollect() throws Exception {
        String timestamp="1564367169";
        String userId="0033c194f2b04b04bc33fce20db3591b";

        String token= AlipaySignature.rsa256Sign(userId+timestamp, HengyiRsaKey.PRI_KEY,"utf-8");

        //检查是否已经绑定手机号
        MultiValueMap map = new LinkedMultiValueMap();

        map.add("companyUUid", "123456");

        map.add("userId", userId);

        map.add("token", URLEncoder.encode(token,"UTF-8"));

        map.add("timestamp", timestamp);

        MvcResult result = getMvcResult(map, "/cipher/portal/getUserAppList");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        Assert.assertEquals(getReturnCode(), 0);

        content = result.getResponse().getContentAsString();

    }




    @Test
    public void testDoOidc() throws Exception {

        //检查是否已经绑定手机号
        MultiValueMap map = new LinkedMultiValueMap();

        map.add("companyUUid", "123456");

        map.add("applyId", "4b1JtV");

        MvcResult result = getMvcResult(map, "/cipher/oidc/doPortalAuthorize");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        Assert.assertEquals(getReturnCode(), 0);

        content = result.getResponse().getContentAsString();

    }
}





