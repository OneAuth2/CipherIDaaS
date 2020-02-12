package cipher.console.oidc.util;


import cipher.console.oidc.common.HttpKey;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {


    /**
     * 从另一台服务器获取json数据
     *
     * @param url
     * @return
     */
    public static String getJsonFromServer(String url) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
//        httpPost.addHeader("Content-type", "application/x-www-form-urlencoded");
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = closeableHttpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity());
            } else {
                String err = response.getStatusLine().getStatusCode() + "";
                System.err.println("网络请求发生错误:" + err);
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
     * 发送post请求组
     *
     * @param url
     * @param list
     * @param charset
     * @return
     */
    public static Map<String, Object> doPost(String url, List<NameValuePair> list, Map<String, String> headerMap, String cookie, String charset, String queryStr) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            if (!CollectionUtils.isEmpty(headerMap)) {
                for (Map.Entry entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }


            httpPost.addHeader("Cookie", cookie);
            if (!CollectionUtils.isEmpty(list)) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpClient.execute(httpPost);
            if (!ObjectUtils.isEmpty(response)) {
                Header[] headers = response.getAllHeaders();
                resMap.put(HttpKey.HEADERS, headers);
                resMap.put(HttpKey.STATUS_CODE, response.getStatusLine().getStatusCode());
                HttpEntity resEntity = response.getEntity();
                if (!ObjectUtils.isEmpty(resEntity)) {
                    if (resEntity.getContentType() != null) {
                        if (!StringUtils.isEmpty(resEntity.getContentType().getValue())) {
                            resMap.put(HttpKey.PAGETYPE, resEntity.getContentType().getValue());
                        }
                    }
                    result = EntityUtils.toString(resEntity, charset);
                    resMap.put(HttpKey.RES, result);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(httpPost)) {
                httpPost.releaseConnection();
            }
        }
        return resMap;
    }

    public static Map<String, Object> doPost(String url, List<NameValuePair> list, Map<String, String> headerMap) {
        return doPost(url, list, headerMap, null, null, null);
    }

    /**
     * 发送get请求
     *
     * @param url     链接地址
     * @param charset 字符编码，若为null则默认utf-8
     * @return
     */
    public static Map<String, Object> doGet(String url, Map<String, String> headerMap, String cookie, String charset, String queryStr, String referer) {
        Map<String, Object> resMap = new HashMap<>();
        if (org.apache.commons.lang.StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + "?" + queryStr);
            }

            if (org.apache.commons.lang.StringUtils.isNotEmpty(referer)) {
                httpGet.addHeader("Referer", referer);
            }
            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpGet.addHeader("Cookie", cookie);
            }

            if (!CollectionUtils.isEmpty(headerMap)) {
                for (Map.Entry entry : headerMap.entrySet()) {
                    httpGet.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }


            HttpResponse response = httpClient.execute(httpGet);

            resMap.put(HttpKey.HEADERS, response.getAllHeaders());
            resMap.put(HttpKey.STATUS_CODE, response.getStatusLine().getStatusCode());

            if (!ObjectUtils.isEmpty(response)) {
                HttpEntity resEntity = response.getEntity();
                if (!ObjectUtils.isEmpty(resEntity)) {

                    if (resEntity.getContentType() != null) {
                        if (!StringUtils.isEmpty(resEntity.getContentType().getValue())) {
                            resMap.put(HttpKey.PAGETYPE, resEntity.getContentType().getValue());
                        }
                    }

                    result = EntityUtils.toString(resEntity, charset);
                    resMap.put(HttpKey.RES, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(httpGet)) {
                httpGet.releaseConnection();
            }
        }
        return resMap;
    }


    public static Map<String, Object> doGet(String url, Map<String, String> headerMap) {
        return doGet(url, headerMap, null, null, null, null);
    }

    public static Map<String, Object> doPostWithPayload(String url, String payload, Map<String, String> headerMap, String queryStr) {

        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new DefaultHttpClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            if (!CollectionUtils.isEmpty(headerMap)) {
                for (Map.Entry entry : headerMap.entrySet()) {
                    httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }

            if (org.apache.commons.lang.StringUtils.isNotEmpty(payload)) {
                httpPost.setEntity(new StringEntity(payload));
            }

            HttpResponse response = httpClient.execute(httpPost);
            if (!ObjectUtils.isEmpty(response)) {
                Header[] headers = response.getAllHeaders();
                resMap.put(HttpKey.HEADERS, headers);
                resMap.put(HttpKey.STATUS_CODE, response.getStatusLine().getStatusCode());
                HttpEntity resEntity = response.getEntity();
                if (!ObjectUtils.isEmpty(resEntity)) {
                    if (resEntity.getContentType() != null) {
                        if (!StringUtils.isEmpty(resEntity.getContentType().getValue())) {
                            resMap.put(HttpKey.PAGETYPE, resEntity.getContentType().getValue());
                        }
                    }
                    result = EntityUtils.toString(resEntity, "UTF-8");
                    resMap.put(HttpKey.RES, result);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (!ObjectUtils.isEmpty(httpPost)) {
                httpPost.releaseConnection();
            }
        }
        return resMap;
    }


    public static Map<String, Object> doPostWithPayload(String url, String payload, Map<String, String> headerMap) {
        return doPostWithPayload(url, payload, headerMap, null);
    }

}
