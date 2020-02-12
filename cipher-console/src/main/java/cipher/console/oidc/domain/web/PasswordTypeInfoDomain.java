package cipher.console.oidc.domain.web;

public class PasswordTypeInfoDomain {
    private Integer passwordCode;

    private String passwordFormat;

    private int status;

    public Integer getPasswordCode() {
        return passwordCode;
    }

    public void setPasswordCode(Integer passwordcode) {
        this.passwordCode = passwordcode;
    }

    public String getPasswordFormat() {
        return passwordFormat;
    }

    public void setPasswordFormat(String passwordformat) {
        this.passwordFormat = passwordformat;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}