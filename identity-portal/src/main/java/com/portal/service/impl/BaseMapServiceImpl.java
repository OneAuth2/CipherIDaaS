package com.portal.service.impl;

import com.portal.enums.ServiceResultMap;
import com.portal.service.BaseMapService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/7/19 10:51
 */
public class BaseMapServiceImpl extends BaseMapService {

    @Override
    public Map<String, Object> getErrorMap(ServiceResultMap resultMap) {
        Map<String,Object> map=new HashMap<>();
        map.put("return_code", resultMap.getCode());
        map.put("return_msg", resultMap.getMessage());
        return map;
    }

    @Override
    public Map<String, Object> getSuccessMap(ServiceResultMap resultMap) {
        Map<String,Object> map=new HashMap<>();
        map.put("return_code", resultMap.getCode());
        map.put("return_msg", resultMap.getMessage());
        return map;
    }

    @Override
    public BaseMapService add(Map map) {

        return this;
    }

    @Override
    public Map<String,Object> baseMapMsgAdd(Map<String,Object> map,String key, String value) {
        map.put(key,value);
        return map;
    }

    @Override
    public Map<String, Object> baseMapCodeAdd(Map<String,Object> map,String key, int value) {
        map.put(key,value);
        return map;
    }
}
