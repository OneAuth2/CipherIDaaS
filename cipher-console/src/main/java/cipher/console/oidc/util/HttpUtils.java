package cipher.console.oidc.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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

}
