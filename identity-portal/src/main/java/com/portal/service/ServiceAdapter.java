package com.portal.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.portal.controller.BaseController;
import com.portal.enums.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public interface ServiceAdapter {

    Map<String, Object> sendBaseNormalMap(Map<String, Object> map);

    Map<String, Object> sendBaseNormalMap();

    Map<String, Object> sendBaseNormalMap(ResultCode result);

    Map<String, Object> sendBaseSameMap(Map<String, Object> map);

    Map<String, Object> sendBaseNormalMap(Map<String, Object> map, ResultCode result);

    Map<String, Object> sendTheMap(Map<String, Object> map, int resultCode, String resultMsg);

    Map<String, Object> sendTheMap(int resultCode, String resultMsg);

    Map<String, Object> sendBaseErrorMap(Map<String, Object> map, ResultCode result);

    Map<String, Object> sendBaseErrorMap(ResultCode result);

    BaseController setMapParam(Map<String, Object> map, String key, Object value);

    Map<String, Object> removeParam(Map<String, Object> map, String key);

    boolean isEmpty(String... informations);

    public void sendResultMap(ResultCode resultCode, String key, String type);

    public  void sendReturnMap(ResultCode resultCode,
                                     String key,String value,String key1,String value1,
                                     String key2,String value2,String key3,String value3,String key4,String value4);

}
