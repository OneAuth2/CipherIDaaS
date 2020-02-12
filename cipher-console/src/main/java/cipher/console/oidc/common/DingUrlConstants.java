package cipher.console.oidc.common;

/**
 * 钉钉接口常量
 *
 * @Author: zt
 * @Date: 2019-05-11 17:10
 */
public class DingUrlConstants {

    /**
     * 获取钉钉access_token的接口地址
     */
    public static final String ACCESS_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";

    /**
     * 递归获取钉钉部门的接口地址
     */
    public static final String GROUP_SYNC_URL = "https://oapi.dingtalk.com/department/list";


    /**
     * 钉钉分页获取用户列表的接口地址
     */
    public static final String DING_USER_URL = "https://oapi.dingtalk.com/user/listbypage";

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
    public static final String DEPT_URL = "https://oapi.dingtalk.com/department/list";

    /**
     * 钉钉根部门id
     */
    public static final Long DING_ROOT_DEPT_ID = 1L;

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
     * 导入钉钉用户时json数据的用户列表key
     */
    public static final String USER_LIST_KEY = "userlist";
    /**
     * 导入钉钉用户json数据，是否有更多的数据,key
     */
    public static final String HAS_MORE_KEY = "hasMore";

}
