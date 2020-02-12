package cipher.console.oidc.domain.subapp;

/**
 * 获取每刻报销系统tokenId的实体
 *
 * @Author: zt
 * @Date: 2018/12/4 12:01
 */
public class MaycurTokenReqDomain {

    /**
     * 客户端帐号
     */
    private String appCode;

    /**
     * 秘钥
     */
    private String secret;


    /**
     * 客户端提供的时间戳
     */
    private long timestamp;



    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public long getTimestamp() {
        if (timestamp == 0) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
