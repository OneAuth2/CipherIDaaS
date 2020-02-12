package cipher.console.oidc.service;

/**
 * @Author: zt
 * @Date: 2018/12/18 11:27
 */
public interface TotpService {

    /**
     * 根据某用户的secret生成对应的totp
     *
     * @param secret
     * @return
     */
    public int buildTotp(String secret);

}
