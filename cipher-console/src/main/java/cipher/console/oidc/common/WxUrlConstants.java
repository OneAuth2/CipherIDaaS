package cipher.console.oidc.common;

/**
 * 企业微信接口常量
 */
public class WxUrlConstants {

    /**
     * 获取企业微信access_token的接口地址
     */
    public static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    /**
     * 递归获取企业微信部门的接口地址
     */
    public static final String GROUP_SYNC_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";


    /**
     * 企业微信获取用户列表的接口地址
     */
    public static final String WX_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list";

    /**
     * 请求用户列表的分页参数
     */
    public static final Integer OFFSET = 0;

    /**
     * 请求用户列表的分页参数
     */
    public static final Integer SIZE = 100;


    /**
     * 递归获取所有部门的子部门列表
     */
    public static final String DEPT_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list";

    /**
     * 企业微信根部门id
     */
    public static final Long WX_ROOT_DEPT_ID = 1L;

    /**
     * 返回码正确是为0
     */
    public static final Integer REQUEST_SUCESS = 0;

    /**
     * 接口返回码的key
     */
    public static final String ERR_CODE_KEY = "errcode";

    public static final String ERR_MSG_KEY = "errmsg";

    /**
     * 导入企业微信用户时json数据的用户列表key
     */
    public static final String USER_LIST_KEY = "userlist";
    /**
     * 导入企业微信用户json数据，是否有更多的数据,key
     */
    public static final String HAS_MORE_KEY = "";

}
