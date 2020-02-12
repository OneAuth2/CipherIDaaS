package cipher.console.oidc.domain.web;

public class PlatUserDomain {

    private Integer id;
    private Integer platUserId;
    private String accountNumber;
    private String creatTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlatUserId() {
        return platUserId;
    }

    public void setPlatUserId(Integer platUserId) {
        this.platUserId = platUserId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }
}
