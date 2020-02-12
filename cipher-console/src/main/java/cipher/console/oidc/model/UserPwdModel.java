package cipher.console.oidc.model;

import cipher.console.oidc.domain.BaseDomain;

/**
 * @Author: zt
 * @Date: 2018/8/14 21:42
 */
public class UserPwdModel extends BaseDomain {
   private String userName;

    private String accountNumber;

    private String mail;

    private String password;

    private int isAdmin;

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserPwdModel{" +
                "userName='" + userName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }
}
