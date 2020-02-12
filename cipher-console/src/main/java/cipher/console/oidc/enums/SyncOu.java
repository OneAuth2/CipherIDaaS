package cipher.console.oidc.enums;

/**
 * AD连接配置中，是否同步OU
 * @Author: zt
 * @Date: 2018/11/13 14:54
 */
public enum SyncOu {

    SYNC_OU(1,"同步OU"),
    NOT_SYNC_OU(2,"不同步OU");

    private int code;
    private String desc;


    SyncOu(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
