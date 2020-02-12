package cipher.console.oidc.controller;

import cipher.console.oidc.enums.ResultCode;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public Map<String,Object> sendBaseNormalMap(Map<String,Object> map){
        if (map==null){
            map= new HashMap<>();
        }
        map.put("return_code",ResultCode.SUCCESS.getCode());
        map.put("return_msg",ResultCode.SUCCESS.getMessage());
        return map;
    }

    public Map<String,Object> sendBaseNormalMap(){
        Map<String,Object> map = map= new HashMap<>();
        map.put("return_code",ResultCode.SUCCESS.getCode());
        map.put("return_msg",ResultCode.SUCCESS.getMessage());
        return map;
    }

    public Map<String,Object> sendBaseNormalMap(ResultCode result){
        Map<String,Object> map = map= new HashMap<>();
        map.put("return_code",result.getCode());
        map.put("return_msg",result.getMessage());
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




}
