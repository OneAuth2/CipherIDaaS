package com.portal.auth.request;


import com.portal.ctid.request.BaseRequest;

/**
 * @Author: TK
 * @Date: 2019/4/28 21:10
 */
public class GetDingAuthReq extends BaseRequest {


    private  final  static String  BASE_URL = "http://101.132.145.69:6114";

    public GetDingAuthReq() {
        super(BASE_URL);
    }
}
