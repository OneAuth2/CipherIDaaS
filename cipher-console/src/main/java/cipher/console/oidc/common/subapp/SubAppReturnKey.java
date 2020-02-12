package cipher.console.oidc.common.subapp;

/**
 * 子账号下发返回的key
 * @Author: zt
 * @Date: 2018/11/29 10:44
 */
public class SubAppReturnKey {

    /**
     * 一定有，0标识请求正确，其他表示出错
     */
    public static final String errCode="ERR_CODE";

    /**
     * 一定有，若,errCode为0,则，errMsg为ok,其余为对应的错误信息
     */
    public static final String errMsg="ERR_MSG";

    public static final int success=0;

    public static final int fail=1;

}
