package cipher.console.oidc.util;


import cipher.console.oidc.common.HttpKey;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/8/3 15:28
 */

public class HttpsClientUtils {

    /**
     * 发送post请求组
     *
     * @param url
     * @param list
     * @param charset
     * @return
     */
    public static Map<String, Object> doPost(String url, List<NameValuePair> list, String cookie, String charset, String referer, String queryStr) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
            httpPost.addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");

            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpPost.addHeader("Cookie", cookie);
            }
            if (!org.apache.commons.lang.StringUtils.isEmpty(referer)) {
                httpPost.addHeader("Referer", referer);
            }
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


    public static Map<String, Object> doPostWithNoHeader(String url, List<NameValuePair> list, String cookie, String charset, String referer, String queryStr) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpPost.addHeader("Cookie", cookie);
            }
            if (!org.apache.commons.lang.StringUtils.isEmpty(referer)) {
                httpPost.addHeader("Referer", referer);
            }
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


    public static Map<String, Object> doPostWithNoHeader(String url, String queryStr) {
        return doPostWithNoHeader(url, null, null, null, null, queryStr);
    }


    /**
     * 发送get请求
     *
     * @param url     链接地址
     * @param charset 字符编码，若为null则默认utf-8
     * @return
     */
    public static Map<String, Object> doGet(String url, String cookie, String charset, String queryStr) {
        Map<String, Object> resMap = new HashMap<>();
        if (org.apache.commons.lang.StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + "?" + queryStr);
            }
            httpGet.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.addHeader("Accept-Language", "zh-EN,zh;q=0.9,en;q=0.8,la;q=0.7");
            httpGet.addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            httpGet.addHeader("X-Requested-With", " XMLHttpRequest");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpGet.addHeader("Cookie", cookie);
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


    /**
     * 发送get请求
     *
     * @param url     链接地址
     * @param charset 字符编码，若为null则默认utf-8
     * @return
     */
    public static Map<String, Object> doGetWithHeader(String url, String cookie, Map<String, String> headers, String charset, String queryStr) {
        Map<String, Object> resMap = new HashMap<>();
        if (org.apache.commons.lang.StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + "?" + queryStr);
            }

            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.102 Safari/537.36");

            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpGet.addHeader("Cookie", cookie);
            }

            if (!CollectionUtils.isEmpty(headers)) {
                for (Map.Entry entry : headers.entrySet()) {
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


    public static Map<String, Object> doGetWithNoHeader(String url, String cookie, String charset, String queryStr) {
        Map<String, Object> resMap = new HashMap<>();
        if (org.apache.commons.lang.StringUtils.isEmpty(charset)) {
            charset = "utf-8";
        }
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpGet = new HttpGet(url);
            } else {
                httpGet = new HttpGet(url + "?" + queryStr);
            }
//            httpGet.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
//            httpGet.addHeader("Accept-Encoding", "gzip, deflate, br");
//            httpGet.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
//            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
//            httpGet.addHeader("X-Requested-With", " XMLHttpRequest");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpGet.addHeader("Cookie", cookie);
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

                    result = EntityUtils.toString(resEntity);
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


    /**
     * 带payload串的post请求
     *
     * @param url      地址
     * @param list     post参数
     * @param payload  payload参数
     * @param cookie   cookie
     * @param charset  编码
     * @param referer  地址来源
     * @param queryStr 附加在链接上的请求参数
     * @return
     */
    public static Map<String, Object> doPostWithPayload(String url, List<NameValuePair> list, String payload, String cookie, String charset, String referer, String queryStr) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,la;q=0.7");
            httpPost.addHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpPost.addHeader("Cookie", cookie);
            }

            if (!org.apache.commons.lang.StringUtils.isEmpty(referer)) {
                httpPost.addHeader("Referer", referer);
            }

            if (!CollectionUtils.isEmpty(list)) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }

            if (org.apache.commons.lang.StringUtils.isNotEmpty(payload)) {
                HttpEntity entity = new StringEntity(payload);
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

    public static Map<String, Object> doPostWithPayload(String url, String payload) {
        return doPostWithPayload(url, null, payload, null, null, null, null);
    }


    /**
     * 带payload串的post请求
     *
     * @param url      地址
     * @param list     post参数
     * @param payload  payload参数
     * @param cookie   cookie
     * @param charset  编码
     * @param referer  地址来源
     * @param queryStr 附加在链接上的请求参数
     * @return
     */
    public static Map<String, Object> doPostWithPayloadNoHeader(String url, Map<String, String> customHeaders, List<NameValuePair> list, String payload, String cookie, String charset, String referer, String queryStr) {

        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }
        Map<String, Object> resMap = new HashMap<>();
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            if (StringUtils.isEmpty(queryStr)) {
                httpPost = new HttpPost(url);
            } else {
                httpPost = new HttpPost(url + "?" + queryStr);
            }

            if (!CollectionUtils.isEmpty(customHeaders)) {
                for (Map.Entry entry : customHeaders.entrySet()) {
                    httpPost.addHeader(entry.getKey().toString(), entry.getValue().toString());
                }
            }
//            httpPost.addHeader("content-type","application/json");

            if (org.apache.commons.lang.StringUtils.isNotEmpty(cookie)) {
                httpPost.addHeader("Cookie", cookie);
            }

            if (!org.apache.commons.lang.StringUtils.isEmpty(referer)) {
                httpPost.addHeader("Referer", referer);
            }

            if (!CollectionUtils.isEmpty(list)) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }

            if (org.apache.commons.lang.StringUtils.isNotEmpty(payload)) {
                HttpEntity entity = new StringEntity(payload);
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

    public static Map<String, Object> doPostWithPayloadNoHeader(String url, String payload, Map<String, String> customHeaders) {
        return doPostWithPayloadNoHeader(url, customHeaders,null, payload, null, null, null, null);
    }


}
