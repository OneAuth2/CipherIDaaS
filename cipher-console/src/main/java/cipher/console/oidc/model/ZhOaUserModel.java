package cipher.console.oidc.model;

import java.io.Serializable;

/**
 * 众合oa下发子账号的实体
 *
 * @Author: zt
 * @Date: 2019-4-10 16:18
 */
public class ZhOaUserModel implements Serializable {

    /**
     * 用户名
     */
    private String username;

    private String profileName;

    private String mobile;

    private String accountNumber;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "ZhOaUserModel{" +
                "username='" + username + '\'' +
                ", profileName='" + profileName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
