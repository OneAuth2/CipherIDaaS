package com.portal.ctid.request;

import com.google.gson.Gson;
import com.portal.ctid.response.BaseResponse;
import com.portal.ctid.uri.RequestUrl;
import com.portal.utils.HttpRequest;
import com.sun.javafx.fxml.builder.URLBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class BaseRequest {

  //private static final String  BASE_URL = "http://68.56.0.203:8001/agentintranet";
   private static final String  BASE_URL = "https://api.auth.dabby.cn";

    public String requestUrl;

    public BaseRequest(String baseUrl){
        this.requestUrl = baseUrl+this.getClass().getAnnotation(RequestUrl.class).uri();
    }


    public BaseRequest(){
        this.requestUrl = BASE_URL +this.getClass().getAnnotation(RequestUrl.class).uri();
    }


    public void addUrl(String param) {
        requestUrl += param;
    }



    public String getMethodRequest(Map<String,Object> map){
        return HttpRequest.get(requestUrl).body();

    }

    public String postMethodRequest(Map<String,Object> map){
        return HttpRequest.post(requestUrl).formJson(map,HttpRequest.CONTENT_TYPE_JSON,true).body();
    }

    public <T extends BaseResponse> T getMethodRequest(Map<String,Object> map, Class<T> object){
        return new Gson().fromJson(getMethodRequest(map),object);
    }

    public <T extends BaseResponse> T postMethodRequest(Map<String,Object> map, Class<T> object){
        return new Gson().fromJson(postMethodRequest(map),object);
    }


    private String baseUrl;
    private StringBuilder builder;
    private String encoding;
    private boolean initProcessed;



    public BaseRequest(String baseUrl, String encoding) {
        this.initProcessed = false;
        this.baseUrl = baseUrl;
        this.encoding = encoding;
        this.builder = new StringBuilder(baseUrl);
    }


    public BaseRequest addParam(String name, String value) {
        if (value == null) {
            return this;
        } else {
            if (!this.initProcessed) {
                if (this.baseUrl.contains("?")) {
                    this.builder.append("&");
                } else {
                    this.builder.append("?");
                }

                this.initProcessed = true;
            } else {
                this.builder.append("&");
            }

            try {
                this.builder.append(name).append("=").append(URLEncoder.encode(value, this.encoding));
                return this;
            } catch (UnsupportedEncodingException var4) {
                var4.printStackTrace();;
            }
        }
        return this;
    }

    public String toString() {
        return this.builder.toString();
    }


}
