package cipher.console.oidc.interceptor;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.token.PassToken;
import cipher.console.oidc.util.URLParser;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class AuthenticationInterceptor implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final String SIGNATURE = "sign";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
         //检验host是否是与本机服务器host是否一致
      /*  String referer = request.getHeader("Referer");
       //  LOGGER.info("referer----------------"+referer);
        if (StringUtils.isNotEmpty(referer)) {
        try {
                URL url = new URL(referer);
                String host = url.getHost();
                URLParser.ParseDomainName parseDomainName = new URLParser.ParseDomainName(host);
                if (!StringUtils.equals(HOST, parseDomainName.getServerIP().getHostAddress())) {
                    LOGGER.info("拦截器认证不通过----------");
                    return checkSign(response, "用户登录异常，重新登录");
                }
            } catch(MalformedURLException e){
                e.printStackTrace();
            }
        }*/

        //不是映射到方法，直接通过
       /* if (!(handler instanceof HandlerMethod)) {
            LOGGER.info("Passed Without Check The Token");
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //校验是否有跳过检验的注解，如果有直接跳过
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                LOGGER.info("Passed Needn't Check The Token");
                return true;
            }
        }*/
      /*  //从请求中获取签名sign
        String token = request.getHeader(SIGNATURE);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(SIGNATURE);
        }

        //检测为校验签名
        if (method.isAnnotationPresent(CheckToken.class)) {
            CheckToken loginToken = method.getAnnotation(CheckToken.class);
            if (!loginToken.required()) {
                return true;
            }
            if (StringUtils.isBlank(token)) {
                return checkSign(response, "用户登录异常，重新登录");
            }
            String sign = request.getParameter("sign");
            //LOGGER.info("sign=" + sign);
            long timestamp = Long.valueOf(request.getParameter("timestamp"));
          //  LOGGER.info("timestamp:" + timestamp);
           // long current = System.currentTimeMillis();
            *//*if (current - timestamp > 30000) {
                return checkSign(response, "用户登录超时，重新登录");
            }*//*

            StringBuilder src = new StringBuilder();
            Map<String, String[]> params = request.getParameterMap();
            List<Map.Entry<String, String[]>> list = new ArrayList<>(params.entrySet());

            //升序排序
            list.sort(Comparator.comparing(Map.Entry::getKey));
            for (Map.Entry entry : list) {
                if ("ticket".equals(entry.getKey())||"json".equals(entry.getKey())||"timestamp".equals(entry.getKey()) || "sign".equals(entry.getKey())) {
                    continue;
                }
                String[] str = (String[]) entry.getValue();
                src.append(entry.getKey()).append("=").append(str[0]).append("&");
            }
            if (StringUtils.isNotEmpty(src)) {
                src.deleteCharAt(src.length() - 1);
            }

            String tosign = null;
            try {
                tosign = URLEncoder.encode(src.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String md5Str = DigestUtils.md5Hex(tosign);
           // LOGGER.info(md5Str);
            if (!StringUtils.equals(sign, md5Str)) {
                return checkSign(response, "用户登录异常，重新登录");
            }
        }*/
        return true;
    }







    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }


    public  boolean checkSign(HttpServletResponse httpServletResponse,String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("return_code", ConstantsCMP.SESSION_EXPIRE);
        jsonObject.put("return_msg",msg);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = httpServletResponse.getWriter();
            out.append(jsonObject.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }





}
