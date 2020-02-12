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

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestResetPasswordController extends BaseControllerTest {

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
    public void testSetPassword() throws Exception{

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("userId","2d4de6ac1d0e42d99574d5c95e371134");
        map.add("companyUUid","123456");
        map.add("password","123456");
        MvcResult result = getMvcResult(map,"/cipher/forget/reset");

        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);

        Assert.assertEquals(getReturnCode(),0);

    }




}
