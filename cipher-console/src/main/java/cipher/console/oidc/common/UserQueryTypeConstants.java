package cipher.console.oidc.common;

/**
 * @Author: zt
 *  * 从欢迎页跳转到用户管理也不同的查询条件
 * @Date: 2018/6/7 11:51
 */
public class UserQueryTypeConstants {

    /**
     * 在线用户
     */
    public static final String ONLINE_USER="1";

    /**
     * 未授权空置账号
     */
    public static final String NOT_AUH_USER="2";

    /**
     * 临时账号过期
     */
    public static final String TMP_ACCOUNT_OUT_OF_DATE="3";

    /**
     * 授权过期
     */
    public static final String AUTH_OUT_OF_DATE="4";

    /**
     * 异常地点登录
     */
    public static final String UNUSUAL_LOGIN_ADRESS="5";

}
