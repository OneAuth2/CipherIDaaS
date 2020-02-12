package cipher.console.oidc.domain.web;

public class AssociatedAccountInfo {
    private Integer assManual;
    private Integer assPrimaryAccount;
    private Integer assEmail;
    private Integer assEmailPrefix;
    private Integer assTelephone;
    private Integer assWorkers;
    private Integer assPwdManual;
    private Integer assPwdPrimaryAccount;

    public Integer getAssManual() {
        return assManual;
    }

    public void setAssManual(Integer assManual) {
        this.assManual = assManual;
    }

    public Integer getAssPrimaryAccount() {
        return assPrimaryAccount;
    }

    public void setAssPrimaryAccount(Integer assPrimaryAccount) {
        this.assPrimaryAccount = assPrimaryAccount;
    }

    public Integer getAssEmail() {
        return assEmail;
    }

    public void setAssEmail(Integer assEmail) {
        this.assEmail = assEmail;
    }

    public Integer getAssEmailPrefix() {
        return assEmailPrefix;
    }

    public void setAssEmailPrefix(Integer assEmailPrefix) {
        this.assEmailPrefix = assEmailPrefix;
    }

    public Integer getAssTelephone() {
        return assTelephone;
    }

    public void setAssTelephone(Integer assTelephone) {
        this.assTelephone = assTelephone;
    }

    public Integer getAssWorkers() {
        return assWorkers;
    }

    public void setAssWorkers(Integer assWorkers) {
        this.assWorkers = assWorkers;
    }

    public Integer getAssPwdManual() {
        return assPwdManual;
    }

    public void setAssPwdManual(Integer assPwdManual) {
        this.assPwdManual = assPwdManual;
    }

    public Integer getAssPwdPrimaryAccount() {
        return assPwdPrimaryAccount;
    }

    public void setAssPwdPrimaryAccount(Integer assPwdPrimaryAccount) {
        this.assPwdPrimaryAccount = assPwdPrimaryAccount;
    }

    @Override
    public String toString() {
        return "AssociatedAccountInfo{" +
                "assManual=" + assManual +
                ", assPrimaryAccount=" + assPrimaryAccount +
                ", assEmail=" + assEmail +
                ", assEmailPrefix=" + assEmailPrefix +
                ", assTelephone=" + assTelephone +
                ", assWorkers=" + assWorkers +
                ", assPwdManual=" + assPwdManual +
                ", assPwdPrimaryAccount=" + assPwdPrimaryAccount +
                '}';
    }
}
