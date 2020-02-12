package cipher.console.oidc.enums;

/**
 * Created by 95744 on 2018/9/26.
 */
public enum WifiLoginType {

    LOGIN_IN(1,"登录"),
    LOGIN_OUT(2,"退出"),
    CANCLE(3,"注销");


    private int type;
    private String desc;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    WifiLoginType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static String getWifiLoginTypeEnum(int type) {
        for (WifiLoginType wifiLoginType : WifiLoginType.values()) {
            if (wifiLoginType.getType() == type) {
                return wifiLoginType.getDesc();
            }
        }
        return null;
    }

}
