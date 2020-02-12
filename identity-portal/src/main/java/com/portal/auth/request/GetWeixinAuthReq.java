package com.portal.auth.request;


import com.portal.ctid.request.BaseRequest;

public class GetWeixinAuthReq extends BaseRequest {

    private  final  static String  ACCESS_BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";


    public GetWeixinAuthReq(){
        super(ACCESS_BASE_URL);
    }


}
