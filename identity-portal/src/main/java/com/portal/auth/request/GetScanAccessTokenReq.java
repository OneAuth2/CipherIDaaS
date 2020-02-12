package com.portal.auth.request;


import com.portal.auth.response.GetAuthAccessTokenResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/4/28 20:23
 */
@RequestUrl(uri = "/getaccesstoken")
public class GetScanAccessTokenReq extends GetCipherAuthReq implements GetRequest {

        public GetScanAccessTokenReq(String clientId ,String clientSecret) {
            super();
            addUrl("?clientId="+clientId+"&clientSecret="+clientSecret);
        }

        @Override
        public GetAuthAccessTokenResp request() {
            Map<String,Object> map = new HashMap<>();
            return getMethodRequest(map, GetAuthAccessTokenResp.class);
        }

        public static void main(String[] args){
        }


}
