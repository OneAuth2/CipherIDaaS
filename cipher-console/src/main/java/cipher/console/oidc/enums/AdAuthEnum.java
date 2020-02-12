package cipher.console.oidc.enums;

/**
 * @Author: zt
 * AD域验证连接时的各种情况
 * @Date: 2018/6/8 15:43
 */
public enum AdAuthEnum {
    AUTH_SUCCESS(1, "连接AD域成功"),
    AUTH_FAIL(2, "用户名或者密码错误"),
    CONNECT_FAIL(3, "AD域连接失败"),
    SYS_ERROR(4, "系统错误,请检查系统是否开启");

    private int code;

    private String desc;

    public static String getAdAuthDesc(Integer code) {
        for(AdAuthEnum adAuthEnum:AdAuthEnum.values()){
            if(code==adAuthEnum.getCode()){
                return adAuthEnum.getDesc();
            }
        }
        return null;
    }

    AdAuthEnum(int code, String desc) {
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
