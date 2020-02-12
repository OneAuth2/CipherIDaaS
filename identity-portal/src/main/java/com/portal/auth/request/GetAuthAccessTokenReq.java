package com.portal.auth.request;


import com.portal.auth.response.GetAuthAccessTokenResp;
import com.portal.ctid.uri.RequestUrl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

//@RequestUrl(uri = "/getaccesstoken?clientId=HJFJMWPT4SAPUY3S&clientSecret=HJFJMWPT4SAPUY3S")
@RequestUrl(uri = "/getaccesstoken?clientId=HJFJMWPT4SAPUY3I&clientSecret=HJFJMWPT4SAPUY3I")
public class GetAuthAccessTokenReq extends GetCipherAuthReq implements GetRequest {

    public GetAuthAccessTokenReq() {
    }

    @Override
    public GetAuthAccessTokenResp request() {
        Map<String, Object> map = new HashMap<>();
        return getMethodRequest(map, GetAuthAccessTokenResp.class);
    }

    private static volatile Integer i = 0;

    public static void main(String[] args) {
        // System.out.println(new GetAuthAccessTokenReq().request());
        CountDownLatch countDownLatch=new CountDownLatch(10);

        for (int j = 0; j < 10; j++) {
            new Thread(() -> {
                for (int k = 0; k <= 100; k++) {
                    i++;
                }
            }).start();
            countDownLatch.countDown();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(i);


    }


}
