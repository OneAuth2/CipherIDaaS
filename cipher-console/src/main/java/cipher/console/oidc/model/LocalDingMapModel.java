package cipher.console.oidc.model;

import java.io.Serializable;

/**
 * 本地属性和dingding属性的映射关系
 *
 * @Author: zt
 * @Date: 2019-04-29 16:22
 */
public class LocalDingMapModel implements Serializable {

    /**
     * 本地属性
     */
    private String localKey;

    /**
     * 钉钉属性
     */
    private String dingKey;

    public LocalDingMapModel() {
    }

    public LocalDingMapModel(String localKey, String dingKey) {
        this.localKey = localKey;
        this.dingKey = dingKey;
    }

    public String getLocalKey() {
        return localKey;
    }

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }

    public String getDingKey() {
        return dingKey;
    }

    public void setDingKey(String dingKey) {
        this.dingKey = dingKey;
    }

    @Override
    public String toString() {
        return "LocalDingMapModel{" +
                "localKey='" + localKey + '\'' +
                ", dingKey='" + dingKey + '\'' +
                '}';
    }
}
