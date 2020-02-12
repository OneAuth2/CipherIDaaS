package com.portal.commons;

/**
 * @Author: TK
 * @Date: 2019/4/29 15:12
 */
public class Constants {
    /**
     * 用户唯一标识
     */
    public static final String NORMAL_UUID="normaluuid";
    /**
     * 二次认证的方式
     */
    public static final String SECOND_LOGIN_INFO = "secondLoginInfo";

    /**
     * cas认证的有效期
     */
    public static final int CAS_REDIS_TTL = 5 * 60 * 1000;

    /**
     * return_code
     */
    public static final String RETURN_CODE = "return_code";

    /**
     * portal未认证
     */
    public static final String ACCOUNT_IS_STOP = "accountIsStop";

    /**
     * 账号停用状态码
     */
    public static final int ACCOUNT_IS_STOP_CODE = 1;

    /**
     * 账号未停用状态码
     */
    public static final int ACCOUNT_IS_NOT_STOP_CODE = 0;

    /**
     * portal未认证
     */
    public static final String PORTAL_UNAUTHNATION = "未进行认证";

    /**
     * return_msg
     */
    public static final String RETURN_MSG = "return_msg";

    /**
     * normalFlow
     */
    public static final String NORMAL_FLOW = "normalFlow";

    /**
     * serviceUrl
     */
    public static final String SERVICE_URL = "serviceUrl";

    /**
     * data
     */
    public static final String DATA = "data";

    /**
     * subPwd
     */
    public static final String SUB_PWD = "subPwd";

    /**
     * subName
     */
    public static final String SUB_NAME = "subName";

    /**
     * applyUrl
     */
    public static final String APPLY_URL = "applyUrl";

    /**
     * 标志登录是走cas认证的流程
     */

    public static final int CAS_PROCESS = 1;

    /**
     * 用户名标识
     */
    public static final String USERNAME = "uuid";

    /**
     * 公司唯一标识
     */
    public static final String COMPANY_UUID = "companyUuid";

    /**
     * 认证的来源的标识
     */
    public static final String STATUS = "status";

    /**
     * serviceUrl重定向的地址
     */
    public static final String SERVICEURL = "serviceUrl";

    /**
     * xDsgUrl
     */
    public static final String X_DSG_URL = "xDsgUrl";

    /**
     * 钉钉的Accesstoken有效期
     */
    public static final long DING_EXPIRE = 7200;

    /**
     * 二次认证
     */
    public static final String SECOND_LOGIN = "secondLogin";

    /**
     * 登录的用户信息
     */
    public static final String LOGIN_INFO = "loginInfo";

    /**
     * 信息采集
     */
    public static final String INFO_COLLECTION = "infoCollection";

    /**
     * 用户id
     */
    public static final String USER_ID = "userId";

    /**
     * 二次认证开启
     */
    public static final int SECOND_LOGIN_CODE = 0;

    /**
     * 二次认证未开启
     */
    public static final int NOT_SECOND_LOGIN_CODE = 1;

    /**
     * 首次登陆
     */
    public static final String FIRST_LOGIN = "firstLogin";

    /**
     * 首次登陆的值
     */
    public static final int FIRST_LOGIN_CODE = 0;

    /**
     * 不是首次登陆的值
     */
    public static final int NOT_FIRST_LOGIN_CODE = 1;

    /**
     * 修改密码
     */
    public static final String UPDATE_PWD = "updatePwd";

    /**
     * 修改密码的值
     */
    public static final int UPDATE_PWD_CODE = 0;

    /**
     * 不修改密码
     */
    public static final int NOT_UPDATE_PWD_CODE = 1;

    /**
     * 认证开
     */
    public static final int AUTH_ON = 0;

    /**
     * 认证关
     */
    public static final int AUTH_OFF = 1;

    /**
     * ad认证
     */
    public static final int AUTH_AD = 4;

    /**
     * 验证成功
     */
    public static final int AUTH_SUCCESS = 0;

    /**
     * 设置缓存的有效期
     */
    public static final long EXPIRES = 1000 * 3600;

    /**
     * 验证失败
     */
    public static final int AUTH_ERROR = 1;

    /**
     * 操作成功
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * 开
     */
    public static final int ON = 1;

    /**
     * 关
     */
    public static final int OFF = 1;

    /**
     * 操作失败
     */
    public static final int ERROR_CODE = 1;


    /**
     * 赛赋绑定
     */
    public static final String SAI_FU_BOUND = "isBound";

    /**
     * 赛赋id
     */
    public static final String SAI_FU_ID = "saiFuId";


    /**
     * 大白绑定
     */
    public static final String DA_BAI_BOUND = "isBound";


    public static final String IS_BOUND = "isBound";

    /**
     * 大白ID
     */
    public static final String DA_BAI_ID = "daBaiId";


    /**
     * 钉钉绑定标记
     */
    public static final String DING_TALK_BOUND = "isBound";


    /**
     * 微信绑定标记
     */
    public static final String WEI_XIN_BOUND = "isBound";


    /**
     * 钉钉ID
     */
    public static final String DING_TALK_ID = "dingTalkId";

    public static final String DING_TALK_SCAN_ID = "dingTalkScanId";

    public static final String DING_TALK_CALL_BACK_URL= "dingTalkCallBackUrl";

    /**
     * 赛赋认证信息
     */
    public static final String SAI_FU_INFO = "saiFuInfo";

    /**
     * 大白认证信息
     */
    public static final String DA_BAI_INFO = "daBaiInfo";

    /**
     * 钉钉认证信息
     */
    public static final String DING_TALK_INFO = "dingTalkInfo";

    /**
     * 是否进行手机校验
     */
    public static final String PHONE_CHECK = "phoneCheck";

    /**
     * 是否进行邮箱校验
     */
    public static final String MAIL_CHECK = "mailCheck";


    /**
     * 手机号参数
     */
    public static final String PHONE_NUMBER = "phoneNumber";

    /**
     * 邮箱参数
     */
    public static final String MAIL = "mail";

    /**
     * 邮箱是否已经收集
     */
    public static final String IS_MAIL_COLLECT = "isMailCollect";

    /**
     * 收集是否已经收集
     */
    public static final String IS_PHONE_COLLECT = "isPhoneCollect";


    public static final int SUCCESS = 0;


    /**
     * 请求XDS失败 请联系管理员
     */

    public static final String SYS_ERROR = "请求XDS失败 请联系管理员";

    /**
     * subAccount
     */
    public static final String SUB_ACCOUNT = "subAccount";

    /**
     * 4
     */
    public static final int ERROR_RETURN_CODE = 4;

    /**
     * 认证类型type
     */
    public static final String TYPE = "type";

    /**
     * 认证类型1 正常登陆
     */
    public static final int NORMAL_TYPE = 1;

    /**
     * 认证类型2 cas认证
     */
    public static final int CAS_TYPE = 2;

    /**
     * 认证类型3 rdsg认证
     */
    public static final int RDSG_TYPE = 3;

    /**
     * size
     */
    public static final int SIZE = 0;


    /**
     * otp码失效时间
     */
    public  static  final  int  OTPEXPRIE = 30;



    /**
     * 微信ID
     */
    public static final String WX_APPID = "appid";

    public static final String WX_REDIRET_URI = "redirect_uri";

    public static final String WX_STATE= "agentid";

    public static final String WX_UNION_ID= "wxUserId";

    public static final String WX_INFO = "weixinInfo";









}
