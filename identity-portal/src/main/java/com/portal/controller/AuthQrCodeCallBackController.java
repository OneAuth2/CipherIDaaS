package com.portal.controller;


import com.portal.auth.response.GetQrCodeCallBackResp;
import com.portal.commons.CacheKey;
import com.portal.redis.RedisClient;
import org.jsoup.helper.StringUtil;
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


/*
* 赛赋认证扫码回调
*
* */
@Controller
@RequestMapping(value = "/cipher/authQrCode")
public class AuthQrCodeCallBackController {

    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();

    @ResponseBody
    @PostMapping("/callback")
    public Map<String,Object> callBack(HttpServletRequest request, HttpServletResponse response, @RequestBody GetQrCodeCallBackResp userInfoModel){
        Map<String,Object> map = new HashMap<>();
        System.out.println("userInfoModel:"+userInfoModel);
        if (userInfoModel == null || StringUtil.isBlank(String.valueOf(userInfoModel.getUuid()))){
            map.put("retCode",1);
            map.put("errorMsg","身份信息不完整!");
            return map;
        }
        redisClient.put(CacheKey.getCacheKeyCipherAuthQrcodeUuid(userInfoModel.getCertToken()),userInfoModel,7*60*1000);
        map.put("retCode",0);
        map.put("errorMsg","接收成功");

        return map;
    }

}
