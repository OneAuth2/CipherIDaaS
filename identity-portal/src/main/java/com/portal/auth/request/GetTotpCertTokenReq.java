package com.portal.auth.request;


import com.portal.auth.response.GetTotpCertTokenResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri ="/totp/authreq" )
public class GetTotpCertTokenReq extends GetCipherAuthReq implements GetRequest {

    private String accessToken;
    private String authType;
    private int  mode;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public GetTotpCertTokenReq(String accessToken, String authType, int mode) {
        this.accessToken = accessToken;
        this.authType = authType;
        this.mode = mode;
    }

    @Override
    public GetTotpCertTokenResp request() {
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("authType",authType);
        map.put("mode",mode);
        GetTotpCertTokenResp resp = postMethodRequest(map, GetTotpCertTokenResp.class);
        return resp;
    }
}
