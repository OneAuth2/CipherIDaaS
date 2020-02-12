package cipher.console.oidc.domain.web;

/**
 * @Author: TK
 * @Date: 2018/12/7 15:42
 * 储存公共密钥和企业标识或者croptoken的类
 */
public class SecretKeyValue {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SecretKeyValue{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
