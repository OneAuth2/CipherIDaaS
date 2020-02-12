package com.portal.auth.request;


import com.portal.auth.response.GetAuthAccessTokenResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:12
 */
@RequestUrl(uri = "/getaccesstoken")
public class GetDingAuthAccessTokenReq extends GetCipherAuthReq implements GetRequest {


        public GetDingAuthAccessTokenReq(String appId ,String redirectUrl) {
            super();
            addUrl("?appid="+appId+"&redirect_uri="+ redirectUrl);
        }

        @Override
        public GetAuthAccessTokenResp request() {
            Map<String,Object> map = new HashMap<>();
            return getMethodRequest(map, GetAuthAccessTokenResp.class);
        }

}
