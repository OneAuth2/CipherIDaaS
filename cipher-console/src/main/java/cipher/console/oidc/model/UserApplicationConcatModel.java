package cipher.console.oidc.model;

/**
 * @Author: zt
 * @Date: 2018/6/1 11:42
 */
public class UserApplicationConcatModel {

    /**
     * 主账号
     */
    private String accountNumber;

    /**
     * 该账号能看到的所有应用的拼接
     */
    private String appNameConcat;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAppNameConcat() {
        return appNameConcat;
    }

    public void setAppNameConcat(String appNameConcat) {
        this.appNameConcat = appNameConcat;
    }
}
