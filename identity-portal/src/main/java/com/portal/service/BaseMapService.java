package com.portal.service;

import com.portal.enums.ServiceResultMap;

import java.util.Map;

/**
 * @Author: 安歌
 * @Date: 2019/7/19 10:52
 */
public abstract class BaseMapService {


    /**
     * 构建私有返回错误方法
     * @param resultMap
     * @return
     */
   public abstract Map<String,Object> getErrorMap(ServiceResultMap resultMap);

    /**
     * 构建成功的返回方法
     * @param resultMap
     * @return
     */
    public abstract Map<String,Object> getSuccessMap(ServiceResultMap resultMap);

    /**
     * 构建循环添加
     * @param map
     * @return
     */
    public abstract BaseMapService add(Map map);

    /**
     * 构建添加多条MSg的方法
     */
    public abstract Map<String,Object> baseMapMsgAdd(Map<String,Object> map,String key ,String value);

    /**
     * 构建自定义返回code
     * @param key
     * @param value
     * @return
     */
    public abstract Map<String,Object> baseMapCodeAdd(Map<String,Object> map ,String key,int value);

    /**
     * 清理map中的数据
     */
    public  void cleanMap(Map<String,Object> map){
       map.clear();
    }
}
