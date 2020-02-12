package com.portal.auth.request;


import com.portal.auth.response.GetCipherQrCodeResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri ="/qrcode/authreq" )
public class GetCipherQrCodeReq extends GetCipherAuthReq implements GetRequest {

    private String accessToken;

    private String authType;

    private int mode;

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

    public GetCipherQrCodeReq(String accessToken, String authType, int mode) {
        this.accessToken = accessToken;
        this.authType = authType;
        this.mode = mode;
    }

    @Override
    public GetCipherQrCodeResp request() {
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("authType",authType);
        map.put("mode",mode);
        GetCipherQrCodeResp resp = postMethodRequest(map, GetCipherQrCodeResp.class);
        return resp;
    }
}
