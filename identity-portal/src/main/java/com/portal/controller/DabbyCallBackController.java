package com.portal.controller;


import com.google.gson.Gson;
import com.portal.commons.CacheKey;
import com.portal.model.DabbyUserInfoModel;
import com.portal.redis.RedisClient;
import edu.hziee.common.lang.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
* 大白扫码回调
*
* */
@Controller
@RequestMapping(value = "/dabby")
public class DabbyCallBackController {

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();


    @Deprecated
    @ResponseBody
    @PostMapping("/auth")
    public  Map<String,Object> callBack(HttpServletRequest request, HttpServletResponse response, @RequestBody String cert){
        System.out.println(cert);
        Map<String,Object> map = new HashMap<>();
        DabbyUserInfoModel userInfoModel =new Gson().fromJson(cert,DabbyUserInfoModel.class);
        if (userInfoModel == null || StringUtil.isBlank(userInfoModel.getId_num()) || StringUtil.isBlank(userInfoModel.getFull_name())){
            map.put("ret_code",1);
            map.put("error_msg","身份信息不完整!");
            return map;
        }
        redisClient.put(CacheKey.getUserLoginUuid(userInfoModel.getCert_token()),userInfoModel,7*60*1000);
        map.put("ret_code",0);
        map.put("error_msg","接收成功");
        return map;

    }

}
