package com.portal.ctid.request;

import com.portal.ctid.response.GetAccessTokenResp;
import com.portal.ctid.uri.RequestUrl;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@RequestUrl(uri = "/getaccesstoken?clientId=695216d3ff65b20c&clientSecret=e296af43-e8e1-4405-84cd-246e7efdd032")
//@RequestUrl(uri = "/getaccesstoken?clientId=ee61428cc344ed76&clientSecret=1639b8fd-f296-4ecc-a29c-546cfca6064c")
public class GetAccessTokenReq extends BaseRequest implements GetRequest {

    public GetAccessTokenReq(){ }

    @Override
    public GetAccessTokenResp request() {
        Map<String,Object> map = new HashMap<>();
        return getMethodRequest(map,GetAccessTokenResp.class);
    }

}
