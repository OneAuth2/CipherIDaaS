package cipher.console.oidc.model;/**
 * @author lqgzj
 * @date 2019-10-12
 */

import java.io.Serializable;

/**
 * @Author qiaoxi
 * @Date 2019-10-1217:44
 **/
public class LocalWxMapModel implements Serializable {

    /**
     * 本地属性
     */
    private String localKey;
    /**
     * 企业微信属性
     */
    private String wxKey;


    public LocalWxMapModel() {
    }

    public LocalWxMapModel(String localKey, String wxKey) {
        this.localKey = localKey;
        this.wxKey = wxKey;
    }

    public String getLocalKey() {
        return localKey;
    }

    public void setLocalKey(String localKey) {
        this.localKey = localKey;
    }

    public String getWxKey() {
        return wxKey;
    }

    public void setWxKey(String wxKey) {
        this.wxKey = wxKey;
    }
    @Override
    public String toString() {
        return "LocalWxMapModel{" +
                "localKey='" + localKey + '\'' +
                ", wxKey='" + wxKey + '\'' +
                '}';
    }

}
