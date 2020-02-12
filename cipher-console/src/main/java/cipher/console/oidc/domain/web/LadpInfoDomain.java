package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/14.
 */
public class LadpInfoDomain implements Serializable {

    private Integer id;
    private String serverAddress;
    private int port;
    private Integer status;
    private String accountNumber;
    private String password;
    private int isSSL;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsSSL() {
        return isSSL;
    }

    public void setIsSSL(int isSSL) {
        this.isSSL = isSSL;
    }


}
