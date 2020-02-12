package com.portal.filter;/**
 * @author lqgzj
 * @date 2019-09-27
 */

import com.alibaba.fastjson.JSON;
import com.portal.commons.ConstantsCMP;
import com.portal.license.VerifyLicense;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019-09-2715:25
 **/
public class LicenseCheckInterceptor extends HandlerInterceptorAdapter {
    private static Logger logger = LogManager.getLogger(LicenseCheckInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        VerifyLicense vLicense = new VerifyLicense();
        //获取参数
        vLicense.setParam("/config/param.properties");
        //验证证书
        boolean verifyResult = vLicense.verify();
        if (verifyResult) {
            return true;
        } else {
            response.setCharacterEncoding("utf-8");
            Map<String, Object> result = new HashMap<>(1);
            result.put("return_code", ConstantsCMP.Code.FAIL);
            result.put("return_msg", "许可证已过期！");
            response.getWriter().write(JSON.toJSONString(result));
            return false;
        }
    }
}
