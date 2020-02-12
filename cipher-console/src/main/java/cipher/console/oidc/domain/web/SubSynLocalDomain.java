package cipher.console.oidc.domain.web;

/**
 * @Author: TK
 * @Date: 2018/12/7 9:25
 */
public class SubSynLocalDomain {
    private String  subKey;//同步子账号对应的字段
    private String localValue;//本地对应的字段

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    public String getLocalValue() {
        return localValue;
    }

    public void setLocalValue(String localValue) {
        this.localValue = localValue;
    }
}
