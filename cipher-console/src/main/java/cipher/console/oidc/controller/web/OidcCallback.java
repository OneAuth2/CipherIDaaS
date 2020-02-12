package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstansUMP;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.UserService;

import cipher.console.oidc.util.aes.AES;
import cipher.console.oidc.util.aes.AesKey;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/")
public class OidcCallback {

    private static final Logger logger = LoggerFactory.getLogger(OidcCallback.class);

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisClient redisClient;


    @RequestMapping("/callBack")
    @ResponseBody
    public Map<String,Object> callback(String idToken,  HttpServletResponse response,HttpServletRequest request)
            throws IOException {
        logger.info("idToken-----"+idToken);
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            Jwt tokenDecoded = JwtHelper.decode(idToken);
            Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(),
                    new TypeReference<Map<String, String>>() {});
            for(Map.Entry<String,String> mm:authInfo.entrySet()){
                logger.info(mm.getKey()+"---"+mm.getValue());
            }
            String clientId=authInfo.get("aud");
            String userName=authInfo.get("user_name");
            logger.info("userName******"+userName);

            redisClient.put(ConstantsCMP.REDIS_WEB_NAME +"clientId",clientId);
            redisClient.put(ConstantsCMP.REDIS_WEB_NAME + userName,userName);
            map.put("state",1);
        }catch (Exception e){
            logger.error(e.getMessage());
            map.put("state",0);
        }
        return map;
    }

    public static void main(String[] args) {
        try{
            Jwt tokenDecoded = JwtHelper.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImF1ZCI6ImFjbWUiLCJ1c2VyX25hbWUiOiJhZG1pbiIsImlzcyI6Imh0dHA6XC9cL3d3d3cud2FuZy5jb20iLCJleHAiOjE1MzA4NTk4NjEsImlhdCI6MTUzMDc3MzQ1NSwianRpIjoiMGE1YmY5OTAtODY0ZC00NjkyLTkyZjYtOGQ3MzRlZWNiMGQ3In0.TlcEcHN5OGNupssI1ahBGaL1vxspIhmY7j9CzSmVa4QP_u8GzLEBe7DbFNno8OK9eckqsOZLYRjjbwknwQhUzKbUgDWOLa1CtCWI043CJY2psfsxXdtxIGsofWpKWcaLfvm0S6iubZ6MBjkNC4sHyJxwBFlWOthQu48nbrEo88gHL7nS2iy0XV4A9jzaxK8EtII0ZU7W0R-TAWO5_D_5EdQVzZuXeQGUOlI7Ly1XHFNftdrjPDRDd6KXcBUqTNwrgg7MXw0MIhepkHScaLgJ-a9poH4ONpiWs0MsiEeAomI9R0LQCTcOweXXoP1iLvk3EwTO_vi0A6ByqIvU5Fdg_A");
            Map<String, String> authInfo = new ObjectMapper().readValue(tokenDecoded.getClaims(),
                    new TypeReference<Map<String, String>>() {});
            for(Map.Entry<String,String> mm:authInfo.entrySet()){
                logger.info(mm.getKey()+"---"+mm.getValue());
            }
            String name=authInfo.get("name");
            String email=authInfo.get("email");
        }catch (Exception e){

        }

    }



}
