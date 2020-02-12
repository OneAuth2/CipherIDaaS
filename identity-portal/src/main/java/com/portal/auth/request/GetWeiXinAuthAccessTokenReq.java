package com.portal.auth.request;

import com.portal.auth.response.WeixinScanAuthUser;
import com.portal.ctid.response.BaseResponse;
import com.portal.ctid.uri.RequestUrl;


import java.util.HashMap;
import java.util.Map;

/**
 * TODO:
 * create by liuying at 2019/9/28
 *
 * @author liuying
 * @since 2019/9/28 17:25
 */
@RequestUrl(uri = "/getaccesstoken")
public class GetWeiXinAuthAccessTokenReq   extends GetWeixinAuthReq implements GetRequest{


    public GetWeiXinAuthAccessTokenReq(String corpId ,String corpSecret)
    {
        super();
        addUrl("?corpid="+corpId+"&corpsecret="+corpSecret);
    }


    @Override
    public BaseResponse request() {
        return null;
    }
}
