package cipher.console.oidc.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * author: madfeng
 * date: 2014-11-05
 */
public class WpsHttpClientUtil {

    private final static Logger logger = LoggerFactory.getLogger(WpsHttpClientUtil.class);

    public final static int TIMEOUT = 5000;

    private static PoolingHttpClientConnectionManager connManager = null;
    private static CloseableHttpClient httpClient = null;

    static {

        connManager = new PoolingHttpClientConnectionManager();
        httpClient = HttpClients.custom().setConnectionManager(connManager).build();
        connManager.setMaxTotal(300);
        connManager.setDefaultMaxPerRoute(50);

    }

    private static RequestConfig buildRequestConfig(int soTimeout, int connectTimeout, int connectionRequestTimeout) {
        return RequestConfig.custom()
                .setSocketTimeout(soTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).build();
    }

    private static RequestConfig buildRequestConfig(int soTimeout, int connectTimeout) {
        return buildRequestConfig(soTimeout, connectTimeout, TIMEOUT + 1000);
    }

    private static RequestConfig buildRequestConfig() {
        return buildRequestConfig(TIMEOUT, TIMEOUT, TIMEOUT);
    }

    private static String handleResponse(HttpRequestBase request) throws Exception {
        String responseString = null;
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            try {
                if (entity != null && status == HttpStatus.SC_OK) {
                    return EntityUtils.toString(entity, Consts.UTF_8);
                }
            } finally {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                }
            }
        }
        return responseString;

    }

    public static String doGet(String url, Map<String, String> headers, int soTimeout, int connectTimeout) {
        String responseString = null;
        HttpGet get = new HttpGet(url);
        try {
            get.setConfig(buildRequestConfig(soTimeout, connectTimeout));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }
            responseString = handleResponse(get);
        } catch (SocketTimeoutException e) {
            logger.error("http get SocketTimeoutException", e);
        } catch (Exception e) {
            logger.error("http get Exception", e);
        } finally {
            get.releaseConnection();
        }
        return responseString;
    }

    /*
     * add by sjc 2018-07-31
     */
    public static String doPut(String url, Map<String, String> headers, int soTimeout, int connectTimeout) {
        String responseString = null;
        HttpPut putget = new HttpPut(url);
        try {
            putget.setConfig(buildRequestConfig(soTimeout, connectTimeout));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    putget.setHeader(entry.getKey(), entry.getValue());
                }
            }
            responseString = handleResponse(putget);
        } catch (SocketTimeoutException e) {
            logger.error("http get SocketTimeoutException", e);
        } catch (Exception e) {
            logger.error("http get Exception", e);
        } finally {
            putget.releaseConnection();
        }
        return responseString;
    }

    
    public static String doGet(String url, int soTimeout, int connectTimeout) {
        return doGet(url, null, soTimeout, connectTimeout);
    }

    public static String doGet(String url, Map<String, String> headers) {
        return doGet(url, headers, TIMEOUT, TIMEOUT);
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, String postContent, int soTimeout, int connectTimeout) {
        String result = null;
        HttpPost post = new HttpPost(url);
        try {
            RequestConfig requestConfig = buildRequestConfig(soTimeout, connectTimeout);

            post.setConfig(requestConfig);

            post.setEntity(new StringEntity(postContent, Consts.UTF_8));

            result = handleResponse(post);

        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException,{}", e.getMessage());
        } catch (Exception e) {
            logger.error("Exception,{}", e.getMessage());
        } finally {
            post.releaseConnection();
        }
        return result;

    }

    public static String doPost(String url, String postContent) {
        return doPost(url, postContent, TIMEOUT, TIMEOUT);
    }


    public static String doPost(String url, Map<String, String> headers
            , Map<String, String> params, int soTimeout, int connectTimeout) {

        String responseContent = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            httpPost.setConfig(buildRequestConfig(soTimeout, connectTimeout));
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            HttpEntity httpEntity=new UrlEncodedFormEntity(formParams, Consts.UTF_8);

            httpPost.setEntity(httpEntity);

            responseContent = handleResponse(httpPost);

        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseContent;

    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> params) {
        return doPost(url, headers, params, TIMEOUT, TIMEOUT);
    }

    public static String doPost(String url, Map<String, String> params) {
        return WpsHttpClientUtil.doPost(url, null, params);
    }

    public static String doPost(String url, Map<String, String> params, int soTimeout, int connectTimeout) {
        return WpsHttpClientUtil.doPost(url, null, params, soTimeout, connectTimeout);
    }

    public static List<Cookie> getPostCookie(String url, Map<String, String> headers, Map<String, String> params,String parameters) {


        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpClientContext context = HttpClientContext.create();

        HttpPost httpPost = new HttpPost(url);
        try {
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
           
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
           // httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8)); // modify by sjc
            httpPost.setEntity(new StringEntity(parameters, Consts.UTF_8)); // add by sjc
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httpPost,context);
                //---
                System.out.println("---------------begin-------------------------");  
                HttpEntity entity = response.getEntity();  
                System.out.println(response.getStatusLine());  
                if (entity != null) {  
                    System.out.println("Response content length: " + entity.getContentLength());  
                    System.out.println(EntityUtils.toString(entity));  
                    EntityUtils.consume(entity);  
                }  
                
                System.out.println("----------------end------------------------");
                //---
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                    CookieStore cookieStore = context.getCookieStore();
                    if (cookieStore != null) {
                        return cookieStore.getCookies();
                    }
                }
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                    }
                }
            }

        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;

    }
    
    ///==begin add by sjc 2017��5��24��
    public static String getPostCookie(String url, Map<String, String> headers,String parameters) {


        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpClientContext context = HttpClientContext.create();

        HttpPost httpPost = new HttpPost(url);
        try {
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    httpPost.setHeader(entry.getKey(), entry.getValue());
                }
            }
           
           // httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8)); // modify by sjc
            httpPost.setEntity(new StringEntity(parameters, Consts.UTF_8)); // add by sjc
            CloseableHttpResponse response = null;
            String res=null;
            try {
                response = httpclient.execute(httpPost,context);
              
                int status = response.getStatusLine().getStatusCode();
                if (status == HttpStatus.SC_OK) {
                	  //---
                    System.out.println("---------------begin-------------------------");  
                    HttpEntity entity = response.getEntity();  
                    System.out.println(response.getStatusLine());  
                    if (entity != null) {  
                        System.out.println("Response content length: " + entity.getContentLength());  
                        res=EntityUtils.toString(entity);
                        System.out.println(res);  
                        EntityUtils.consume(entity);  
                    }  
                    System.out.println("----------------end------------------------");
                    return res;
                    //---
                }else{
                	System.out.println("====="+EntityUtils.toString(response.getEntity()));
                }
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                    }
                }
            }

        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException", e);
        } catch (Exception e) {
            logger.error("Exception", e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;

    }
    ///==end add by sjc 2017��5��24��

    public static String doDelete(String url, int soTimeout, int connectTimeout) {
        String responseString = null;
        HttpDelete httpDelete = new HttpDelete(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(TIMEOUT)
                    .setConnectTimeout(TIMEOUT)
                    .setConnectionRequestTimeout(TIMEOUT).build();
            httpDelete.setConfig(requestConfig);

            responseString = handleResponse(httpDelete);

        } catch (SocketTimeoutException e) {
            logger.error("http get SocketTimeoutException", e);
        } catch (Exception e) {
            logger.error("http get Exception", e);
        } finally {
            httpDelete.releaseConnection();
        }
        return responseString;
    }

    public static String doDelete(String url) {
        return doDelete(url, TIMEOUT, TIMEOUT);
    }
    
    /*
     * add by sjc 2018-07-31
     */
    public static String doPut(String url, Map<String, String> params) {
        return doPut(url,params,TIMEOUT, TIMEOUT);
    }

}
