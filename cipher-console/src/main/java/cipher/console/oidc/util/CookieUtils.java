package cipher.console.oidc.util;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.OnlineVisitorService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.applet.Applet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CookieUtils {


    private static RedisClient redisClient = SpringContextUtil.getBean(RedisClient.class);


    private static volatile boolean isTrue;


    public static String getCookie(HttpServletRequest request, String cookieName) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static String writeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String uuid) {
        String cipherParam = NumberUtil.createRandomCharData(10);
      /*  if (!redisClient.containsKey(CacheKey.getCacheUserConsoleCookieInfo(uuid))) {
            redisClient.put(CacheKey.getCacheUserConsoleCookieInfo(uuid),cipherParam,7200);
        }*/

        HttpSession session = request.getSession();
        redisClient.put(CacheKey.getCacheUserConsoleCookieInfo(uuid),cipherParam,7200);
        System.out.println("cipherParam---------------------------------" + cipherParam);
        Cookie cookie = new Cookie(cookieName, (String)redisClient.get(CacheKey.getCacheUserConsoleCookieInfo(uuid)));
        cookie.setPath("/");
        cookie.setMaxAge(18000);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return cipherParam;
    }


    public static void updateCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String value) {
        Cookie[] cookies = request.getCookies();
        if (cookies.length > 1) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieName)) {
                    String oldValue = cookies[i].getValue();
                    String newValue = value;
                    cookies[i].setValue(newValue);
                    response.addCookie(cookies[i]);
                    break;
                }
            }

        }
    }


    public static void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        // 获取Cookies数组
        Cookie[] cookies = request.getCookies();
        // 迭代查找并清除Cookie
        for (Cookie cookie : cookies) {
            if (ConstantsCMP.CONSOLE_USER_COOKIE.equals(cookie.getName())) {
                cookie = new Cookie(ConstantsCMP.CONSOLE_USER_COOKIE, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }


    }
}
