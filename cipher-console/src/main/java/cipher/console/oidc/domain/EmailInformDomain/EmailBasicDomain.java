package cipher.console.oidc.domain.EmailInformDomain;

public class EmailBasicDomain {
    private Integer id;
    private String companyUuid;
    private String smtp;
    private int port;
    private String account;
    private String pwd;
    private int isSSL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getIsSSL() {
        return isSSL;
    }

    public void setIsSSL(int isSSL) {
        this.isSSL = isSSL;
    }

    @Override
    public String toString() {
        return "EmailBasicDomain{" +
                "smtp='" + smtp + '\'' +
                ", port=" + port +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", isSSL=" + isSSL +
                '}';
    }

}
