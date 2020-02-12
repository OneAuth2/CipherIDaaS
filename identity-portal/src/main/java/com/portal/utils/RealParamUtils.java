package com.portal.utils;

import com.alibaba.fastjson.JSON;
import com.portal.commons.Constants;
import com.portal.domain.LusGetParamDomain;
import com.portal.domain.ParamInfo;
import com.portal.domain.RealParamDomain;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/7/22 9:26
 */
public final class RealParamUtils {

    /**
     * 根据传回的参数转换参数
     * @param url
     * @return
     */
    //根据xdsg返回的URL转换参数
    public static RealParamDomain getRealParam(String url) {
        GetParamUtil.UrlEntity parse = GetParamUtil.parse(url);
        parse.getParams();
        RealParamDomain realParamDomain = new RealParamDomain();
        realParamDomain.setAppKey(parse.getParams().get("appKey"));
        realParamDomain.setSfApp(parse.getParams().get("sfApp"));
        String ssoUri = parse.getParams().get("ssoUri");
        realParamDomain.setTeamIdList(parse.getParams().get("teamId"));
        realParamDomain.setToken(parse.getParams().get("token"));
        if(StringUtils.isNotEmpty(parse.getParams().get("sessionTime"))){
            realParamDomain.setSessionTime(Long.valueOf(parse.getParams().get("sessionTime")));
        }
        try {
            URLEncoder.encode(ssoUri,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        realParamDomain.setSsoUri(ssoUri);
        String getParam=parse.getParams().get("getParam");
        String ss="";
        try {
           ss= java.net.URLDecoder.decode(getParam, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(ss)){
            JSONArray array = JSONArray.fromObject(ss);
            List<ParamInfo> paramInfoList = JSONArray.toList(array, ParamInfo.class);// 过时方法
            List<LusGetParamDomain> list = new ArrayList<>();
            for(ParamInfo paramInfo:paramInfoList){
                list.add(new LusGetParamDomain(paramInfo.getKey(), paramInfo.getValue()));
            }
            realParamDomain.setGetParam(JSON.toJSONString(list));
        }
        return realParamDomain;
    }

    public static void main(String[] args) {
      //  String s = "http://192.168.1.20:8088?teamId=&sfApp=chandaolan&appKey=lR8G53&sessionTime=18000&ssoUri=%2FchandaoLogin&token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI5NDUyNDkzNWE0NmIxMWU5YjVhZjAwMTYzZTAwY2M2YSJ9.2_OGQ95Mp10IcLcqgEfBE6hpDVv3TSYOPsGWg-gqzww&getParam=%5B%7B%22key%22%3A%22cipherParam%22%2C%22value%22%3A%229B48FC6577FB94CF515713F6A74A1907EC24B14A6AC330D28E1A8E88AFEC4746D1DDF7D7C359BDFD77356972C3D1A402681522FDAF607B1696BA4695F5074A2542D53A062E4F3CC2E8328AF007659219E73C8EC271FABCB3D2C8AF05D36AE6FF%22%7D%2C%7B%22key%22%3A%22clientId%22%2C%22value%22%3A%221l4esJ%22%7D%2C%7B%22key%22%3A%22protocol%22%2C%22value%22%3A%22http%22%7D%2C%7B%22key%22%3A%22host%22%2C%22value%22%3A%22192.168.1.181%22%7D%2C%7B%22key%22%3A%22port%22%2C%22value%22%3A%228088%22%7D%5D";
        String s = "http://172.19.5.6:9999/cipher/getSsoLoginUrl?param=2BB8711CF189245799B0D8DEE156C1E0D26C3B80DB478B1FBCF07ABE2C7564B87FE70471BF06C5A61DFF1130A2A25B4421A8C7B87D089AA4E8DADB967ED5041376DE31118B36E1F10F4BB1E6C6EA0EF8702CFE2F5267CC4FBE1AE74E8B0980BA2A5B39529E1730ED690A625FDCBA40C15F41CDB94A00B94CF8A2A061DFA6B2EA&clientId=74d2M2";
        RealParamDomain realParamDomain = getRealParam(s);
        System.out.println(realParamDomain);
        Map<String,Object> map=new HashMap();
        map.put(Constants.RETURN_CODE, Constants.SUCCESS_CODE);
        map.put(Constants.DATA, realParamDomain);
        System.out.println("realParamDomain-------------------"+realParamDomain);
        System.out.println("map------"+JSON.toJSONString(map));

    }
}
