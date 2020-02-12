package cipher.console.oidc.model;

/**
 * @Author: zt
 * @Date: 2018/11/15 16:24
 */
public class AdMap2LocalConfigModel {

    /**
     * ad域当中的属性
     */
    private String adKey;

    /**
     * 对应本地数据库当中的属性
     */
    private String localVal;

    public AdMap2LocalConfigModel() {
    }

    public AdMap2LocalConfigModel(String adKey, String localVal) {
        this.adKey = adKey;
        this.localVal = localVal;
    }

    public String getAdKey() {
        return adKey;
    }

    public void setAdKey(String adKey) {
        this.adKey = adKey;
    }

    public String getLocalVal() {
        return localVal;
    }

    public void setLocalVal(String localVal) {
        this.localVal = localVal;
    }

    @Override
    public String toString() {
        return "AdMap2LocalConfigModel{" +
                "adKey='" + adKey + '\'' +
                ", localVal='" + localVal + '\'' +
                '}';
    }
}
