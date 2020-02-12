package cipher.console.oidc.enums;

/**
 * @Author: zt
 * @Date: 2018/10/25 9:20
 */

/**
 * 账号状态
 */
public enum AccountStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(2, "停用"),
    LOCK(3, "锁定");


    /**
     * 账号状态
     */
    private int code;

    /**
     * 状态描述
     */
    private String msg;

    AccountStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
