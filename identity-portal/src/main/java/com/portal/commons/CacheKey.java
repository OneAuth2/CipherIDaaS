package com.portal.commons;

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
    public static final String CACHE_KEY_RADIUS_CONFIG = "KEY_RADIUS_CONFIG_";
    public static final String CACHE_KEY_USER_BY_NAME = "KEY_USER_BY_NAME_";
    public static final String CACHE_KEY_GLOBAL_AUTH_TYPE = "KEY_GLOBAL_AUTH_TYPE_";
    public static final String CACHE_KEY_MOBILE_PHONE_SMS_STR = "KEY_MOBILE_PHONE_SMS_STR_";
    public static final String CACHE_KEY_USER_LOGIN_UUID = "KEY_USER_LOGIN_UUID_";
    public static final String CACHE_DING_DING_ACCESS_TOKEN = "DING_DING_ACCESS_TOKEN";

    public static final String CACHE_KEY_MAIL_AUTH_CODE = "KEY_MAIL_AUTH_CODE";
    public static final String CACHE_KEY_PHONE_AUTH_CODE = "KEY_PHONE_AUTH_CODE";


    public static final String CACHE_KEY_AUTH_CODE = "KEY_PHONE_AUTH_CODE";
    public static final String CACHE_KEY_SAIFU_AUTH_CODE = "KEY_SAIFU_AUTH_CODE";
    public static final String CACHE_KEY_DABAI_AUTH_CODE = "KEY_DABAI_AUTH_CODE";
    public static final String CACHE_KEY_DINGDING_AUTH_CODE = "KEY_DINGDING_AUTH_CODE";
    public static final String CACHE_KEY_CIPHER_AUTH_QRCODE_UUID = "KEY_CIPHER_AUTH_QRCODE_UUID_";

    public static final String CACHE_KEY_CIPHER_AUTH_ACCESS_TOKEN = "KEY_CIPHER_AUTH_ACCESS_TOKEN";

    public static final String CACHE_KEY_USER_SESSION_ = "KEY_USER_SESSION_";

    public static final String CACHE_KEY_CIPHER_CONSOLE_USER_INFO = "KEY_CIPHER_CONSOLE_USER_INFO_";

    private static final String CACHE_USER_CONSOLE_COOKIE_INFO = "KET_USER_CONSOLE_COOKIE_INFO_";


    private static final String CACHE_KEY_KATA_CODE = "KEY_KATACODE_";

    private static final String CACHE_USER_ISFREEZED = "KEY_USER_ISFREEZED_";

    /**
     * 钉钉push认证结果的key
     */
    private static final String DING_PUSH_AUTH_RESULT_KEY = "DING_PUSH_AUTH_RESULT_KEY_";


    /**
     * 钉钉push认证结果的key
     */
    private static final String DING_PUSH_RETURN_RESULT_KEY = "DING_PUSH_RETURN_RESULT_KEY_";


    public static String getDingPushReturnResultKey(String uuid) {
        return DING_PUSH_RETURN_RESULT_KEY +uuid;
    }

    public static String getDingPushAuthResultKey(String uuid, Long timestamp) {
        return DING_PUSH_AUTH_RESULT_KEY + uuid + timestamp ;
    }

    private static  final String API_GATEWAY_USER_LOGIN_IN="API_GATEWAY_USER_LOGIN_IN_";

    public static String getCacheUserIsfreezed(String uuid) {
        return CACHE_USER_ISFREEZED + uuid;
    }


    public static String getyKataCodeCacheKe(String phoneNumberOrMail) {
        return CACHE_KEY_KATA_CODE + phoneNumberOrMail;
    }


    public static String getCacheUserConsoleCookieInfo(String uuid) {
        return CACHE_USER_CONSOLE_COOKIE_INFO + uuid;
    }

    public static final String CACHE_KEY_CIPHER_CAS_SERVICE_URL = "CACHE_KEY_CIPHER_CAS_SERVICE_URL";

    public static String getCacheKeyCipherConsoleUserInfo(String uuid) {
        return CACHE_KEY_CIPHER_CONSOLE_USER_INFO + uuid;
    }

    public static final String CACHE_KEY_CIPHER_PORTAL_COOKIE_INFO = "KEY_CIPHER_PORTAL_COOKIE_";


    public static String getCacheKeyCipherPortalCookieInfo(String userId) {
        return CACHE_KEY_CIPHER_PORTAL_COOKIE_INFO + userId;
    }


    public static final String CACHE_KEY_USER_REGISTER_PHONE = "KEY_USER_REGISTER_PHONE_";

    public static final String CACHE_KEY_USER_REGISTER_MAIL = "KEY_USER_REGISTER_MAIL_";

    public static final String CACHE_KEY_USER_BINDING_PHONE = "CACHE_KEY_USER_BINDING_PHONE_";

    public static final String CACHE_KEY_USER_BINDING_MAIL = "CACHE_KEY_USER_BINDING_MAIL_";

    public static final String CACHE_KEY_USER_AUTH = "CACHE_KEY_USER_AUTH_";

    public static final String CACHE_KEY_USER_OTP_SECRECT="CACHE_KEY_USER_OTP_SECRECT_";


    public static String getCacheKeyUserOtpSecrect(String userId) {
        return CACHE_KEY_USER_OTP_SECRECT + userId;
    }

    public static String getUserSessionCacheKey(String userName) {
        return CACHE_KEY_USER_SESSION_ + userName;
    }


    public static String getCacheKeyCipherAuthAccessToken() {
        return CACHE_KEY_CIPHER_AUTH_ACCESS_TOKEN;
    }


    public static String getCacheKeyCipherAuthQrcodeUuid(String uuid) {
        return CACHE_KEY_CIPHER_AUTH_QRCODE_UUID + uuid;
    }

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

    public static String getRadiusConfigCacheKey() {
        return CACHE_KEY_RADIUS_CONFIG;
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

    public static String getUserLoginUuid(String uuid) {
        return CACHE_KEY_USER_LOGIN_UUID + uuid;
    }

    public static String getCacheKeyMailAuthCode(String mail) {
        return CACHE_KEY_MAIL_AUTH_CODE + mail;
    }


    public static String getCacheKeySaifuAuthCode(String saifuUuid) {
        return CACHE_KEY_SAIFU_AUTH_CODE + saifuUuid;
    }

    public static String getCacheKeyDabaiAuthCode(String dabaiUuid) {
        return CACHE_KEY_DABAI_AUTH_CODE + dabaiUuid;
    }

    public static String getCacheKeyDingdingAuthCode(String dingdingUuid) {
        return CACHE_KEY_DINGDING_AUTH_CODE + dingdingUuid;
    }

    public static String getCacheKeyPhoneAuthCode(String phoneNumber) {
        return CACHE_KEY_PHONE_AUTH_CODE + phoneNumber;
    }

    public static String getCacheKeyAuthCode(String accountNumber) {
        return CACHE_KEY_AUTH_CODE + accountNumber;
    }

    public static String getCacheDingDingAccessToken(String companyUUid) {
        return CACHE_DING_DING_ACCESS_TOKEN + companyUUid;
    }

    public static String getCacheKeyUserRegisterPhone(String phoneNumber) {
        return CACHE_KEY_USER_REGISTER_PHONE + phoneNumber;
    }


    public static String getCacheKeyUserRegisterMail(String mail) {
        return CACHE_KEY_USER_REGISTER_MAIL + mail;
    }


    public static String getCacheKeyUserBindingPhone(String userId) {
        return CACHE_KEY_USER_BINDING_PHONE + userId;
    }

    public static String getCacheKeyUserBindingMail(String userId) {
        return CACHE_KEY_USER_BINDING_MAIL + userId;
    }

    public static String getCacheKeyUserAuth(String userId) {
        return CACHE_KEY_USER_AUTH + userId;
    }

    public static String getCacheKeyCipherCasServiceUrl(String userName) {
        return CACHE_KEY_CIPHER_CAS_SERVICE_URL + userName;
    }

    public static String getApiGatewayUserLoginIn(String uuid) {
        return API_GATEWAY_USER_LOGIN_IN+uuid;
    }
}
