package cipher.console.oidc.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 95744 on 2018/6/8.
 */
public class ConstantsCMP {


    //管理员行为常量
    public final static class AdminBehaviorConstant {
/*
        public final static int    NUMBER_MAINTAIN_RECORD=1;
        public final static int APPLICATION_MANAGER_RECORD=2;
        public final static int AUTHCAZITION_RECODE=3;
        public final static int  WIFE_MANAGER_RECORD=4;
        public final static int SYSTEM_SETUP_OPERATION_RECODE=5;*/


    }



    //应用常量
    public final static class ApplicationConstant {
        public final static int AUTHORIZE = 1;
        public final static int LOGIN = 2;

    }


    public final static String   REDIS_WEB_NAME = "cipher_console_oidc";

    public final static String   CIPHER_OIDC_USER_SESSION_INFO = "account";

    public final static String   CIPHER_CONSOLE_COMPANY_SESSION_INFO = "companyUuid";

    public final static String  CIPHER_UUID_INFO="uuid";

    public final static int   IS_ADMIN = 1;

    public final static int   IS_NOT_ADMIN = 0;

    public final static  String   CIPHER_CONSOLE_USER_SESSION_INFO = "console_user";


    public final static String   CONSOLE_USER_COOKIE= "console_cookie";



    public final static class SmsConstant {
        public final static int IS_OPEN = 1;
        public final static int IS_CLOSE = 0;
    }

    public static String getCipherUuidInfo(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute("uuid"));
    }

    public static String getSessionUser(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute("account"));
    }



    public static String getSessionCompanyId(HttpServletRequest request) {
        return (String) (request.getSession().getAttribute("companyUuid"));
    }


    public static final String AES_KEY = "u13ED9Sb5UX4eUnu";


    public final static class WifiStrategyConstant {
        public static final String STAFF = "staff";
        public static final String VISTOR = "vistor";
    }


    public final static int   IS_DELETE = 1;


    //管理员行为常量
    public final static class ReturnCode {
        public final static int SUCCESS = 1;
        public final static int FAIL = 0;

    }


    public final static class Code {
        public final static int SUCCESS = 0;
        public final static int FAIL = 1;
        public final static int EXPERTION=2;

    }


    public final static class QuartzConstant {
        public static final String VISTOR_ON_LINE = "vistor";

    }


    public final static class WelcomeConstant {
        public final static int    NO_GROUP = 1;
        public final static int    LOCK_NUMBER = 2;
        public final static int    NOT_LOGIN = 3;
    }

    public final static class UserStatus {
        public final static int    START = 1;
        public final static int    STOP = 2;

    }

    public final static class SubNameRuleConstant {
        public final static int    ACCOUNT_NUMBER = 1;
        public final static int    TELEPHONE = 2;
        public final static int    MAIL = 3;
        public final static int    MAIL_PREX = 4;
    }


    public final static class IsAdon {
        public final static int AD_NO = 0;
        public final static int AD_YES = 1;

    }

    /**
     * 返回SESSION失效
     */
    public static final int SESSION_EXPIRE=419;

}









