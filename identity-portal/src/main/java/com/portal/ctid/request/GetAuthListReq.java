package com.portal.ctid.request;


import com.portal.ctid.response.GetAuthListResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri = "/authhist")
public class GetAuthListReq extends BaseRequest implements GetRequest {


    private AuthorizInfo authorizInfo;

    private AuthHistQry authHistQry;

    public GetAuthListReq(String accessToken,String certToken){
        this.authorizInfo = new AuthorizInfo(accessToken);
        this.authHistQry = new AuthHistQry(certToken);

    }

    @Override
    public GetAuthListResp request() {
        Map<String,Object> map = new HashMap<>();
        map.put("authorizInfo",authorizInfo);
        map.put("authHistQry",authHistQry);
        return postMethodRequest(map, GetAuthListResp.class);
    }


    public class AuthorizInfo{
        String accessToken;

        public AuthorizInfo(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        @Override
        public String toString() {
            return "AuthorizInfo{" +
                    "accessToken='" + accessToken + '\'' +
                    '}';
        }
    }

    public class AuthHistQry{
        private String certToken;

        public AuthHistQry(String certToken) {
            this.certToken = certToken;
        }

        public String getCertToken() {
            return certToken;
        }

        public void setCertToken(String certToken) {
            this.certToken = certToken;
        }

        @Override
        public String toString() {
            return "AuthHistQry{" +
                    "certToken='" + certToken + '\'' +
                    '}';
        }
    }


}