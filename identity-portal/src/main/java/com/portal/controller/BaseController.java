package com.portal.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.portal.enums.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.RETURN_CODE;
import static com.portal.commons.Constants.RETURN_MSG;
import static com.portal.enums.ResultCode.SUCCESS;


public class BaseController {

    @Autowired
    private HttpServletResponse response;

    public Map<String,Object> sendBaseNormalMap(Map<String,Object> map){
        if (map==null){
            map= new HashMap<>();
        }
        map.put("return_code", SUCCESS.getCode());
        map.put("return_msg", SUCCESS.getMessage());
        return map;
    }
    public Map<String,Object> sendBaseNormalMap(){
        Map<String,Object> map = map= new HashMap<>();
        map.put("return_code", SUCCESS.getCode());
        map.put("return_msg", SUCCESS.getMessage());
        return map;
    }

    public Map<String,Object> sendBaseNormalMap(ResultCode result){
        Map<String,Object> map = map= new HashMap<>();
        map.put("return_code",result.getCode());
        map.put("return_msg",result.getMessage());
        return map;
    }

    public Map<String ,Object> sendBaseSameMap(Map<String ,Object> map){
        return map;
    }

    public Map<String,Object> sendBaseNormalMap(Map<String,Object> map,ResultCode result){
        if (map==null){
            map= new HashMap<>();
        }
        map.put("return_code",result.getCode());
        map.put("return_msg",result.getMessage());
        return map;
    }

    public Map<String,Object> sendTheMap(Map<String,Object> map,int resultCode,String resultMsg){
        map = map == null?new HashMap<>():map;
        map.put("return_code",resultCode);
        map.put("return_msg",resultMsg);
        return map;
    }

    public Map<String,Object> sendTheMap(int resultCode,String resultMsg){
        Map<String,Object> map = new HashMap<>();
        map.put("return_code",resultCode);
        map.put("return_msg",resultMsg);
        return map;
    }

    public Map<String,Object> sendBaseErrorMap(Map<String,Object> map, ResultCode result){
        if (map==null){
            map= new HashMap<>();
        }
        map.put("return_code",result.getCode());
        map.put("return_msg",result.getMessage());
        return map;
    }

    public Map<String,Object> sendBaseErrorMap(ResultCode result){
        Map<String,Object> map = map= new HashMap<>();
        map.put("return_code",result.getCode());
        map.put("return_msg",result.getMessage());
        return map;
    }


    public void sendJson(Map<String,Object> map) throws IOException {
       PrintWriter printWriter= response.getWriter();
        JSONObject jsonObject=new JSONObject(map);
       printWriter.write(jsonObject.toJSONString());
       printWriter.flush();
       printWriter.close();
    }

    public BaseController setMapParam(Map<String,Object> map,String key,Object value){
        if (map == null){
            map = new HashMap<>();
        }
        map.put(key,value);
        return this;
    }

    public Map<String,Object> removeParam(Map<String,Object> map,String key){
        if (map == null){
            map = new HashMap<>();
        }
        map.remove(key);
        return map;
    }

    public boolean isEmpty(String ... informations){
        for (String info:informations){
            if (StringUtils.isEmpty(info)){
                return true;
            }
        }
        return false;
    }



    public  void sendResultMap(ResultCode resultCode, String key, String value){
        if (resultCode==null){
            return;
        }

        //定义map集合并向其中添加值
        Map<String,Object> map=new HashMap<>();
        map.put(RETURN_CODE,resultCode.getCode());
        map.put(RETURN_MSG,resultCode.getMessage());
        map.put(key,value);
        //定义输出流 并将错误结果返回给前端
        PrintWriter out=null;
        try {
            // response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String s = JSON.toJSONString(map);
            out.write(s);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                out.close();
            }
        }
    }



    public  void sendReturnMap( ResultCode resultCode,
                                     String key,String value,String key1,String value1,
                                     String key2,String value2,String key3,String value3,String key4,String value4){
        if (resultCode==null){
            return;
        }

        //定义map集合并向其中添加值
        Map<String,Object> map=new HashMap<>();
        map.put(RETURN_CODE,resultCode.getCode());
        map.put(RETURN_MSG,resultCode.getMessage());
        map.put(key,value);
        if (org.apache.commons.lang.StringUtils.isEmpty(value1)){
            map.put(key1,"");
        }else {
            map.put(key1,value1);
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(value2)){
            map.put(key2,"");
        }else {
            map.put(key2,value2);
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(value3)){
            map.put(key3,"");
        }else {
            map.put(key3,value3);
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(value4)){
            map.put(key4,"");
        }else {
            map.put(key4,value4);
        }
        //定义输出流 并将错误结果返回给前端
        PrintWriter out=null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=UTF-8");
            out = response.getWriter();
            String s = JSON.toJSONString(map);
            out.write(s);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (out!=null){
                out.close();
            }
        }
    }
}
