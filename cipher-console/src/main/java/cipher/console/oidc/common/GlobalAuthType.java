package cipher.console.oidc.common;


/**
 * 全局的认证方式
 * @Author:
 * @Date: 2018/8/27 11:44
 */
public enum  GlobalAuthType {

    LOCALAUTH(0,"本地认证"),
    ADAUTH(1,"AD认证"),
    JTLERP(2,"JTL-ERP");



    private int code;

    private String type;

    GlobalAuthType(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
