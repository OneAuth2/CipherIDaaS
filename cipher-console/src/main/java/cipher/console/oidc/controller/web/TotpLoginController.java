package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.config.ServerUrlProperties;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.mapper.UserMapper;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.util.HttpUtils;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.URLParser;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zt
 * @Date: 2018/5/23 19:48
 */


@Controller
@RequestMapping(value = "/cipher")
@EnableAutoConfiguration
public class TotpLoginController {


    @Autowired
    private ServerUrlProperties serverUrlProperties;


    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisClient redisClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(TotpLoginController.class);

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login_new";
    }

    /**
     * @param userName
     * @param pwd
     * @param totp     totp码
     */
    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkUser(@RequestParam(value = "userName") String userName,
                                         @RequestParam(value = "pwd") String pwd,
                                         @RequestParam(value = "totp",required = false) String totp,
                                         HttpServletRequest request) {
        LOGGER.debug("enter TotpLoginController.checkUser;userName," + userName + ",pwd:" + pwd + ",totp:" + totp);
        Map<String, Object> map = new HashMap<>();
        //最后一个参数0代表非托管,1代表托管
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", userName));
        nameValuePairs.add(new BasicNameValuePair("pwd", pwd));
        nameValuePairs.add(new BasicNameValuePair("totp", totp));
//        nameValuePairs.add(new BasicNameValuePair("time", "0"));
        nameValuePairs.add(new BasicNameValuePair("status", "1"));

        String res = HttpUtils.getJsonFromServer(serverUrlProperties.getUrlPre() + "/user", nameValuePairs);
//        redisClient.remove(CacheKey.getCacheKeyCipherRadiusconfig(pwd, totp));
        if (StringUtils.isEmpty(res)) {
            return null;
        }

        JSONObject jsonObject = JSONObject.fromObject(res);
        int checkCode = jsonObject.getInt("checkCode");
        //totp不正确
        if (checkCode == 1) {
            map.put("status", 1);
            map.put("msg", "用户名密码或totp码不正确！");
            return map;
        }
        //验证成功，进入poortal页
        if (checkCode == 0) {
            UserInfoDomain domain=userMapper.getUserByAccountNumber(userName);
            if(domain.getIsAdmin()==0){
                map.put("status", 1);
                map.put("msg", "你不是管理员，没有权限登录");
            }else {
                map.put("status", 0);
                map.put("msg", "登录成功");
                map.put("userName", userName);
            }
        }

        return map;
    }

    @RequestMapping(value = "/sccanImg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSccanImg() {
        LOGGER.debug("enter TotpLoginController.getSccanImg");
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("secret", serverUrlProperties.getSecret()));
        nameValuePairs.add(new BasicNameValuePair("url", serverUrlProperties.getCallBack()));
        String res = HttpUtils.getJsonFromServer(serverUrlProperties.getUrlPre() + "/qrcode", nameValuePairs);
        if (StringUtils.isEmpty(res)) {
            return null;
        }
        String imgUrl = null;
        String uuid = null;
        try {
            JSONObject jsonObject = JSONObject.fromObject(res);
            //二维码的有效期时间
            Long expireDate = jsonObject.getLong("expiresIn");
            //二维码的地址
            imgUrl = jsonObject.getString("qrCodeURL");
            uuid = URLParser.fromURL(imgUrl).compile().getParameterFromUrl("url", "uuid");
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(serverUrlProperties.getLoginUUID() + uuid, uuid, expireDate / 1000, TimeUnit.SECONDS);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("imgUrl", imgUrl);
        map.put("userInfo", uuid);
        return map;
    }


    @RequestMapping(value = "/scanCallBack")
    public void callBack(@RequestParam(value = "userName") String userName,
                         @RequestParam(value = "result") int result,
                         @RequestParam(value = "uuid") String uuid,
                         HttpSession session,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        LOGGER.debug("enter TotpLoginController.getSccanImg;userName:" + userName + "result:" + result + "uuid:" + uuid);
        if (result == 0) {
            //回调成功，更改该用户在redis中的状态
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set(serverUrlProperties.getLoginUUID() + uuid, userName);
        } else {
            LOGGER.error(" scan img callback error:enter TotpLoginController.getSccanImg; userName:" + userName + "result:" + result + "uuid:" + uuid);
        }
    }


    /**
     * 轮询保持登录页的连接状态
     *
     * @return
     */
    @RequestMapping(value = "/longpoll", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> LongPoll(@RequestParam(value = "uuid") String uuid,HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String tms = valueOperations.get(serverUrlProperties.getLoginUUID() + uuid);
        /**
         * 状态值含义：
         * 0代表二维码已经过期，
         * 1.二维码还未被扫描
         * 2.扫码登录成功
         */
        //二维码已经过期
        if (null == tms) {
            map.put("status", 0);
            map.put("userName", "");
            return map;
        }
        //扫码等待状态
        if (uuid.equals(tms)) {
            map.put("status", 1);
            map.put("userName", "");
            return map;
        }
        //用户扫码已经回调成功

        UserInfoDomain domain=userMapper.getUserByAccountNumber(tms);
        if(domain.getIsAdmin()==0){
            map.put("status",3);
            map.put("userName", "");
            map.put("msg", "你不是管理员，没有权限登录");
        }else {
            map.put("status", 2);
            map.put("userName", tms);
        }
        return map;
    }



 /*   @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public void loginOut(HttpSession session,HttpServletRequest request){
        Map<String, Object> map=new HashMap<>();
        String username=ConstantsCMP.getSessionUser(request);
        if(StringUtils.isNotEmpty(username)){
            redisClient.remove(ConstantsCMP.REDIS_WEB_NAME + username);
            session.removeAttribute(username);
           }
       }*/

}
