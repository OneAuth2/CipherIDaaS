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
public class TestOidc extends BaseControllerTest{

    @Autowired
    private WebApplicationContext webApplicationContext;



    @Before
    public void setUp() throws Exception{
        //MockMvcBuilders.webAppContextSetup(WebApplicationContext context)：指定WebApplicationContext，将会从该上下文获取相应的控制器并得到相应的MockMvc；
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();//建议使用这种
    }


    @Test
    public void testOidc() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        map.add("userId","403d66d4828b11e9b5af00163e00cc6a");
        map.add("applyId","tengxun");

        MvcResult result = getMvcResult(map,"/cipher/oidc/doAuthOidcAuthorize");
        String content = result.getResponse().getContentAsString();

        JSONObject jsonObject = parseJsonObject(content);
        Assert.assertNotEquals(getReturnCode(),0);
        content = result.getResponse().getContentAsString();

        Assert.assertEquals(getReturnCode(),0);

        content = result.getResponse().getContentAsString();


    }


    @Test
    public void testSaveSubAccount() throws Exception {

        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        map.add("subName","test");
        map.add("applyId","tengxun");
        map.add("subPwd","123456");
        map.add("userId","403d66d4828b11e9b5af00163e00cc6a");

        MvcResult result = getMvcResult(map,"/cipher/oidc/checkAndAddUser");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        Assert.assertNotEquals(getReturnCode(),0);
        content = result.getResponse().getContentAsString();
        Assert.assertEquals(getReturnCode(),0);
        content = result.getResponse().getContentAsString();


    }


    @Test
    public void testSaveSub() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        map.add("subName","test");
        map.add("applyId","tengxun");
        map.add("subPwd","123456");
        map.add("userId","403d66d4828b11e9b5af00163e00cc6a");

        MvcResult result = getMvcResult(map,"/cipher/oidc/checkAndAddUser");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        content = result.getResponse().getContentAsString();
        Assert.assertEquals(getReturnCode(),0);
    }



    @Test
    public void testCsApplication() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("companyUUid","123456");
        map.add("applyId","D41m36");
        map.add("userId","be22b0466b1411e9b5af00163e00cc6a");
        MvcResult result = getMvcResult(map,"/cipher/oidc/doCsAuthorize");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        content = result.getResponse().getContentAsString();
        Assert.assertEquals(getReturnCode(),0);
    }




    @Test
    public void testCsPath() throws Exception {
        MultiValueMap map = new LinkedMultiValueMap();
        map.add("path","C:\\Program Files (x86)\\Kingdee\\KIS\\Profession\\Advance\\KISMain.exe");
        map.add("appId","ddddaa");
        map.add("userId","be22b0466b1411e9b5af00163e00cc6a");
        MvcResult result = getMvcResult(map,"/cipher/user/saveCsPath");
        String content = result.getResponse().getContentAsString();
        JSONObject jsonObject = parseJsonObject(content);
        content = result.getResponse().getContentAsString();
        Assert.assertEquals(getReturnCode(),0);
    }


    }
