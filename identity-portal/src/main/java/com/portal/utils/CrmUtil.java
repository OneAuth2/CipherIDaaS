package com.portal.utils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.PrintWriter;

public class CrmUtil {



    //目标应用的域名或ip
    private static final String SERVER_HOST = "wxtestbusiness.nabeluse.com";
    //    目标应用的端口
    private static final int SERVER_PORT = 5555;
    //    目标应用的协议
    private static final String SERVER_PROTOCO = "http";
    //    用户名密码的组装
    private static final String USERPWD = "test123@adtest123@ad:test123";


    public static boolean checkCrmLogin(String userName,String password,String loginUrl){
        DefaultHttpClient httpclient = new DefaultHttpClient();
        NTCredentials creds = new NTCredentials(userName+":"+password);
        httpclient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);
        String serverHost=ConstantsUrls.getServerHost(loginUrl);
        int serverPort=Integer.valueOf(ConstantsUrls.getServerPort(loginUrl));
        HttpHost target = new HttpHost(serverHost, serverPort, SERVER_PROTOCO);
        HttpGet httpget = new HttpGet("/NobelDev/main.aspx");
        HttpResponse response1 = null;
        PrintWriter out = null;
        try {
            response1 = httpclient.execute(target, httpget);
            int code=response1.getStatusLine().getStatusCode();
            System.out.println(code);
            if(code == 200){
                return true;
            }else{
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            httpclient.close();
            if (!ObjectUtils.isEmpty(out)) {
                out.close();
            }
        }
    }

    public static void main(String[] args) {
      /* boolean flag= checkCrmLogin("test123@ad","test123");
       System.out.println(flag);*/
        String reg = ".*\\/\\/([^\\/\\:]*).*";
        String str1 = "http://wxtestbusiness.nabeluse.com:5555";
        String host=ConstantsUrls.getServerHost(str1);
        String port=ConstantsUrls.getServerPort(str1);
        System.out.println (host);
        System.out.println (port);



    }

}
