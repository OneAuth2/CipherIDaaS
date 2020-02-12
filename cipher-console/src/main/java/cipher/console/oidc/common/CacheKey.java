package cipher.console.oidc.common;

/**
 * 缓存KEY常量管理类
 *
 * @author jason.xu
 */
public class CacheKey {

    private static final String UNDER_LINE = "_";

    public static final String CACHE_FOR_ALL_SYSTEM_INFO_LIST = "KEY_ALL_SYSTEM_INFO_LIST_";
    public static final String CACHE_KEY_LOGIN_FAILED_CONFIG = "KEY_LOGIN_MANAGE_";
    public static final String CACHE_KEY_USER_LOGIN_FAILED_INFO = "KEY_USER_LOGIN_FAILED_INFO_";
    public static final String CACHE_KEY_PWD_BY_USER_NAME = "KEY_PWD_BY_USER_NAME_";
    public static final String CACHE_KEY_SECRET_BY_USER_NAME = "KEY_SECRET_BY_USER_NAME_";
    public static final String CACHE_KEY_QUERY_RADIUS_CONFIG = "KEY_RADIUS_CONFIG_";
    public static final String CACHE_KEY_USER_BY_NAME = "KEY_USER_BY_NAME_";
    public static final String CACHE_KEY_GLOBAL_AUTH_TYPE = "KEY_GLOBAL_AUTH_TYPE_";
    public static final String CACHE_KEY_MOBILE_PHONE_SMS_STR = "KEY_MOBILE_PHONE_SMS_STR_";
    public static final String CACHE_KEY_CIPHER_LDAP_CONFIG = "KEY_CIPHER_LDAP_CONFIG_";
    public static final String CACHE_KEY_CIPHER_AC_PORTAL_CONFIG = "KEY_CIPHER_AC_PORTAL_CONFIG_";
    public static final String CACHE_KEY_CIPHER_GUEST_ASYNC = "KEY_CIPHER_GUEST_ASYNC_";
    public static final String CIPHER_KEY_CIPHER_TOTPAUTHORIZATIONMAG = "KEY_CIPHER_TOTPAUTHORIZATIONMAG_";
    public static final String CACHE_KEY_CIPHER_PORTAL_CONFIG = "KEY_CIPHER_PORTAL_CONFIG_";

    public static final String CACHE_KEY_CIPHER_CONSOLE_USER_INFO= "KEY_CIPHER_CONSOLE_USER_INFO_";

    /*用户TOKEN加密的字符串*/
    private static final String CACHE_USER_CONSOLE_TOKEN = "KEY_USER_CONSOLE_TOKEN_";

    private static final String CACHE_USER_CONSOLE_COOKIE_INFO="KET_USER_CONSOLE_COOKIE_INFO_";

    private static final String CACHE_PORTAL_SET_ONE="KET_PORTAL_SET_ONE_";


    private static final String CACHE_PORTAL_SET_TWO="KET_PORTAL_SET_TWO_";

    private static final String CACHE_KEY_USER_COOKIE_VALUE="CACHE_KEY_USER_COOKIE_VALUE_";

    private static final String CACHE_USER_ISFREEZED="KEY_USER_ISFREEZED_";

    //获取组织结构树
    private static final String CACHE_ORGANITION_TREE_LIST="KEY_ORGANITION_TREE_LIST_";

    //获取部门结构树
    private static  final String CACHE_GROUP_TREE_LIST="KEY_GROUP_TREE_LIST_";


    //获取应用列表
    private static  final String CACHE_APPLICATION_LIST="KEY_APPLICATION_LIST_";

    public static String getCacheApplicationList(String companyId) {
        return CACHE_APPLICATION_LIST + companyId;
    }

    public static String getCacheGroupTreeList(String companyId) {
        return CACHE_GROUP_TREE_LIST + companyId;
    }

    public static String getCacheOrganitionTreeList(String companyId) {
        return CACHE_ORGANITION_TREE_LIST + companyId;
    }

    public static String getCachePortalSetOne() {
        return CACHE_PORTAL_SET_ONE;
    }

    public static String getCachePortalSetTwo() {
        return CACHE_PORTAL_SET_TWO;
    }


    public static String getCacheUserIsfreezed(String uuid) {
        return CACHE_USER_ISFREEZED + uuid;
    }


    public static String getCacheKeyUserCookieValue(String userId) {
        return CACHE_KEY_USER_COOKIE_VALUE +userId;
    }

    public static String getCacheUserConsoleCookieInfo(String uuid) {
        return CACHE_USER_CONSOLE_COOKIE_INFO +uuid;
    }

    public static String getCacheUserConsoleToken(String uuid, String ip) {
        return CACHE_USER_CONSOLE_TOKEN+ uuid +ip;
    }

    public static String getCacheKeyCipherConsoleUserInfo(String uuid) {
        return CACHE_KEY_CIPHER_CONSOLE_USER_INFO + uuid;
    }

    /**
     * 钉钉的access_token缓存key前缀
     */
    private static final String CACHE_KEY_DING_ACCESS_TOKEN = "DING_ACCESS_TOKEN_";

   /*
   企业微信的access_token缓存key前缀
    */
    private static final String CACHE_KEY_WX_ACCESS_TOKEN = "WX_ACCESS_TOKEN_";

    public static String getAllSystemInfoListCacheKey() {
        return CACHE_FOR_ALL_SYSTEM_INFO_LIST;
    }

    public static String getLoginFailedConfigCacheKey() {
        return CACHE_KEY_LOGIN_FAILED_CONFIG;
    }

    public static String getUserLoginFailedInfoCacheKey(String account) {
        return CACHE_KEY_USER_LOGIN_FAILED_INFO + account;
    }

    public static String getPwdByUserNameCacheKey(String userName) {
        return CACHE_KEY_PWD_BY_USER_NAME + userName;
    }

    public static String getSecretByUserNameCacheKey(String userName) {
        return CACHE_KEY_SECRET_BY_USER_NAME + userName;
    }

    public static String getQueryRadiusConfigCacheKey() {
        return CACHE_KEY_QUERY_RADIUS_CONFIG;
    }

    public static String getUserByNameCacheKey(String userName) {
        return CACHE_KEY_USER_BY_NAME + userName;
    }

    public static String getGlobalAuthTypeCacheKey() {
        return CACHE_KEY_GLOBAL_AUTH_TYPE;
    }

    public static String getMobilePhoneSmsStrCacheKey(String phone) {
        return CACHE_KEY_MOBILE_PHONE_SMS_STR + phone;
    }

    public static final String getCacheKeyCipherLdapConfig() {
        return CACHE_KEY_CIPHER_LDAP_CONFIG;
    }

    public static final String getCacheKeyCipherAcPortalConfig() {
        return CACHE_KEY_CIPHER_AC_PORTAL_CONFIG;
    }

    public static final String getCacheKeyCipherGuestAsync(String tel) {
        return CACHE_KEY_CIPHER_GUEST_ASYNC + tel;
    }

    public static final String getCipherKeyCipherTotpauthorizationmag(String accountNumber) {
        return CIPHER_KEY_CIPHER_TOTPAUTHORIZATIONMAG + accountNumber;
    }

    public static final String getCacheKeyCipherPortalConfig(int id) {
        return CACHE_KEY_CIPHER_PORTAL_CONFIG + id;
    }

    public static String getCacheKeyDingAccessToken(String companyId) {
        return CACHE_KEY_DING_ACCESS_TOKEN + companyId;
    }

    public static String getCacheKeyWxAccessToken(String companyId) {
        return CACHE_KEY_WX_ACCESS_TOKEN + companyId;
    }

}
