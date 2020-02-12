package com.portal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.MultiValueMap;

public class BaseControllerTest {

    private int returnCode;

    public MockMvc mockMvc;

    private String returnMsg;

    public JSONObject  parseJsonObject(String content){
        JSONObject jsonObject = JSON.parseObject(content);

        returnMsg = jsonObject.getString("return_msg");

        returnCode = jsonObject.getIntValue("return_code");

        return jsonObject;
    }


    public MvcResult getMvcResult(MultiValueMap map,String url) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .params(map)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(new ResultMatcher() {
                    @Override
                    public void match(MvcResult mvcResult) throws Exception {

                    }
                })
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        return result;

    }


    public int getReturnCode() {
        return returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }


}
