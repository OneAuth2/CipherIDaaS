package cipher.console.oidc.common;

/**
 * @Author: zt
 * 获取在线用户列表相关常量
 * @Date: 2018/6/9 15:00
 */
public class OnlineUserConstants {

    /**
     * 在线用户的前缀
     */
    public static final String UNIQUE_PRE = "online_user_";

    /**
     * 键的过期时间应和session的有效期相同
     */
    public static final int EXPIRE_TIME = 30 * 60;

    /**
     * 存session的登录用户的键的前缀
     */
    public static final String UUID_PRE="uuid_";

}
