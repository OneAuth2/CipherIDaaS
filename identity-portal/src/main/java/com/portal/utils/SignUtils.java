package com.portal.utils;


import com.portal.utils.rsa.RSATool;
import org.apache.commons.lang.StringUtils;

/**
 * @Author: TK
 * @Date: 2019/7/19 13:22
 */
public final class SignUtils {

    /**
     * 根据参数获取参数的签名
     * @param params
     * @return
     */
    public static String getSign(String params){

        //入参校验
        if (StringUtils.isEmpty(params)){
            return "";
        }

        return RSATool.encryptWithPubKey(params, RSATool.PUB_KEY);
    }
}
