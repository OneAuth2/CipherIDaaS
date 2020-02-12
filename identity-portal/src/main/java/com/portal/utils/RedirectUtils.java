package com.portal.utils;

import com.portal.commons.Constants;
import com.portal.redis.RedisClient;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: TK
 * @Date: 2019/7/2 16:24
 */
public final class RedirectUtils {

    public static void redirect(HttpServletRequest request, HttpServletResponse response, RedisClient redisClient, Logger logger) {

        //获取存入session中serviceURL的地址
        String serviceUrl= (String) SessionUtils.getSessionByName(request, Constants.SERVICEURL);

        //TODO
        //根据url获取的从账号
        String  sonAccount="dsasdasdad";

        //TODO 生成票据
        String ticket="dsadsads";

        //存入redis中
        redisClient.put(ticket,sonAccount);

        //生成重定向地址
        String url = UrlUtils.getUrl(serviceUrl, ticket);

        try {

            //重定向到目标地址
            response.sendRedirect(url);
            return;
        } catch (IOException e) {

            //打印错误
            logger.error("System error by serviceUrl ,this url={}", url);
            logger.error(e.getMessage(), e);

        }

        return;

    }
}
