package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.domain.web.UserSessionDomain;
import cipher.console.oidc.domain.web.UserSessionResp;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.CookieUtils;
import cipher.console.oidc.util.JasonUtil;
import cipher.console.oidc.util.MapUtil;
import cipher.console.oidc.util.NumberUtil;
import cipher.console.oidc.util.aes.AES;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {
    @Autowired
    private RedisClient<String,Object> redisClient;

    private int                         ttl        = 18000;  // 单位秒

    @Autowired
    private UserService userService;



    @Override
    public String getCompanyUUid(HttpSession session) {
        String companyUuid = (String) session.getAttribute(ConstantsCMP.CIPHER_CONSOLE_COMPANY_SESSION_INFO);
        return companyUuid;
    }




    @Override
    public boolean isSessionExpire(String uuid) {
        String userSessionJason = this.getUserSessionDomain(uuid);
        if (StringUtils.isBlank(userSessionJason)) {
            return  false;
        }
        return true;
    }

    @Override
    public void updateSessionExpireTime(String requestIp, String userName, String companyUUid) {
        this.setUserSessionDomain(userName);
    }

    @Override
    public void removeSession(String uuid) {

    }

    @Override
    public String getSession(String userName,String companyId) {
        String userSessionJason = this.getUserSessionDomain(userName);
        return userSessionJason;
    }



    /**
     * 获取session信息
     *
     * */
    private String getUserSessionDomain(String uuid) {
        return null;
    }

    /**
     * 设置session信息
     *
     * */
    private void setUserSessionDomain(String uuid) {
        String userSessionJason = this.getUserSessionDomain(uuid);
        if (StringUtils.isBlank(userSessionJason)) {
            UserSessionDomain  userSessionDomain = new UserSessionDomain();
            userSessionDomain.setUuid(uuid);
            userSessionDomain.setExpireTime(new Date());
            userSessionJason = JasonUtil.object2JSONString(userSessionDomain);
        }

    }

    @Override
    public UserSessionResp setUserLoginInfo(String uuid) {
        SortedMap<String, Object> parameterMap = new TreeMap<>();
        UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
        parameterMap.put("uuid", uuid);
        parameterMap.put("companyUUid", userInfoDomain.getCompanyId());
        parameterMap.put("timestamp", System.currentTimeMillis());
        StringBuffer sb = new StringBuffer();
        Set es = parameterMap.entrySet();  //所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if (null != v && !"".equals(v)) {
                sb.append(v);
            }
        }
        String param= MapUtil.getMapToString(parameterMap);
        String ticket= AES.encryptToBase64(param, ConstantsCMP.AES_KEY);
        //控制一个用户只允许登陆一次
     /*   String cipherParam= NumberUtil.createRandomCharData(10);
        redisClient.put(CacheKey.getCacheUserConsoleCookieInfo(uuid),cipherParam,ttl);
        UserSessionResp userSessionResp=new UserSessionResp();
        userSessionResp.setCookie(cipherParam);*/
        UserSessionResp userSessionResp=new UserSessionResp();
        userSessionResp.setTicket(ticket);
        return userSessionResp;
    }


}
