package cipher.jms.consumer.common;

public class CacheKey {
    private static final String CACHE_KEY_KATA_CODE = "KEY_KATACODE_";

    public static final String CACHE_KEY_MOBILE_PHONE_SMS_STR = "KEY_MOBILE_PHONE_SMS_STR_";


    private static final String CACHE_KEY_RONG_LIAN_INFO = "KEY_RONG_LIAN_INFO";


    private static final String CACHE_KEY_ALI_YUN_INFO = "KEY_ALI_YUN_INFO";


    private static final  String CACHE_KEY_EMAIL_INFO="KEY_EMAIL_INFO";

    public static String getCacheKeyEmailInfo() {
        return CACHE_KEY_EMAIL_INFO;
    }

    public static String getyKataCodeCacheKe(String phoneNumberOrMail) {
        return CACHE_KEY_KATA_CODE + phoneNumberOrMail;
    }

    public static String getMobilePhoneSmsStrCacheKey(String phone) {
        return CACHE_KEY_MOBILE_PHONE_SMS_STR + phone;
    }

    public static String getCacheKeyRongLianInfo() {
        return CACHE_KEY_RONG_LIAN_INFO;
    }

    public static String getCacheKeyAliYunInfo() {
        return CACHE_KEY_ALI_YUN_INFO;
    }
}
