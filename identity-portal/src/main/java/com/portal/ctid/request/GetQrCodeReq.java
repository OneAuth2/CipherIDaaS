package com.portal.ctid.request;

import com.portal.ctid.response.GetQrCodeResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri ="/authreq" )
public class GetQrCodeReq extends BaseRequest implements GetRequest {

    private String accessToken;

    private String authType;

    private int mode;

    private String idInfo;

    private String scopeLimit;

    private String exp;

    private String fullName;

    private String idNum;

    private double longitude;

    private double latitude;

    private long distanceLimit;

    private String expType;

    private long expSec;


    public GetQrCodeReq(String accessToken,String authType,int mode){
        this.accessToken = accessToken;
        this.authType = authType;
        this.mode = mode;
    }

    @Override
    public GetQrCodeResp request() {
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("authType",authType);
        map.put("mode",mode);
        GetQrCodeResp resp = postMethodRequest(map, GetQrCodeResp.class);
        return resp;
    }




}
