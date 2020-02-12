package com.portal.utils;

import com.alibaba.fastjson.JSONObject;
import com.portal.domain.XdsgReqInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/5/16 11:41
 */
public class HttpUtils {

    private static final Logger LOGGER= LoggerFactory.getLogger(HttpUtils.class);

    /**
     *从另一台服务器获取json数据
     * @param url
     * @param nameValuePairs
     * @return
     */
    public static String getJsonFromServer(String url, List<NameValuePair> nameValuePairs) {
        LOGGER.info("进入网络请求url:"+url);
        HttpClientBuilder httpClientBuilder=HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient=httpClientBuilder.build();
        HttpPost httpPost=new HttpPost(url);
        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            HttpResponse response = closeableHttpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                /*读返回数据*/
                return EntityUtils.toString(response.getEntity());
            } else {
                String err = response.getStatusLine().getStatusCode()+"";
                LOGGER.error("发送HTTP请求失败:"+err);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /*
	 * 处理https GET/POST请求 请求地址、请求方法、参数
	 */
    public static String doHttpsRequest(String requestUrl,String outputStr) {
        StringBuffer buffer = null;
        try {
            // 创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL");
            TrustManager[] tm = { new MyX509TrustManager() };
            // 初始化
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true); // 允许输出流，即允许上传
            conn.setDoInput(true); // 允许输入流，即允许下载
            conn.setUseCaches(false); // 不使用缓冲

                conn.setRequestMethod("GET");

            // 设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            // conn.connect();
            // 往服务器端写内容
            if (StringUtils.isNotBlank(outputStr)) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.flush();
                os.close();
            }

            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println(buffer);
        return buffer.toString();
    }

    public static class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }


    public static List<NameValuePair> postPwdParam(XdsgReqInfo xdsgReqInfo) {
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("subName", xdsgReqInfo.getSubName()));
        nameValuePairList.add(new BasicNameValuePair("subPwd", xdsgReqInfo.getSubPwd()));
        nameValuePairList.add(new BasicNameValuePair("appKey", xdsgReqInfo.getAppKey()));
        nameValuePairList.add(new BasicNameValuePair("userName", xdsgReqInfo.getUserName()));
        nameValuePairList.add(new BasicNameValuePair("apiDsgUrl", xdsgReqInfo.getApiDsgUrl()));
        nameValuePairList.add(new BasicNameValuePair("teamIds", xdsgReqInfo.getTeamIds()));
        nameValuePairList.add(new BasicNameValuePair("sign", xdsgReqInfo.getSign()));
        nameValuePairList.add(new BasicNameValuePair("app_session", xdsgReqInfo.getAppSession()));
        nameValuePairList.add(new BasicNameValuePair("app_session_max_age", xdsgReqInfo.getAppSessionMaxAge()));
        nameValuePairList.add(new BasicNameValuePair("app_token", xdsgReqInfo.getToken()));
        nameValuePairList.add(new BasicNameValuePair("config", xdsgReqInfo.getConfig()));
        nameValuePairList.add(new BasicNameValuePair("xdsgUrl", xdsgReqInfo.getXdsgUrl()));
        return nameValuePairList;
    }

    public static void main(String[] args) {
        sendGet("http://192.168.1.190:9090/oauth/authorize","response_type=code&client_id=acme&redirect_uri=http://192.168.1.158:8888/redirect_uri");
        String result=doHttpsRequest("https://api.exmail.qq.com/cgi-bin/gettoken","corpid=wmcffc3b3a06fd2c8c&corpsecret=wleTmYpoRY-893YLiTRtDxrQPq25sMC9TDfFb9wDwN1StXKfDBQzvD58Y_xc_L3r");
        JSONObject jsonResult= JSONObject.parseObject(result);
        String access_token= (String) jsonResult.get("access_token");
        doHttpsRequest("https://api.exmail.qq.com/cgi-bin/service/get_login_url","access_token="+access_token+"&userid=Demo@cipherchina.com");
    }

}
