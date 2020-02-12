package com.portal.utils;

import com.alibaba.fastjson.JSON;
import com.portal.enums.ResultCode;
import com.portal.enums.ServiceResultMap;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.RETURN_CODE;
import static com.portal.commons.Constants.RETURN_MSG;

/**
 * @Author: TK
 * @Date: 2019/7/4 17:43
 */
public final class ResponseUtils {

    /**
     * 定义返回错误信息
     * create by 安歌
     * create time 2019年7月22日10:55:42
     * @param response
     * @param resultCode
     */
    public static void sendResultMap(HttpServletResponse response, ResultCode resultCode){

        //入参校验
        if (response ==null || resultCode==null){
            return;
        }

        //定义map集合并向其中添加值
        Map<String,Object> map=new HashMap<>();
        map.put(RETURN_CODE,resultCode.getCode());
        map.put(RETURN_MSG,resultCode.getMessage());

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

    /**
     * 定制化返回错误信息
     * create by 安歌
     * create time 2019年7月23日09:09:30
     * @param response
     * @param resultCode
     * @param key
     * @param value
     */
    public static void sendResultMap(HttpServletResponse response, ResultCode resultCode,String key,int value){
//入参校验
        if (response ==null || resultCode==null){
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

    public static void sendResultMap(HttpServletResponse response, ResultCode resultCode,
                                     String key,int value,String key1,String value1,
                                     String key2,String value2,String key3,String value3,String key4,String value4){
//入参校验
        if (response ==null || resultCode==null){
            return;
        }

        //定义map集合并向其中添加值
        Map<String,Object> map=new HashMap<>();
        map.put(RETURN_CODE,resultCode.getCode());
        map.put(RETURN_MSG,resultCode.getMessage());
        map.put(key,value);
        if (StringUtils.isEmpty(value1)){
            map.put(key1,"");
        }else {
            map.put(key1,value1);
        }
        if (StringUtils.isEmpty(value2)){
            map.put(key2,"");
        }else {
            map.put(key2,value2);
        }
        if (StringUtils.isEmpty(value3)){
            map.put(key3,"");
        }else {
            map.put(key3,value3);
        }
        if (StringUtils.isEmpty(value4)){
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

    /**
     * 重载定义返回错误信息
     * creareBy 安歌
     * create time 2019年7月22日10:55:10
     * @param response
     * @param resultCode
     */
    public static void sendResultMap(HttpServletResponse response, ServiceResultMap resultCode){

        //入参校验
        if (response ==null || resultCode==null){
            return;
        }

        //定义map集合并向其中添加值
        Map<String,Object> map=new HashMap<>();
        map.put(RETURN_CODE,resultCode.getCode());
        map.put(RETURN_MSG,resultCode.getMessage());

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
    /**
     * 重载定义返回错误信息
     * creareBy 安歌
     * create time 2019年7月22日10:55:10
     * @param response
     * @param
     */
    public static void sendResultMap(HttpServletResponse response, Map<String,Object> map){

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
}
