package cipher.console.oidc.model;

import java.io.Serializable;

/**
 * @Author: TK
 * @Date: 2018/12/8 16:40
 */
public class SubAcountConfigModel implements Serializable {
    private String localValue;
    private String subKey;

    public String getLocalValue() {
        return localValue;
    }

    public void setLocalValue(String localValue) {
        this.localValue = localValue;
    }

    public String getSubKey() {
        return subKey;
    }

    public void setSubKey(String subKey) {
        this.subKey = subKey;
    }

    @Override
    public String toString() {
        return "SubAcountConfigModel{" +
                "localValue='" + localValue + '\'' +
                ", subKey='" + subKey + '\'' +
                '}';
    }
}
