package com.portal.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: TK
 * @Date: 2019/7/19 13:19
 */
public final class TokenUtils {

    /**
     * 对传来的参数获取签名
     * @param params1
     * @param params2
     * @return
     */
    public static String getToken(String params1,String params2){

        //入参校验
        if (StringUtils.isEmpty(params1) ||StringUtils.isEmpty(params2)){
            return "";
        }

        //生成token
        Integer random = (int) ((Math.random() * 9 + 1) * 100000);
        String cipherString = params1 + params2 + random;
        String token = JWT.create().withAudience(params1).sign(Algorithm.HMAC256(cipherString));

        //返回token
        return token;
    }
}
